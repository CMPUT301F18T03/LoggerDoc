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

public class ActivityEditProblem extends AppCompatActivity {

    private EditText editTitle;
    private EditText editDate;
    private EditText editDescription;
    private Problem problem;
    private ImageView problemTitleWarning;
    private ImageView problemDescriptionWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem);
    }

    @Override
    protected void onResume (){
        super.onResume();

        editTitle = (EditText) findViewById(R.id.editTitle);
        editDate = (EditText) findViewById(R.id.edit_problem_date_pick);
        editDescription = (EditText) findViewById(R.id.edit_prob_desc);
        problemTitleWarning = (ImageView) findViewById(R.id.warningEditTitle);
        problemDescriptionWarning = (ImageView) findViewById(R.id.warningEditDesc);

        Intent intent = getIntent();
        problem = (Problem) intent.getSerializableExtra("problemToEdit");

        editTitle.setText(problem.getTitle());
        editDate.setText(problem.getTimestamp().toString());
        editDescription.setText(problem.getDescription());
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
            emptyFieldAlertDialog();
        }

        else {
            problem.setTitle(editTitle.getText().toString());
            problem.setTimestamp(editDate.getText().toString());
            problem.setDescription(editDescription.getText().toString());

            //check if the title is too long
            if (!problem.checkTitleLength(problem.getTitle())){
                tooLongTitleAlertDialog();
            }

            //check if the description is too long
            if (!problem.checkDescriptionLength(problem.getDescription())){
                tooLongDescriptionAlertDialog();
            }

            //TODO: Add Problem to the User's Problem List
            changeActivity(v);
        }
    }

    public boolean checkEmptyString(String string){
        if (string.length() == 0){
            return true;
        }
        return false;
    }

    private void emptyFieldAlertDialog(){

        LayoutInflater layoutInflater = LayoutInflater.from(ActivityEditProblem.this);
        final View dialogView = layoutInflater.inflate(R.layout.account_creation_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityEditProblem.this);
        builder.setView(dialogView);
        Bundle messageArgs = new Bundle();
        messageArgs.putString(DialogAddProblem.TITLE_ID, "Error: Empty Fields");
        messageArgs.putString(DialogAddProblem.MESSAGE_ID,"Please fill in the fields indicated by the red stars");
        builder.setMessage("Please fill in the fields indicated by the red stars");

        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private void tooLongTitleAlertDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(ActivityEditProblem.this);
        final View dialogView = layoutInflater.inflate(R.layout.account_creation_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityEditProblem.this);
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
        LayoutInflater layoutInflater = LayoutInflater.from(ActivityEditProblem.this);
        final View dialogView = layoutInflater.inflate(R.layout.account_creation_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityEditProblem.this);
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
