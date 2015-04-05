package com.moligamescr.simpleandroidgdf;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioManager;
import android.media.SoundPool;
import java.io.IOException;
import java.io.InputStream;
import com.moligamescr.framework.animation.Animation;
import com.moligamescr.framework.animation.Frame;

public class Assets {

    private static SoundPool soundPool;
    public static Bitmap welcome, block, cloud1, cloud2, duck, grass, jump, run1, run2, run3, run4, run5, score, scoreDown, start, startDown, mute, muteDown, unmute, unmuteDown;
    public static Animation runAnim;
    public static int hitID, onJumpID;
    private static MediaPlayer mediaPlayer;

    public static void load(){
        welcome = loadBitmap("welcome.png", false);
        block = loadBitmap("block.png", false);
        cloud1 = loadBitmap("cloud1.png", false);
        cloud2 = loadBitmap("cloud2.png", false);
        duck = loadBitmap("duck.png", false);
        grass = loadBitmap("grass.png", false);
        jump = loadBitmap("jump.png", false);
        run1 = loadBitmap("runAnim1.png", false);
        run2 = loadBitmap("runAnim2.png", false);
        run3 = loadBitmap("runAnim3.png", false);
        run4 = loadBitmap("runAnim4.png", false);
        run5 = loadBitmap("runAnim5.png", false);
        score = loadBitmap("scoreButton.png", false);
        scoreDown = loadBitmap("scoreButtonDown.png", false);
        start = loadBitmap("startButton.png", false);
        startDown = loadBitmap("startButtonDown.png", false);
        mute = loadBitmap("muteButton.png", false);
        muteDown = loadBitmap("muteButtonDown.png", false);
        unmute = loadBitmap("unmuteButton.png", false);
        unmuteDown = loadBitmap("unmuteButtonDown.png", false);

        Frame f1 = new Frame(run1, .1f);
        Frame f2 = new Frame(run2, .1f);
        Frame f3 = new Frame(run3, .1f);
        Frame f4 = new Frame(run4, .1f);
        Frame f5 = new Frame(run5, .1f);
        runAnim = new Animation(f1, f2, f3, f4, f5, f3, f2);
    }

    public static void onResume(){
        hitID = loadSound("hit.wav");
        onJumpID = loadSound("onJump.wav");
        playMusic("bgMusic.mp3", true);

    }

    public static void onPause(){
        if (soundPool != null){
            soundPool.release();
            soundPool = null;
        }
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private static Bitmap loadBitmap(String filename, boolean transparency) {
        InputStream inputStream = null;
        try {
            inputStream = GameMainActivity.assets.open(filename);
        }catch (IOException e){
            e.printStackTrace();
        }
        Options options = new Options();
        if (transparency){
            options.inPreferredConfig = Config.ARGB_8888;
        } else {
            options.inPreferredConfig = Config.RGB_565;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        return bitmap;
    }

    private static int loadSound(String filename){
        int soundID = 0;
        if (soundPool == null){
            soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
        }
        try {
            soundID = soundPool.load(GameMainActivity.assets.openFd(filename), 1);
        } catch (IOException e){
            e.printStackTrace();
        }
        return soundID;
    }

    public static void playSound(int soundID){
        if (soundPool != null){
            soundPool.play(soundID, 1, 1, 1, 0, 1);
        }
    }

    public static void playMusic(String filename, boolean looping){
        if (mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
        }
        try {
            AssetFileDescriptor afd = GameMainActivity.assets.openFd(filename);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(looping);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onMute() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public static void onUnmute() {
        if (mediaPlayer != null){
            mediaPlayer.start();
        } else {
            playMusic("bgMusic.mp3", true);
        }
    }
}
