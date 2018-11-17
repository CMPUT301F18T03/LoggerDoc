package com.example.loggerdoc;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


public class ActivityAddProblem extends AppCompatActivity {
    private Patient patient;
    private DatePickerFragment datePicker;
    private EditText problemTitle;
    private EditText problemDescription;
    private ImageView problemTitleWarning;
    private ImageView problemDescriptionWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
    }

    @Override
    protected void onResume(){
        super.onResume();

        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("Patient");
        datePicker = new DatePickerFragment();

        problemTitle = (EditText) findViewById(R.id.problem_Title_Text);
        problemDescription = (EditText) findViewById(R.id.problem_desc_text);
        problemTitleWarning = (ImageView) findViewById(R.id.warning_Problem_Title);
        problemDescriptionWarning = (ImageView) findViewById(R.id.warning_Problem_Desc);

    }

    //To be called when the user hits the create button
    public void checkProblemFields (View v){

        //Check if the problemTitle and if the problemDescription is empty
        boolean emptyTitle = checkEmptyString(problemTitle.getText().toString());
        boolean emptyDescription = checkEmptyString(problemDescription.getText().toString());

        //If either of the problemTitle or the problemDescription is empty
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
            //Turn flags off
            problemTitleWarning.setVisibility(View.INVISIBLE);
            problemDescription.setVisibility(View.INVISIBLE);

            Problem problem = new Problem (problemTitle.getText().toString(), datePicker, problemDescription.getText().toString());

            //Check if the title is too long or description is too long
            if (!problem.checkTitleLength(problem.getTitle()) || !problem.checkDescriptionLength(problem.getDescription()) ){

                if (!problem.checkTitleLength(problem.getTitle())) {
                    showAlertDialog("Error: Too Long Title", "The title can be a maximum of 30 characters. Please shorten it");
                }

                if (!problem.checkDescriptionLength(problem.getDescription())){
                    showAlertDialog("Error: Too Long Description","The description can be a maximum of 300 characters. Please shorten it");

                }
            }

            else {
                //Add problem to patient's problem list
                patient.getProblems().add(problem);
                changeActivity(v);
            }
        }
    }

    /*
     * Check if the string is empty. Takes a string as a parameter and returns true if empty.
     * Returns false otherwise.
     */
    public boolean checkEmptyString(String string){
        if (string.length() == 0){
            return true;
        }
        return false;
    }

    //Show the Date Picker
    public void clickPickDate(View v){
        datePicker.show(getSupportFragmentManager(), "pick_date");
    }

    //Show an error Alert Dialog.
    private void showAlertDialog( String title, String message){
        Bundle messageArgs = new Bundle();
        messageArgs.putString(DialogProblem.TITLE_ID, title);
        messageArgs.putString(DialogProblem.MESSAGE_ID, message);

        DialogProblem dialog = new DialogProblem();
        dialog.setArguments(messageArgs);
        dialog.show(getSupportFragmentManager(), "error_dialog");
    }

    //Change the activity to ActivityBrowseProblems
    public void changeActivity(View v){
        Intent intent = new Intent(this, ActivityBrowseProblems.class);
        intent.putExtra("Patient", patient);
        startActivity(intent);
    }
}
