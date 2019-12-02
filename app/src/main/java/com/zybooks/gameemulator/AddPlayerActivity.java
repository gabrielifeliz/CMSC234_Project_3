package com.zybooks.gameemulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity to add a player to the database
 * @author Gabriel Isaac Feliz
 */
public class AddPlayerActivity extends AppCompatActivity {

    private GameEmulatorDatabase gameEmulatorDb;
    private EditText newPlayerInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        // Create a game emulator database instance
        gameEmulatorDb = GameEmulatorDatabase.getInstance(getApplicationContext());

        // Find the text view object from the layout XML
        newPlayerInput = findViewById(R.id.new_player);
    }

    public void onAddPlayerClick(View view) {

        // Create new player
        Player newPlayer = new Player(newPlayerInput.getText().toString());
        try {
            // Attempt inserting the new player into the database
            gameEmulatorDb.playerDao().insertPlayer(newPlayer);
            // Inform the user that the player was added successfully
            Toast.makeText(this, newPlayer.getPlayerName()
                            + " added successfully.", Toast.LENGTH_SHORT).show();
            // Move to the main menu activity
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            // Stay in the add player activity
            // Inform user that there is already a player with that name
            Toast.makeText(this, newPlayer.getPlayerName()
                            + " has already been added.", Toast.LENGTH_SHORT).show();
        }
    }
}
