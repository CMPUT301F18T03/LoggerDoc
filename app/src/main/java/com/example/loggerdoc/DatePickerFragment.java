package com.example.loggerdoc;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private int year;
    private int month;
    private int day;
    private Boolean isSet = false;
    private DialogFragment nextFragment;

    public static DatePickerFragment newInstance(@NonNull LocalDateTime date){
        DatePickerFragment datePickerFragment = new DatePickerFragment();

        Bundle message = new Bundle();
        message.putInt("YEAR", date.getYear());
        message.putInt("MONTH", date.getMonthValue());
        message.putInt("DAY", date.getDayOfMonth());
        datePickerFragment.setArguments(message);

        return datePickerFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog (Bundle saved){
        return new DatePickerDialog(getActivity(), this, getArguments().getInt("YEAR"),
                getArguments().getInt("MONTH")-1, getArguments().getInt("DAY"));
    }

    @Override
    public void onDateSet (DatePicker view, int year, int month, int dayofMonth) {
        this.year = year;
        this.month = month+1;
        this.day = dayofMonth;
        this.isSet = true;

        if(nextFragment != null){
            nextFragment.show(getActivity().getSupportFragmentManager(),"TimeFragment");
        }
    }

    public void setNextFragment(DialogFragment NextFragment){
        this.nextFragment = NextFragment;
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
