package com.example.projetomidia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    Button btnPlay;
    Button btnPause;
    Button btnStop;
    SeekBar barVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bootStrap();
    }
    public void bootStrap(){
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        barVolume = findViewById(R.id.barVolume);
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.teste);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null)
                    mediaPlayer.start();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null && mediaPlayer.isPlaying())
                    mediaPlayer.pause();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null && mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.teste);
            }
        });

        bootSeekBar();

        // no final do bootStrap
        // para liberar recursos de mídia
        MediaPlayer.OnCompletionListener onCompletionListener = new
                MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        releaseMediaPlayerResources(); // implementar main
                    }
                }; // vai pedir para implementar o método, implementar em main

    }

    protected void bootSeekBar(){
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        int volMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        barVolume.setMax(volMax);

        int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        barVolume.setProgress(volume);

        barVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void releaseMediaPlayerResources() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release(); // libera recursos da memória
        }
    }

    @Override
    protected void onStop() { // se o aplicativo parar (home p.ex)
        releaseMediaPlayerResources();
        super.onStop();
    }
}
