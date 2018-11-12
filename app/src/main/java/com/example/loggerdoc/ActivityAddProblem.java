package com.example.loggerdoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.time.LocalDateTime;

public class ActivityAddProblem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
    }

    //To be called when the user hits the create button
    public void checkProblemFields (View v){
        EditText problemTitle = (EditText) findViewById(R.id.problem_Title_Text);
        EditText problemDate = (EditText) findViewById(R.id.problem_start_date_text);
        EditText problemDescription = (EditText) findViewById(R.id.problem_desc_text);

        ImageView problemTitleWarning = (ImageView) findViewById(R.id.warning_Problem_Title);
        ImageView problemDescriptionWarning = (ImageView) findViewById(R.id.warning_Problem_Desc);

        //Check if the problemTitle and if the problemDescription is empty
        boolean emptyTitle = checkEmptyString(problemTitle.getText().toString());
        boolean emptyDescription = checkEmptyString(problemDescription.getText().toString());

        if (emptyTitle || emptyDescription){

            //Set the flag for the user to indicate the Title Field is Empty
            if (emptyTitle){
                problemTitleWarning.setVisibility(View.VISIBLE);
            }

            //Set the flag for the user to indicate the Description Field is Empty
            if (emptyDescription){
                problemDescriptionWarning.setVisibility(View.VISIBLE);
            }

            emptyFieldAlertDialog();
        }

        else{
            problemTitleWarning.setVisibility(View.INVISIBLE);
            problemDescription.setVisibility(View.INVISIBLE);
            createProblem(problemTitle.getText().toString(), problemDate.getText().toString(), problemDescription.toString(), v);
        }
    }

    private void createProblem(String problemTitle, String problemDate, String problemDescription, View v){

        Problem problem = new Problem (problemTitle, problemDate,problemDescription);

        //check if the title is too long
        if (!problem.checkTitleLength(problem.getTitle())){
            tooLongTitleAlertDialog();
        }

        //check if the description is too long
        if (!problem.checkDescriptionLength(problem.getDescription())){
            tooLongDescriptionAlertDialog();
        }

        changeActivity(v);
    }

    public boolean checkEmptyString(String string){
        if (string.length() == 0){
            return true;
        }
        return false;
    }

    private void emptyFieldAlertDialog(){

        LayoutInflater layoutInflater = LayoutInflater.from(ActivityAddProblem.this);
        final View dialogView = layoutInflater.inflate(R.layout.account_creation_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddProblem.this);
        builder.setView(dialogView);

        builder.setTitle("Error: Empty Fields");
        builder.setMessage("Please fill in the fields indicated by the red stars");

        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private void tooLongTitleAlertDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(ActivityAddProblem.this);
        final View dialogView = layoutInflater.inflate(R.layout.account_creation_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddProblem.this);
        builder.setView(dialogView);

        builder.setTitle("Error: Too Long Title");
        builder.setMessage("The title can be a maximum of 30 characters. Please shorten it.");

        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private void tooLongDescriptionAlertDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(ActivityAddProblem.this);
        final View dialogView = layoutInflater.inflate(R.layout.account_creation_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddProblem.this);
        builder.setView(dialogView);

        builder.setTitle("Error: Too Long Description");
        builder.setMessage("The title can be a maximum of 300 characters. Please shorten it.");

        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    public void changeActivity(View v){
        Intent intent = new Intent(this, ActivityBrowseProblems.class);
        startActivity(intent);
    }
}
