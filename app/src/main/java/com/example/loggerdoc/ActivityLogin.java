package com.example.loggerdoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    UserList tempUserList = new UserList();
    EditText userID = (EditText) findViewById(R.id.Username_Field);


    // If user hits the login button
    public void login(View view) {
        String userLogin = userID.getText().toString();
        if (loginCheck(userLogin)) {
            // TODO: check the type of account and go to proper activity
        }
    }

    public boolean verifyUsername(String id){
    }


    public void createAccount(final String emotion) {

        LayoutInflater layoutInflater = LayoutInflater.from(ActivityLogin.this);
        View dialogView = layoutInflater.inflate(R.layout.account_creation_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLogin.this);
        builder.setView(dialogView);

        builder.setTitle("Account Creation");
        final EditText userID = (EditText) dialogView.findViewById(R.id.userID);
        final EditText userEmail = (EditText) dialogView.findViewById(R.id.userEmailAddress);
        final EditText userPhoneNumber = (EditText) dialogView.findViewById(R.id.userPhoneNumber);


        // Triggered when the user clicks on the Patient button
        builder.setPositiveButton("Patient", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String username = userID.getText().toString();
                String emailAddress = userEmail.getText().toString();
                String phoneNumber = userPhoneNumber.getText().toString();

                getEmotionList().addEmotion(new Emotion(emotion, date, user_comment));
                saveList();

            }
        });

        builder.setPositiveButton("Caregiver", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String username = userID.getText().toString();
                String emailAddress = userEmail.getText().toString();
                String phoneNumber = userPhoneNumber.getText().toString();

                getEmotionList().addEmotion(new Emotion(emotion, date, user_comment));
                saveList();
        builder.show();
    }
    }
    }
}