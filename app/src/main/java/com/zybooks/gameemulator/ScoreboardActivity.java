package com.zybooks.gameemulator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Activity to show scoreboard with all players' stats
 * @author Gabriel Isaac Feliz
 */
public class ScoreboardActivity extends AppCompatActivity {

    private GameEmulatorDatabase gameEmulatorDb;

    private TableLayout scoreboardTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        // Create game emulator database instance
        gameEmulatorDb = GameEmulatorDatabase.getInstance(getApplicationContext());

        // Get table layout object in layout XML
        scoreboardTable = findViewById(R.id.scoreboard_table);

        // Display table
        displayPlayerStats();
    }

    private void displayPlayerStats() {

        // Display header
        displayHeader();
        // Display players from database if any
        displayItems();

    }

    private void displayHeader() {

        // Header text for player name with size 20 and style bold
        TextView name = new TextView(getApplicationContext());
        name.setText(R.string.scoreboard_name);
        name.setTextSize(20);
        name.setTypeface(null, Typeface.BOLD);
        // Header text for wins with size 20 and style bold
        TextView wins = new TextView(getApplicationContext());
        wins.setText(R.string.scoreboard_wins);
        wins.setTextSize(20);
        wins.setTypeface(null, Typeface.BOLD);
        // Header text for losses with size 20 and style bold
        TextView losses = new TextView(getApplicationContext());
        losses.setText(R.string.scoreboard_losses);
        losses.setTextSize(20);
        losses.setTypeface(null, Typeface.BOLD);
        // Header text for ties with size 20 and style bold
        TextView ties = new TextView(getApplicationContext());
        ties.setText(R.string.scoreboard_ties);
        ties.setTextSize(20);
        ties.setTypeface(null, Typeface.BOLD);

        // Create first row
        TableRow row = new TableRow(getApplicationContext());
        // Set views to the center-horizontal
        row.setGravity(R.style.ScoreboardRow);
        // Add text views to the row
        row.addView(name);
        row.addView(wins);
        row.addView(losses);
        row.addView(ties);

        // Add row to table
        scoreboardTable.addView(row);
    }

    private void displayItems() {
        // Display a numbered list of items
        for (Player player : gameEmulatorDb.playerDao().getPlayersMostWinsFirst()) {
            // A player's name with size 20 and style bold
            TextView name = new TextView(getApplicationContext());
            name.setText("\t" + player.getPlayerName() + "\t");
            name.setTextSize(20);
            // A player's wins with size 20 and style bold
            TextView wins = new TextView(getApplicationContext());
            wins.setText("\t" + player.getWins() + "\t");
            wins.setTextSize(20);
            // A player's losses with size 20 and style bold
            TextView losses = new TextView(getApplicationContext());
            losses.setText("\t" + player.getLosses() + "\t");
            losses.setTextSize(20);
            // A player's ties with size 20 and style bold
            TextView ties = new TextView(getApplicationContext());
            ties.setText("\t" + player.getTies() + "\t");
            ties.setTextSize(20);

            // Create new row
            TableRow row = new TableRow(getApplicationContext());
            // Set views to the center-horizontal
            row.setGravity(R.style.ScoreboardRow);
            // Add text views to the row
            row.addView(name);
            row.addView(wins);
            row.addView(losses);
            row.addView(ties);

            // Add row to table
            scoreboardTable.addView(row);
        }
    }
}
