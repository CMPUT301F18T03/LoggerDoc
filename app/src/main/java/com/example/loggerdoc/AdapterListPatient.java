/* Created 2018-11-13 by Stephen Zuk
 *
 *  The patient list adapter is a custom array adapter used to display some of the field variables
 *  in the patient class using a custom layout made with and xml file
 *
 */


package com.example.loggerdoc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterListPatient extends ArrayAdapter<Patient> {
    //custom adapter  built to display the data of a patient in the patient list

    private Context adapterContext;
    private int adapterResource;

    public AdapterListPatient(Context context, int resource, ArrayList<Patient> patients){
        super(context, resource, patients);
        adapterContext = context;
        adapterResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup Parent){
        //This method gets the values of userID, emailAddress and phoneNumber from each
        //patient in the list and displays them in the custom xml layout patient_listview_layout

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
