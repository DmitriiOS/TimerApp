package ru.startandroid.timerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTimeTxt;
    Button timerBtn;
    Boolean timerIsActive = false;
    CountDownTimer countDownTimer;

    public void timerRestart() {
        timerTimeTxt.setText("01:00");
        timerSeekBar.setProgress(60);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        timerBtn.setText("START");
        timerIsActive = false;
    }

    public void timerBtnClicked (View view) {

        if (timerIsActive) {

            timerRestart();

        } else {

            timerIsActive = true;
            timerSeekBar.setEnabled(false);
            timerBtn.setText("STOP");



            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    timeUpdate((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mplayer.start();
                    timerRestart();
                }
            }.start();
        }
    }

    public void timeUpdate(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        String seconds2digits = Integer.toString(seconds);
        String minutes2digits = Integer.toString(minutes);


        if (seconds <= 9) {
            seconds2digits = "0" + seconds2digits;
        }

        if (minutes <= 9) {
            minutes2digits = "0" + minutes2digits;
        }

        timerTimeTxt.setText(minutes2digits + ":" + seconds2digits);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTimeTxt = findViewById(R.id.timerTimeTxt);
        timerBtn = findViewById(R.id.timerBtn);

        timerSeekBar.setMax(300);
        timerSeekBar.setProgress(60);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                timeUpdate(progress);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
