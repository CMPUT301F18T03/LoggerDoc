package com.example.loggerdoc;

import android.content.DialogInterface;
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
            createProblem(problemTitle.getText().toString(), problemDate.getText().toString(), problemDescription.toString());
        }
    }

    private void createProblem(String problemTitle, String problemDate, String problemDescription){

        Problem problem = new Problem (problemTitle, problemDate,problemDescription);

        //check if the title is too long and the description is too long
        if (!problem.checkTitleLength(problem.getTitle()) && !problem.checkDescriptionLength(problem.getDescription())){
            //TODO: create an alert dialog that says both are too long
        }

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

}
