package com.example.loggerdoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loggerdoc.elasticclient.ElasticDataCallback;
import com.example.loggerdoc.elasticclient.getUsersTask;
import com.example.loggerdoc.elasticclient.modifyUserTask;

import java.util.ArrayList;

public class ActivityLogin extends AppCompatActivity implements ElasticDataCallback<ArrayList<User>>{

    // Local userList to store all of the Users along with all the data associated with users
    static UserList userList = UserListController.getUserList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.Login_Button).setEnabled(false);
        getUsersTask loadUserList = new getUsersTask(this,this);
        loadUserList.mkDirs();
        loadUserList.execute();
    }


    // If user hits the login button
    public void login(View v) {
        final EditText userID = findViewById(R.id.Username_Field);
        String userLogin = userID.getText().toString();

        Log.d("TAG", "edit text = " + userLogin);


        // verify that the user actually exists, if true then proceed with login
        if (verifyUsername(userLogin)) {
            Toast.makeText(this, "WORKS", Toast.LENGTH_SHORT).show();
            for (User user : userList.getArray()) {
                Log.d("TAG", "email = " + user.getEmailAddress());
                if (user.getUserID().equals(userLogin)) {
                    if (user.getRole().equals("Patient")) {
                        Intent intent = new Intent(ActivityLogin.this, ActivityPatientHomePage.class);
                        intent.putExtra("Patient", user.getElasticID());
                        startActivity(intent);
                        break;
                    }
                    else {
                        Intent intent = new Intent(ActivityLogin.this, ActivityCareGiverHomePage.class);
                        intent.putExtra("Caregiver", user.getElasticID());
                        startActivity(intent);
                        break;
                    }
                }
            }
        }
    }



    // When the create account button is pressed from the login screen this method gets run
    // this method will display the create account alert dialog
    public void createAccount(View v) {
        createAccountInfo();
    }


    // need method to check if the username is taken when the user is creating an account
    public boolean verifyUsername(String id) {
        for (User user : userList.getArray()) {
            Log.d("TAG","userID" + user.getUserID());
            if (user.getUserID().equals(id)) {
                Log.d("TAG", "TRUE");
                return true;
            }
        }
        Toast.makeText(this, "That username does not exist! Please try again or create a new account!", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * @source https://stackoverflow.com/questions/1819142/how-should-i-validate-an-e-mail-address
     * @author mindriot
     * @param email email that the user enters during account creation
     * @return True if email is valid, false otherwise
     */
    public final static boolean isValidEmail(CharSequence email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    // This method will get called when the user clicks on create account from the ActivityLogin page.
    // If the user presses the create account button when they do not have internet connection they will be prompted
    // with a toast/error message saying they need internet to create an account
    public void createAccountInfo() {

        LayoutInflater layoutInflater = LayoutInflater.from(ActivityLogin.this);
        final View dialogView = layoutInflater.inflate(R.layout.account_creation_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLogin.this);
        builder.setView(dialogView);

        builder.setTitle("Account Creation");
        final EditText userID = dialogView.findViewById(R.id.userID);
        final EditText userEmail = dialogView.findViewById(R.id.userEmailAddress);
        final EditText userPhoneNumber = dialogView.findViewById(R.id.userPhoneNumber);


        // Triggered when the user clicks on the Patient button
        // TODO: check if the username has already been taken, need elasticsearch
        builder.setPositiveButton("Patient", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String username = userID.getText().toString();
                String emailAddress = userEmail.getText().toString();
                String phoneNumber = userPhoneNumber.getText().toString();

                if (UserListController.findUser(username)) {
                    Toast.makeText(ActivityLogin.this, "That username is already taken! please try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (username.length() < 8) {
                    Toast.makeText(ActivityLogin.this, "Username has to be 8 or more characters. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidEmail(emailAddress)) {
                    Toast.makeText(ActivityLogin.this, "Invalid email address! Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Patient patient = new Patient(username, emailAddress, phoneNumber,"Patient", new CareGiverList());
                //userList.add_internal(patient);
                Toast.makeText(ActivityLogin.this, "Success", Toast.LENGTH_SHORT).show();

                // Save the userlist to disk for creating a new account offline we can check for unique userID
                UserListController.getUserList().add(patient,getApplicationContext());
                //new modifyUserTask(getBaseContext()).execute(patient);//Fire and forget

            }
        });

        // Triggered when the user clicks on the CareGiver button
        // TODO: check whether thee user id is taken, need elasticsearch
        builder.setNegativeButton("Caregiver", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String username = userID.getText().toString();
                String emailAddress = userEmail.getText().toString();
                String phoneNumber = userPhoneNumber.getText().toString();

                if (UserListController.findUser(username)) {
                    Toast.makeText(ActivityLogin.this, "That username is already taken! please try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (username.length() < 8) {
                    Toast.makeText(ActivityLogin.this, "Username has to be 8 or more characters. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidEmail(emailAddress)) {
                    Toast.makeText(ActivityLogin.this, "Invalid email address! Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                CareGiver careGiver = new CareGiver(username, emailAddress, phoneNumber,"Caregiver", new PatientList());
                //userList.add_internal(careGiver);
                UserListController.getUserList().add(careGiver,getApplicationContext());
                Toast.makeText(ActivityLogin.this, "Success", Toast.LENGTH_SHORT).show();
                //new modifyUserTask(getBaseContext()).execute(careGiver);

            }
        });

        // If the user wants to cancel out of the AlertDialog
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void dataCallBack(ArrayList<User> data) {
        UserListController.setList(data);
        Button loginbut = findViewById(R.id.Login_Button);
        loginbut.setEnabled(true);

    }
}