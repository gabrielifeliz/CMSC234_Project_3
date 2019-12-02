package com.zybooks.gameemulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Activity to select the following options:
 * Start Game, View Scoreboard, Select Player 1,
 * Select Player 2, Add Player, and Delete Player
 * @author Gabriel Isaac Feliz
 */
public class MainMenuActivity extends AppCompatActivity {

    public static final String SELECTED_PLAYER_1 = "com.zybooks.gameemulator.selected_player1";
    public static final String SELECTED_PLAYER_2 = "com.zybooks.gameemulator.selected_player2";

    private static String player1;
    private static String player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();
        String newPlayer1 = intent.getStringExtra(SelectPlayer1Activity.PLAYER_1);
        if (newPlayer1 != null) {
            // Set player 1 name from previous activity if selected
            player1 = newPlayer1;
        }
        String newPlayer2 = intent.getStringExtra(SelectPlayer2Activity.PLAYER_2);
        if (newPlayer2 != null) {
            // Set player 2 name from previous activity if selected
            player2 = newPlayer2;
        }
    }

    public void onStartGameClicked(View view) {
        if (player1 == null || player2 == null) {
            // If either player 1 or player 2 has not been selected, do not start game
            Toast.makeText(this, "Select two players to start the game",
                            Toast.LENGTH_SHORT).show();
        } else {
            // If both player 1 and 2 are selected, move to the game emulator activity
            Toast.makeText(this, "Let's begin!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, GameEmulatorActivity.class);
            intent.putExtra(SELECTED_PLAYER_1, player1);
            intent.putExtra(SELECTED_PLAYER_2, player2);
            startActivity(intent);
        }
    }

    public void onViewScoreboardClicked(View view) {
        Intent intent = new Intent(this, ScoreboardActivity.class);
        startActivity(intent);
    }

    public void onSelectPlayer1Clicked(View view) {
        Intent intent = new Intent(this, SelectPlayer1Activity.class);
        intent.putExtra(SELECTED_PLAYER_2, player2);
        startActivity(intent);
    }

    public void onSelectPlayer2Clicked(View view) {
        Intent intent = new Intent(this, SelectPlayer2Activity.class);
        intent.putExtra(SELECTED_PLAYER_1, player1);
        startActivity(intent);
    }

    public void onAddPlayerClicked(View view) {
        Intent intent = new Intent(this, AddPlayerActivity.class);
        startActivity(intent);
    }

    public void onDeletePlayerClicked(View view) {
        Intent intent = new Intent(this, DeletePlayerActivity.class);
        startActivity(intent);
    }
}
