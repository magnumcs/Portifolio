package com.portifolio.magnum.pomodoroapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by magnum on 12/13/16.
 */
public class TimerPickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private int minuto;

    public TimerPickerFragment(){

    }


    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        minuto = args.getInt("minuto");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.MINUTE);
        int minute = c.get(Calendar.SECOND);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),R.style.MyDialogTheme,this, hour, minute,
                true);

        timePickerDialog.setTitle("");

        timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", timePickerDialog);

        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hora, int minuto) {

        TextView textView = (TextView) getActivity().findViewById(R.id.pomodoro_timer);

        boolean pressed = true;

        int min = (hora * 60) + minuto;

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("hora", hora);
        intent.putExtra("minuto", minuto);
        intent.putExtra("pressed", pressed);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        getActivity().finish();
        startActivity(intent);

        textView.setText(String.valueOf(min) + ": 00");

    }


}
