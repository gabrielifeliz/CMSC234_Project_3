package com.zybooks.gameemulator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class runs the main activity of the app
 * @author Gabriel Isaac Feliz
 */
public class GameEmulatorActivity extends AppCompatActivity {

    // Grid layout for the buttons that will show either X or O
    private GridLayout gridLayoutButtons;
    // Grid layout for the vectors X or O
    private GridLayout gridLayoutVectors;
    // Tic Tac Toe game object
    private TicTacToeGame mGame;
    // Text view for game messages
    private TextView gameMessage;
    // Two-dimensional array of buttons
    private Button[][] mButtons;
    // Two-dimensional array of image views (vectors X or O)
    private ImageView[][] mImageViews;

    // The first turn when game starts
    private static char initTurn;

    private static String selectedPlayer1;
    private static String selectedPlayer2;

    private GameEmulatorDatabase gameEmulatorDb;

    // Label for game state
    private static final String GAME_STATE = "gameState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(null);
        // Display layout for main activity
        setContentView(R.layout.activity_game_emulator);

        // Initialize 2D array of buttons
        mButtons = new Button[TicTacToeGame.NUM_ROWS][TicTacToeGame.NUM_COLS];
        // Initialize 2D array of image views (X or O)
        mImageViews = new ImageView[TicTacToeGame.NUM_ROWS][TicTacToeGame.NUM_COLS];
        // Find text view for game messages from the layout file
        gameMessage = findViewById(R.id.game_message);
        // Find grid layout for buttons from the layout file
        gridLayoutButtons = findViewById(R.id.light_grid);
        // Find grid layout for image views from the layout file
        gridLayoutVectors = findViewById(R.id.vector_grid);

        Intent intent = getIntent();
        selectedPlayer1 = intent.getStringExtra(MainMenuActivity.SELECTED_PLAYER_1);
        selectedPlayer2 = intent.getStringExtra(MainMenuActivity.SELECTED_PLAYER_2);

        gameEmulatorDb = GameEmulatorDatabase.getInstance(getApplicationContext());

        // Initialize game object
        mGame = new TicTacToeGame();

        if (savedInstanceState == null) {
            // Start the game when the app is first opened
            startGame();
        }
        else {
            // Get game state
            String gameState = savedInstanceState.getString(GAME_STATE);
            // Reset tic tac toe view
            initializeTicTacToe();
            // Restore tic tac toe game
            restoreTicTacToe(gameState);
            // Set current game message
            setGameMessage();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Set save instance state
        super.onSaveInstanceState(outState);
        // Save game state a as string
        outState.putString(GAME_STATE, mGame.getState());
    }

    private void startGame() {
        // Create a new game state
        mGame.newGame();
        // Randomly choose who's turn it will be
        mGame.initializeTurn();
        // Reset tic tac toe view
        initializeTicTacToe();
        initTurn = mGame.getTurn();

        mGame.setPlayers(initTurn, selectedPlayer1, selectedPlayer2);

        // Display who's turn it is
        String message = mGame.getPlayer().toUpperCase() + "'S TURN";
        gameMessage.setText(message);
        // Set color for a message about turn
        gameMessage.setTextColor(getResources().getColor(R.color.colorNormalText));
    }

    private void initializeTicTacToe() {
        // Set child index to zero
        int childIndex = 0;
        for (int row = 0; row < TicTacToeGame.NUM_ROWS; row++) {
            for (int col = 0; col < TicTacToeGame.NUM_COLS; col++) {
                // Set current buttons into the 2D array
                mButtons[row][col] = (Button) gridLayoutButtons.getChildAt(childIndex);
                mButtons[row][col].setBackgroundColor(getResources().getColor(R.color.colorEmpty));
                // Enable the buttons to be tapped
                mButtons[row][col].setEnabled(true);
                // Set current image views into the 2D array
                mImageViews[row][col] = (ImageView) gridLayoutVectors.getChildAt(childIndex);
                // Set image to be a vector that symbolizes the square is empty
                mImageViews[row][col].setImageResource(R.drawable.tic_tac_toe_empty);
                // Increase child index
                childIndex++;
            }
        }
    }

    public void onLightButtonClick(View view) {
        // Find the row and col selected
        boolean buttonFound = false;
        for (int row = 0; row < TicTacToeGame.NUM_ROWS && !buttonFound; row++) {
            for (int col = 0; col < TicTacToeGame.NUM_COLS && !buttonFound; col++) {
                // Check if the current view clicked is one of the buttons in the 2D array
                if (view == mButtons[row][col]) {
                    // If so, select the button and save it into the game state
                    mGame.selectButton(row, col);
                    if (mGame.getTurn() == 'X') {
                        // Display the X vector if the current turn is X
                        mImageViews[row][col].setImageResource(R.drawable.tic_tac_toe_x);
                        mButtons[row][col].setBackgroundColor(getResources().getColor(R.color.player1));
                    } else if (mGame.getTurn() == 'O') {
                        // Display the O vector if the current turn is O
                        mImageViews[row][col].setImageResource(R.drawable.tic_tac_toe_o);
                        mButtons[row][col].setBackgroundColor(getResources().getColor(R.color.player2));
                    }
                    // This square can't be chosen anymore
                    mButtons[row][col].setEnabled(false);
                    // The button is found
                    buttonFound = true;
                }
            }
        }
        // Change player's turn
        mGame.changeTurn();
        // Set a new message
        setGameMessage();
    }

    private void setGameMessage() {
        if (mGame.isThereWinner()) {
            // Change the player's turn back to the winner
            mGame.changeTurn();
            // Display who won
            String message = mGame.getPlayer().toUpperCase() + " WINS!";
            gameMessage.setText(message);
            // Set color of message for the winner
            gameMessage.setTextColor(getResources().getColor(R.color.colorWinner));

            Player winner = gameEmulatorDb.playerDao().getPlayer(mGame.getPlayer()
                            .equals(selectedPlayer1) ? selectedPlayer1 : selectedPlayer2);
            winner.setWins(winner.getWins() + 1);
            Player loser = gameEmulatorDb.playerDao().getPlayer(mGame.getPlayer()
                            .equals(selectedPlayer1) ? selectedPlayer2 : selectedPlayer1);
            loser.setLosses(loser.getLosses() + 1);
            gameEmulatorDb.playerDao().updatePlayer(winner);
            gameEmulatorDb.playerDao().updatePlayer(loser);

            for (int row = 0; row < TicTacToeGame.NUM_ROWS; row++) {
                for (int col = 0; col < TicTacToeGame.NUM_COLS; col++) {
                    // Disable the buttons to be tapped
                    mButtons[row][col].setEnabled(false);
                }
            }
        } else {
            // Check if the game is complete but no player has won
            if (mGame.isGameComplete()) {
                // Display that it is a tie
                gameMessage.setText(R.string.tie);
                // Set color of message for a tie game
                gameMessage.setTextColor(getResources().getColor(R.color.colorTie));

                Player tie1 = gameEmulatorDb.playerDao().getPlayer(selectedPlayer1);
                tie1.setTies(tie1.getTies() + 1);
                Player tie2 = gameEmulatorDb.playerDao().getPlayer(selectedPlayer2);
                tie2.setTies(tie2.getTies() + 1);
                gameEmulatorDb.playerDao().updatePlayer(tie1);
                gameEmulatorDb.playerDao().updatePlayer(tie2);
            } else {
                // Display the player's turn
                String message = mGame.getPlayer().toUpperCase() + "'S TURN";
                gameMessage.setText(message);
                // Set color for a message about turn
                gameMessage.setTextColor(getResources().getColor(R.color.colorNormalText));
            }
        }
    }

    private void restoreTicTacToe(String gameState) {
        // Restore the game state in the game object with the initial turn
        mGame.restoreState(gameState, initTurn, selectedPlayer1, selectedPlayer2);
        // Initialize index to zero
        int index = 0;
        for (int row = 0; row < TicTacToeGame.NUM_ROWS; row++) {
            for (int col = 0; col < TicTacToeGame.NUM_COLS; col++) {
                // Check if the current character from the game state is an X
                if (gameState.charAt(index) == TicTacToeGame.PLAYER_X) {
                    // Display an X
                    mImageViews[row][col].setImageResource(R.drawable.tic_tac_toe_x);
                    mButtons[row][col].setBackgroundColor(getResources().getColor(R.color.player1));
                    // Disable button for tapping
                    mButtons[row][col].setEnabled(false);
                } // Check if the current character from the game state is an O
                else if (gameState.charAt(index) == TicTacToeGame.PLAYER_O) {
                    // Display an O
                    mImageViews[row][col].setImageResource(R.drawable.tic_tac_toe_o);
                    mButtons[row][col].setBackgroundColor(getResources().getColor(R.color.player2));
                    // Disable button for tapping
                    mButtons[row][col].setEnabled(false);
                }
                else {
                    // Display a vector for empty square
                    mImageViews[row][col].setImageResource(R.drawable.tic_tac_toe_empty);
                    // Enable button for tapping
                    mButtons[row][col].setEnabled(true);
                }
                // Increase index
                index++;
            }
        }
    }

    public void onNewGameClick(View view) {
        // Start a new game when clicking the New Game button
        startGame();
    }
}
