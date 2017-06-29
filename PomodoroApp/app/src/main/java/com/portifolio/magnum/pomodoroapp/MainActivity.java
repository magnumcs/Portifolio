package com.portifolio.magnum.pomodoroapp;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private TextView pomodoroTimer;
    private ImageButton pomodoroStart, pomodoroPause, pomodoroCount;
    private ProgressBar barTimer;
    private CountDownTimer countDownTimer;
    private TextView txtIntervalo;
    private int minutoTimer;
    private int contador, contadorFinal;
    private ImageView tomate01, tomate02, tomate03, tomate04;
    private int min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pomodoroTimer = (TextView) findViewById(R.id.pomodoro_timer);
        barTimer = (ProgressBar) findViewById(R.id.progress_bar_timer);
        barTimer.setMax(1500);
        barTimer.setProgress(1500);

        Animation an = new RotateAnimation(0.0f, 90.0f, 250f, 273f);
        an.setFillAfter(true);
        barTimer.startAnimation(an);

        Intent intent = getIntent();
        int hora = intent.getIntExtra("hora", 0);
        int minuto = intent.getIntExtra("minuto", 25);
        boolean pressed = intent.getBooleanExtra("pressed", false);
        min = (hora * 60) + minuto;
        if (pressed) {
            pomodoroTimer.setText(String.valueOf(min) + ": 00");
        } else {
            pomodoroTimer.setText("25:00");
        }

        pomodoroStart = (ImageButton) findViewById(R.id.start_button);
        pomodoroPause = (ImageButton) findViewById(R.id.pause_button);
        pomodoroCount = (ImageButton) findViewById(R.id.count_button);

        tomate01 = (ImageView) findViewById(R.id.tomate01);
        tomate02 = (ImageView) findViewById(R.id.tomate02);
        tomate03 = (ImageView) findViewById(R.id.tomate03);
        tomate04 = (ImageView) findViewById(R.id.tomate04);

        tomate01.setVisibility(View.GONE);
        tomate02.setVisibility(View.GONE);
        tomate03.setVisibility(View.GONE);
        tomate04.setVisibility(View.GONE);

        txtIntervalo = (TextView) findViewById(R.id.txt_intervalo);

        txtIntervalo.setVisibility(View.GONE);

        pomodoroPause.setEnabled(false);

        pomodoroCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pomodoroCount.setOnCreateContextMenuListener(contextMenuListener);
            }
        });

        pomodoroTimer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setTimer(v);
                return false;
            }
        });

        pomodoroStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean pressed;
                pomodoroPause.setEnabled(true);
                pomodoroStart.setEnabled(false);

                Intent intent = getIntent();
                int hora = intent.getIntExtra("hora", 0);
                int minuto = intent.getIntExtra("minuto", 25);
                pressed = intent.getBooleanExtra("pressed", false);

                min = (hora * 60) + minuto;

                if (pressed) {
                    startTimer(min);
                } else {
                    pomodoroTimer.setText("25:00");
                    minutoTimer = 25;
                    startTimer(minutoTimer);
                }
            }
        });

        pomodoroPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                pomodoroStart.setEnabled(true);
                Snackbar snackbar = Snackbar.make(v, "Timer foi pausado. A tarefa será reiniciada!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.argb(255, 135, 12, 18));
                snackbar.show();
                pomodoroTimer.setText("--:--");
            }
        });
    }

    private View.OnCreateContextMenuListener contextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(1, 10, 1, "Pomodoros/Dia");
            menu.add(1, 20, 2, "Sobre");
        }
    };

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        long date;
        SimpleDateFormat sdf;
        StringBuilder info;
        switch (item.getItemId()) {
            case 10:
                date = System.currentTimeMillis();
                sdf = new SimpleDateFormat("d/M/yyyy");
                String dateString = sdf.format(date);
                info = new StringBuilder();
                info.append("\nData: "+dateString);
                info.append("\nPomodoros: "+contadorFinal);
                info.append("\n");
                Util.showPomodoroCount(MainActivity.this, "Pomodoros/Dia", info.toString());
                break;

            case 20:
                info = new StringBuilder();
                info.append("\nNome: Pomodoro App");
                info.append("\nVersão: v1.0");
                info.append("\nAutor: Magnum Adelano");
                info.append("\nEmail: magnum.adelano@gmail.com");
                info.append("\nTelefone: (67) 98141-6494");
                info.append("\n");
                Util.showPomodoroCount(MainActivity.this, "Sobre", info.toString());
                break;
        }
        return true;
    }

    private void startTimer(final int minuto){
        countDownTimer = new CountDownTimer(60 * minuto * 1000, 500) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                barTimer.setProgress((int)seconds);
                pomodoroTimer.setText(String.format("%02d", seconds/60) + ":" + String.format("%02d", seconds%60));
            }

            @Override
            public void onFinish() {
                if(pomodoroTimer.getText().equals("00:00")){
                    if(txtIntervalo.getVisibility() == View.GONE){
                        geraTomate(contador);
                        if(contador == 4){
                            StringBuilder info = new StringBuilder();
                            info.append("Deseja alterar o intevalo para 10 minutos?");
                            Util.showPomodoroConfirm(MainActivity.this, "Acréscimo de intervalo", info.toString(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startTimer(10);
                                    txtIntervalo.setVisibility(View.VISIBLE);
                                    gerarNotificacao();
                                    contador = 0;
                                }
                            });
                        }
                        else{
                            startTimer(5);
                            gerarNotificacao();
                        }
                        txtIntervalo.setVisibility(View.VISIBLE);
                        contador++;
                        contadorFinal++;
                        geraTomate(contador);
                    }
                    else if(txtIntervalo.getVisibility() == View.VISIBLE){
                        txtIntervalo.setVisibility(View.GONE);
                        startTimer(minutoTimer);
                        gerarNotificacao();
                    }
                }
                else{
                    pomodoroTimer.setText(String.valueOf(min) + ": 00");
                    barTimer.setProgress(60*minuto);
                }
            }
        }.start();
    }

    private void gerarNotificacao(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(300);
        Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone toque = RingtoneManager.getRingtone(getApplicationContext(), som);
        toque.play();
    }

    private void geraTomate(int contador){
        if(contador == 0){
            tomate01.setVisibility(View.GONE);
            tomate02.setVisibility(View.GONE);
            tomate03.setVisibility(View.GONE);
            tomate04.setVisibility(View.GONE);
        }
        if(contador == 1){
            tomate01.setVisibility(View.VISIBLE);
        }
        if(contador == 2){
            tomate02.setVisibility(View.VISIBLE);
        }
        if(contador == 3){
            tomate03.setVisibility(View.VISIBLE);
        }
        if(contador == 4){
            tomate04.setVisibility(View.VISIBLE);
        }
    }

    private void setTimer(View view){
        DialogFragment dialogFragment = new TimerPickerFragment();
        dialogFragment.show(getFragmentManager(), "Novo Timer");
    }
}
