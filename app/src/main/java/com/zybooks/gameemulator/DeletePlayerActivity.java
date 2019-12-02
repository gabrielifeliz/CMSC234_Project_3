package com.zybooks.gameemulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Activity to select a list of players as player 1 for the tic tac toe
 * @author Gabriel Isaac Feliz
 */
public class DeletePlayerActivity extends AppCompatActivity {

    private GameEmulatorDatabase gameEmulatorDb;

    private Spinner playerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_player);

        // Create a game emulator database instance
        gameEmulatorDb = GameEmulatorDatabase.getInstance(getApplicationContext());

        // Find the spinner object from the layout XML
        playerOptions = findViewById(R.id.player_options);

        // Set player options from all players in the database
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, gameEmulatorDb.playerDao().getPlayerNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerOptions.setAdapter(adapter);
    }

    public void onDeletePlayerClick(View view) {
        // Get selected player name
        String player = playerOptions.getSelectedItem().toString();
        // Create player to be deleted
        Player playerToBeDeleted = gameEmulatorDb.playerDao().getPlayer(player);
        FragmentManager manager = getSupportFragmentManager();
        // Create delete dialog, passing current database instance and player to be deleted
        DeleteDialogFragment dialog = new DeleteDialogFragment(
                this, gameEmulatorDb, playerToBeDeleted);
        // Show dialog
        dialog.show(manager, "deleteDialogFragment");
    }
}
