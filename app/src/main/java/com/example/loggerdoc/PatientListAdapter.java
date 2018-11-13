package com.example.loggerdoc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PatientListAdapter extends ArrayAdapter<Patient> {
    //custom adapter  built to display the data of a patient in the patient list

    private Context adapterContext;
    private int adapterResource;
    public PatientListAdapter(Context context, int resource, ArrayList<Patient> patients){
        super(context, resource, patients);
        adapterContext = context;
        adapterResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup Parent){
        String patientID = getItem(position).getUserID();
        String patientEmail = getItem(position).getEmailAddress();
        String patientPhoneNum = getItem(position).getPhoneNumber();

        LayoutInflater inflater = LayoutInflater.from(adapterContext);
        convertView = inflater.inflate(adapterResource, Parent, false);

        TextView patientIDTextView = (TextView) convertView.findViewById(R.id.PatientListUserID);
        TextView emailTextView = (TextView) convertView.findViewById(R.id.PatientListUserEmail);
        TextView phoneTextView = (TextView) convertView.findViewById(R.id.PatientListUserPhone);

        patientIDTextView.setText(patientID);
        emailTextView.setText(patientEmail);
        phoneTextView.setText(patientPhoneNum);

        return convertView;
    }
}
