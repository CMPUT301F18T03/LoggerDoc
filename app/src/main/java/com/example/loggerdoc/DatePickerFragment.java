package com.example.loggerdoc;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private int year;
    private int month;
    private int day;
    private Boolean isSet;

    public DatePickerFragment(){
        isSet = false;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog (Bundle saved){
        final Calendar calendar = Calendar.getInstance();
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, this.year, this.month, this.day);
    }

    @Override
    public void onDateSet (DatePicker view, int year, int month, int dayofMonth) {
        this.year = year;
        this.month = month+1;
        this.day = dayofMonth;

        this.isSet = true;
    }

    public boolean getSet(){
        return this.isSet;
    }

    public int getDay(){

        if (isSet){
            return this.day;
        }
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth(){
        return this.month;
    }

    public int getYear(){
        return this.year;
    }

}
