/*

 */

package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class ActivityEditProblem extends AppCompatActivity {

    private EditText editTitle;
    private EditText editDate;
    private EditText editDescription;
    private Patient patient;
    private Problem problem;
    private int position;
    private ImageView problemTitleWarning;
    private ImageView problemDescriptionWarning;
    private DatePickerFragment datePickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem);
    }

    @Override
    protected void onResume (){
        super.onResume();

        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("Patient");
        position = (int) intent.getSerializableExtra("Position");
        problem = patient.getProblems().getProblemArrayList().get(position);

        editTitle = (EditText) findViewById(R.id.editTitle);
        editDate = (EditText) findViewById(R.id.edit_problem_date_pick);
        editDescription = (EditText) findViewById(R.id.edit_prob_desc);
        problemTitleWarning = (ImageView) findViewById(R.id.warningEditTitle);
        problemDescriptionWarning = (ImageView) findViewById(R.id.warningEditDesc);


        editTitle.setText(problem.getTitle());
        editDate.setText(problem.getTimestamp().toString());
        editDescription.setText(problem.getDescription());

        datePickerFragment = new DatePickerFragment();
    }

    public void updateProblem (View v){

        //Check if the problemTitle and if the problemDescription is empty
        boolean emptyTitle = checkEmptyString(editTitle.getText().toString());
        boolean emptyDescription = checkEmptyString(editDescription.getText().toString());

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

        else {
            problem.setTitle(editTitle.getText().toString());
            problem.setTimestamp(datePickerFragment);
            problem.setDescription(editDescription.getText().toString());

            problemTitleWarning.setVisibility(View.INVISIBLE);
            problemDescriptionWarning.setVisibility(View.INVISIBLE);

            //check if the title is too long
            if (!problem.checkTitleLength(problem.getTitle())){
                showAlertDialog("Error: Too Long Title","The title can be a maximum of 30 characters. Please shorten it");
            }

            //check if the description is too long
            if (!problem.checkDescriptionLength(problem.getDescription())){
                showAlertDialog("Error: Too Long Description","The description can be a maximum of 300 characters. Please shorten it");
            }

            patient.getProblems().remove(problem);
            patient.getProblems().add(problem);

            changeActivity(v);
        }
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
        Intent intent = new Intent(this, ActivityViewProblem.class);
        intent.putExtra("Patient", patient);
        intent.putExtra("Position", position);
        startActivity(intent);
    }

}
