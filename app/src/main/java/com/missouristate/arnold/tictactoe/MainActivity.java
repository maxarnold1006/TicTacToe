package com.missouristate.arnold.tictactoe;


import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Point;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private TicTacToe tttGame;
    private ButtonGridAndTextView tttView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tttGame = new TicTacToe();
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x / TicTacToe.SIDE;
        ButtonHandler bh = new ButtonHandler();
        tttView = new ButtonGridAndTextView( this, w, TicTacToe.SIDE, bh);
        tttView.setStatusText(tttGame.result());
        setContentView(tttView);
    }
    //Message dialogue prompts to play again
    public void showNewGameDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("This is fun");
        alert.setMessage("Play again?");
        PlayDialog playAgain = new PlayDialog();
        alert.setPositiveButton("Yes", playAgain);
        alert.setNegativeButton("NO", playAgain);
        alert.show();
    }


    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {
            for (int row = 0; row < TicTacToe.SIDE; row++) {
                for (int column = 0; column < TicTacToe.SIDE; column++) {
                    if (tttView.isButton((Button) v, row, column)) {
                        int play = tttGame.play(row, column);
                        if (play == 1)
                            tttView.setButtonText(row, column, "X");
                        else if (play == 2)
                            tttView.setButtonText(row, column, "O");
                        if (tttGame.isGameOver()) {
                            tttView.setStatusBackgroundcolor(Color.RED);
                            tttView.enableButtons(false);
                            tttView.setStatusText(tttGame.result());
                            showNewGameDialog(); //offer to play again
                        }
                    }
                }
            }
        }
    }
    private class PlayDialog implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int id) {
            if (id == -1) { //Yes button
                tttGame.resetGame();
                tttView.enableButtons(true);
                tttView.resetButtons();
                tttView.setStatusBackgroundcolor(Color.GREEN);
                tttView.setStatusText(tttGame.result());
            } else if (id == -2) //NO button
                MainActivity.this.finish();
        }

    }
}
