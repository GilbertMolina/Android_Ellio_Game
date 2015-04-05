package com.moligamescr.game.state;

import android.view.MotionEvent;
import com.moligamescr.framework.util.Painter;
import com.moligamescr.framework.util.UIButton;
import com.moligamescr.simpleandroidgdf.Assets;
import com.moligamescr.simpleandroidgdf.GameMainActivity;

public class MenuState extends State{

    private UIButton playButton, scoreButton, muteButton, unmuteButton;

    @Override
    public void init() {
        playButton = new UIButton(316, 227, 484, 286, Assets.start, Assets.startDown);
        scoreButton = new UIButton(316, 300, 484, 359, Assets.score, Assets.scoreDown);
        muteButton = new UIButton(752, 0, 800, 48, Assets.mute, Assets.muteDown);
        unmuteButton = new UIButton(752, 0, 800, 48, Assets.unmute, Assets.unmuteDown);
    }

    @Override
    public void update(float delta) {
        //No implementado.
    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.welcome, 0, 0);
        playButton.render(g);
        scoreButton.render(g);

        if (GameMainActivity.isMuted()){
            unmuteButton.render(g);
        } else {
            muteButton.render(g);
        }
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN){
            playButton.onTouchDown(scaledX, scaledY);
            scoreButton.onTouchDown(scaledX, scaledY);
            if (GameMainActivity.isMuted()){
                unmuteButton.onTouchDown(scaledX, scaledY);
            } else {
                muteButton.onTouchDown(scaledX, scaledY);
            }
        }

        if (e.getAction() == MotionEvent.ACTION_UP){
            if (playButton.isPressed(scaledX, scaledY)){
                playButton.cancel();
                setCurrentState(new PlayState());
                //Log.d("MenuState", "Play Button Pressed!");
            } else if (scoreButton.isPressed(scaledX, scaledY)){
                scoreButton.cancel();
                setCurrentState(new ScoreState());
                //Log.d("MenuState", "Score Button Pressed!");
            } else if(muteButton.isPressed(scaledX, scaledY)) {
                muteButton.cancel();
                Assets.onMute();
                GameMainActivity.setMuted(true);
            } else if(unmuteButton.isPressed(scaledX, scaledY)) {
                unmuteButton.cancel();
                Assets.onUnmute();
                GameMainActivity.setMuted(false);
            } else {
                playButton.cancel();
                scoreButton.cancel();
                muteButton.cancel();
                unmuteButton.cancel();
            }
        }
        return true;
    }
}
