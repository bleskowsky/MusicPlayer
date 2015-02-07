package com.example.bleskowsky.musicplayer;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.location.GpsStatus;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.media.MediaPlayer;
import android.widget.Button;


import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    private MediaPlayer player;
    private Button buttonPlay;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();
        buttonPlay = (Button) findViewById(R.id.playButton);
        buttonPlay.setOnClickListener(playMusicListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        // final cleanup when activity is destroyed (for now)
        player.release();
        player = null;
        super.onDestroy();
    }

    private void playMusic() {
        try {
            String url = "http://itori.animenfo.com:443/";
            buttonPlay.setEnabled(false);
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            player.setDataSource(url);
            player.prepareAsync();

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    buttonPlay.setEnabled(true);
                    player.start();
                    buttonPlay.setText(res.getString(R.string.stop));
                }
            });
        }
        catch (Exception e) {
            Log.e(res.getString(R.string.app_name), "exception", e);
        }
    }

    private void stopMusic() {
        player.pause();
        buttonPlay.setText(res.getString((R.string.play)));

    }

    private View.OnClickListener playMusicListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(player == null) {
                playMusic();
                return;
            }
            if(player.isPlaying()) {
                stopMusic();
            }
            else {
                playMusic();
            }
        }
    };
}
