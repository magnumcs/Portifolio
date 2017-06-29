package com.portifolio.magnum.pomodoroapp;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by magnum on 12/10/16.
 */
public class PomodoroCountDownTimer extends CountDownTimer {

    private Context context;
    private TextView textView;
    private long tempoFuturo;
    private long intervalo;
    private ProgressBar barTime;

    public PomodoroCountDownTimer(Context context, TextView textView, long tempoFuturo, long intervalo) {
        super(tempoFuturo, intervalo);
        this.context = context;
        this.textView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {

        tempoFuturo = millisUntilFinished;
        textView.setText(getCorrectTimer(true, millisUntilFinished) + ":" + getCorrectTimer(false, millisUntilFinished));

    }

    @Override
    public void onFinish() {
        tempoFuturo -= 1000;
        barTime.setProgress((int) tempoFuturo);
        textView.setText(getCorrectTimer(true, tempoFuturo)+":"+getCorrectTimer(false, tempoFuturo));
        Toast.makeText(context, "Finish!", Toast.LENGTH_SHORT).show();

    }

    private String getCorrectTimer(boolean isMinute, long millisUntilFinished){
        String aux;
        int constCalendar = isMinute ? Calendar.MINUTE : Calendar.SECOND;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisUntilFinished);

        aux = calendar.get(constCalendar) < 10 ? "0"+calendar.get(constCalendar) : ""+calendar.get(constCalendar);
        return (aux);
    }
}
