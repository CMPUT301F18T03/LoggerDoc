package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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

            showAlertDialog("Error: Empty Fields","Please fill in the fields indicated by the red stars");
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
            showAlertDialog("Error: Too Long Title","The title can be a maximum of 30 characters. Please shorten it");
        }

        //check if the description is too long
        if (!problem.checkDescriptionLength(problem.getDescription())){
            showAlertDialog("Error: Too Long Description","The description can be a maximum of 300 characters. Please shorten it");
        }

        //TODO: Add Problem to the User's Problem List
        changeActivity(v);
    }

    public boolean checkEmptyString(String string){
        if (string.length() == 0){
            return true;
        }
        return false;
    }

    private void showAlertDialog( String title, String message){
        Bundle messageArgs = new Bundle();
        messageArgs.putString(DialogProblem.TITLE_ID, title);
        messageArgs.putString(DialogProblem.MESSAGE_ID, message);

        DialogProblem dialog = new DialogProblem();
        dialog.setArguments(messageArgs);
        dialog.show(getSupportFragmentManager(), "error_dialog");
    }

    public void changeActivity(View v){
        Intent intent = new Intent(this, ActivityBrowseProblems.class);
        startActivity(intent);
    }
}
