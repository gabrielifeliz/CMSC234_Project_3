package com.zybooks.gameemulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Activity to select a list of players as player 1 for the tic tac toe
 * @author Gabriel Isaac Feliz
 */
public class SelectPlayer1Activity extends AppCompatActivity {

    public static final String PLAYER_1 = "com.zybooks.gameemulator.player1";

    private Spinner player1Options;

    private String selectedPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player1);

        // Get name of player 2 from previous activity
        Intent intent = getIntent();
        selectedPlayer2 = intent.getStringExtra(MainMenuActivity.SELECTED_PLAYER_2);

        // Create game emulator database instance
        GameEmulatorDatabase gameEmulatorDb = GameEmulatorDatabase.getInstance(getApplicationContext());

        // Find the spinner object from the layout XML
        player1Options = findViewById(R.id.player1_options);

        // Set player 1 options from all players in the database
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, gameEmulatorDb.playerDao().getPlayerNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        player1Options.setAdapter(adapter);

    }

    public void onSavePlayer1Click(View view) {
        // Get selected item in spinner
        String player1 = player1Options.getSelectedItem().toString();
        if (player1.equals(selectedPlayer2)) {
            // Inform the user that this player is selected as player 2
            Toast.makeText(this, player1 + " has already been selected as player 2",
                            Toast.LENGTH_SHORT).show();
        } else {
            // Inform the user that the selected player is player 1
            Toast.makeText(this, player1 + " selected as player 1 successfully",
                            Toast.LENGTH_SHORT).show();
            // Move to the main menu activity and send player 1 name
            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.putExtra(PLAYER_1, player1);
            startActivity(intent);
            finish();
        }
    }
}