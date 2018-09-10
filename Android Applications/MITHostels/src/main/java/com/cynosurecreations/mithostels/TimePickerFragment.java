package com.cynosurecreations.mithostels;

/**
 * Created by Shiva on 23/07/16.
 */
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

public class TimePickerFragment extends DialogFragment {

    TimePickerDialog.OnTimeSetListener ontimeSet;

    public TimePickerFragment() {
    }

    public void setCallBack(TimePickerDialog.OnTimeSetListener ondate) {
        ontimeSet = ondate;
    }

    private int hour, min;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        hour = args.getInt("hour");
        min = args.getInt("min");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), ontimeSet, hour, min, DateFormat.is24HourFormat(getActivity()));
    }
}
