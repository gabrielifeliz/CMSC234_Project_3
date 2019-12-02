package com.zybooks.gameemulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Activity to select a list of players as player 2 for the tic tac toe
 * @author Gabriel Isaac Feliz
 */
public class SelectPlayer2Activity extends AppCompatActivity {

    public static final String PLAYER_2 = "com.zybooks.gameemulator.player2";

    private Spinner player2Options;

    private String selectedPlayer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player2);

        // Get name of player 1 from previous activity
        Intent intent = getIntent();
        selectedPlayer1 = intent.getStringExtra(MainMenuActivity.SELECTED_PLAYER_1);

        // Create game emulator database instance
        GameEmulatorDatabase gameEmulatorDb = GameEmulatorDatabase.getInstance(getApplicationContext());

        // Find the spinner object from the layout XML
        player2Options = findViewById(R.id.player2_options);

        // Set player 2 options from all players in the database
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, gameEmulatorDb.playerDao().getPlayerNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        player2Options.setAdapter(adapter);
    }

    public void onSavePlayer2Click(View view) {
        // Get selected item in spinner
        String player2 = player2Options.getSelectedItem().toString();
        if (player2.equals(selectedPlayer1)) {
            // Inform the user that this player is selected as player 1
            Toast.makeText(this, player2 + " has already been selected as player 1",
                            Toast.LENGTH_SHORT).show();
        } else {
            // Inform the user that the selected player is player 2
            Toast.makeText(this, player2 + " selected as player 2 successfully",
                    Toast.LENGTH_SHORT).show();
            // Move to the main menu activity and send player 2 name
            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.putExtra(PLAYER_2, player2);
            startActivity(intent);
            finish();
        }
    }
}
