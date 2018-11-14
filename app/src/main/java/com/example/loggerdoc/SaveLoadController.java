package com.example.loggerdoc;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SaveLoadController {

    /**
     * @author Dylan
     * @param context context is the activity the method is being called from. ex: ActivityLogin.this
     * @param patient patient is a Patient object that needs to be saved to disk, patient will contain its list of problems and list of caregivers
     */
    public static void savePatientToDisk(Context context, Patient patient) {
        try {
            OutputStream fos = context.openFileOutput(patient.getUserID()+".sav", Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(patient, out);
            out.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @author Dylan
     * @param context context is the activity the method is being called from. ex: ActivityLogin.this
     * @param userID userID is a the Users username this is logging in/being looked up to load their profile
     * @return returns a Patient object to be passed into PatientHomepageActivity
     */
    public static Patient loadPatientFromDisk(Context context, String userID) {
        try {

            InputStream fis = context.openFileInput(userID+".sav");
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson(); // Library to save objects

            Patient patient = gson.fromJson(in, Patient.class);
            return patient;

        } catch (FileNotFoundException e) {
            Toast.makeText(context, "This account does not exist! Please create a new account!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    /**
     * @author Dylan
     * @param context context is the activity the method is being called from. ex: ActivityLogin.this
     * @param careGiver patient is a Patient object that needs to be saved to disk, patient will contain its list of problems and list of caregivers
     */
    public static void saveCareGiverToDisk(Context context, CareGiver careGiver) {
        try {
            OutputStream fos = context.openFileOutput(careGiver.getUserID()+".sav", Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(careGiver, out);
            out.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @author Dylan
     * @param context context is the activity the method is being called from. ex: ActivityLogin.this
     * @param userID userID is a the Users username this is logging in/being looked up to load their profile
     * @return returns a Patient object to be passed into PatientHomepageActivity
     */
    public static CareGiver loadCareGiverFromDisk(Context context, String userID) {
        try {

            InputStream fis = context.openFileInput(userID+".sav");
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson(); // Library to save objects

            CareGiver careGiver = gson.fromJson(in, CareGiver.class);
            return careGiver;

        } catch (FileNotFoundException e) {
            Toast.makeText(context, "This account does not exist! Please create a new account!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    /**
     *
     * @param context context is the activity the method is being called from. ex: ActivityLogin.this
     * @param userList userList is the current userlist that you want to save. This is used to check if a usernname has been taken
     * @param FILENAME The file that the userlist will be saved too. This will be a globally accessed file
     */
    public static void saveUserListToDisk(Context context, UserList userList, String FILENAME) {
        try {
            OutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(userList, out);
            out.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * @param context context is the activity the method is being called from. ex: ActivityLogin.this
     * @param FILENAME The file that the userlist will be saved too. This will be a globally accessed file
     */
    public static void loadUserListFromDisk(Context context, String FILENAME) {
        try {

            InputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson(); // Library to save objects

            ActivityLogin.userList = gson.fromJson(in, UserList.class);

        } catch (FileNotFoundException e) {
            Toast.makeText(context, "This File could not be found!", Toast.LENGTH_SHORT).show();
        }
    }

}

