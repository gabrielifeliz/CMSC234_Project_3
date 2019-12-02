package com.zybooks.gameemulator;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * Fragment to display alert dialog
 * @author Gabriel Isaac Feliz
 */
public class DeleteDialogFragment extends DialogFragment {

    private Context deleteContext;

    private GameEmulatorDatabase gameEmulatorDb;

    private Player playerToBeDeleted;

    public DeleteDialogFragment(Context context, GameEmulatorDatabase db, Player player) {
        // Set delete player activity context
        deleteContext = context;
        // Set game emulator database from an activity
        gameEmulatorDb = db;
        // Set player object to be deleted
        playerToBeDeleted = player;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // Set alert dialog title
                .setTitle(R.string.delete_warning_title)
                // Set alert dialog message
                .setMessage(R.string.delete_warning_message)
                // Set Yes button with the action to delete a player
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Delete player
                        gameEmulatorDb.playerDao().deletePlayer(playerToBeDeleted);
                        // Inform the user that the player was deleted
                        Toast.makeText(deleteContext, playerToBeDeleted.getPlayerName() +
                                        " was deleted successfully", Toast.LENGTH_SHORT).show();
                        // Move to the main menu activity
                        Intent intent = new Intent(deleteContext, MainMenuActivity.class);
                        startActivity(intent);
                    }
                })
                // Set No button with no action
                .setNegativeButton(R.string.no, null)
                // Create dialog
                .create();
    }
}
