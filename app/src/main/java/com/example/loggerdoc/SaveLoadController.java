package com.example.loggerdoc;

import android.content.Context;

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


    public static void saveDataToDisk(Context context, UserList userList, String FILENAME) {
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
    public static void loadDataFromDisk(Context context, String FILENAME) {
        try {

            InputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson(); // Library to save objects
            Type listType = new TypeToken<UserList>(){}.getType();

            ActivityLogin.userList = gson.fromJson(in, UserList.class);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
        }
    }
}

