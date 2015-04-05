package com.moligamescr.game.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;
import com.moligamescr.framework.util.Painter;
import com.moligamescr.simpleandroidgdf.GameMainActivity;

public class GameOverState extends State {

    private String playerScore;
    private String gameOverMessage = "GAME OVER";

    public GameOverState(int playerScore){
        this.playerScore = playerScore + "";
        if (playerScore > GameMainActivity.getHighScore()){
            GameMainActivity.setHighScore(playerScore);
            gameOverMessage = "HIGH SCORE";
        }
    }

    @Override
    public void init() {
        //No implementado.
    }

    @Override
    public void update(float delta) {
        //No implementado.
    }

    @Override
    public void render(Painter g) {
        g.setColor(Color.rgb(255, 145, 0));
        g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        g.setColor(Color.GRAY);
        g.setFont(Typeface.DEFAULT_BOLD, 50);
        g.drawString(gameOverMessage, 257, 175);
        g.drawString(playerScore, 385, 250);
        g.drawString("Touch the screen", 220, 350);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_UP){
            setCurrentState(new MenuState());
        }
        return true;
    }

}
