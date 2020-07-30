package com.mohammadsulaeman.mediaplayermusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.Handler;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private TextView songName, songDurasi;
    private SeekBar seekBar;
    private double timeStart = 0, finalTime = 0;
    private int forwardTime = 2000, backwardTime = 2000;
    private Handler durationHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // inisialisasi komponen

        songName = (TextView) findViewById(R.id.songName);
        songDurasi = (TextView) findViewById(R.id.songDurasi);
        mediaPlayer = MediaPlayer.create(this, R.raw.dont_wory);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        songName.setText("Lagu Mp3");
        seekBar.setMax((int) finalTime);
        seekBar.setClickable(false);
    }

    private Runnable updateSeekBarTime = new Runnable() {
        @Override
        public void run() {
            timeStart = mediaPlayer.getCurrentPosition();
            seekBar.setProgress((int) timeStart);
            double timeRemaining = finalTime - timeStart;
            songDurasi.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long)timeRemaining),
                    TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)timeRemaining))));
            durationHandler.postDelayed(this,100);
        }
    };
    public void play(View view){
        mediaPlayer.start();
        timeStart = mediaPlayer.getCurrentPosition();
        seekBar.setProgress((int) timeStart);
        durationHandler.postDelayed(updateSeekBarTime,100);
    }
    public void pause(View view){
        mediaPlayer.pause();
    }
    public void forward(View view){
        if ((timeStart + forwardTime) <= finalTime){
            timeStart = timeStart - backwardTime;
            mediaPlayer.seekTo((int) timeStart);
        }
    }
    public void backforward(View view){
        if ((timeStart - backwardTime) > 0) {
            timeStart = timeStart - backwardTime;
            mediaPlayer.seekTo((int) timeStart);
        }
    }

}