package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.time.LocalDateTime;

/*
 * This class displays the problem that the user has selected and it allows them to edit the title,
 * starting date and the comment.
 */

public class ActivityEditProblem extends AppCompatActivity {

    private EditText editTitle;
    private EditText editDescription;
    private Problem problem;
    private int problemID;
    private ImageView problemTitleWarning;
    private ImageView problemDescriptionWarning;
    private DatePickerFragment datePickerFragment;
    private TimePickerFragment timePickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem);
    }

    @Override
    protected void onResume (){
        super.onResume();

        Intent intent = getIntent();
        problemID = intent.getIntExtra("Problem",0);
        problem = ProblemRecordListController.getProblemList().get(problemID);

        editTitle = (EditText) findViewById(R.id.editTitle);

        editDescription = (EditText) findViewById(R.id.edit_prob_desc);
        problemTitleWarning = (ImageView) findViewById(R.id.warningEditTitle);
        problemDescriptionWarning = (ImageView) findViewById(R.id.warningEditDesc);

        //Set the EditText's boxes to the appropriate attributes from the problem
        editTitle.setText(problem.getTitle());
        editDescription.setText(problem.getDescription());

        datePickerFragment = DatePickerFragment.newInstance(problem.getTimestamp());
        timePickerFragment = TimePickerFragment.newInstance(problem.getTimestamp());
        datePickerFragment.setNextFragment(timePickerFragment);

    }
    
    /**
     * @author Alexandra Tyrrell
     *
     * This method checks whether we have all the fields that are needed to update the problem. If
     * not, there will be an appropriate error dialog shown. The method will then update the problem
     * in the problem list.
     *
     * @param v View
     */
    public void updateProblem (View v){

        //Check if the problemTitle and if the problemDescription is empty
        boolean emptyTitle = checkEmptyString(editTitle.getText().toString());
        boolean emptyDescription = checkEmptyString(editDescription.getText().toString());

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

        else {
            //Update the problem's attributes
            problem.setTitle(editTitle.getText().toString());
            problem.setTimestamp(formatDateAndTime(datePickerFragment,timePickerFragment));
            problem.setDescription(editDescription.getText().toString());

            //Turn off the flags
            problemTitleWarning.setVisibility(View.INVISIBLE);
            problemDescriptionWarning.setVisibility(View.INVISIBLE);

            //check if the title is too long
            if (!problem.checkTitleLength(problem.getTitle())){
                showAlertDialog("Error: Too Long Title","The title can be a maximum of 30 characters. Please shorten it");
            }

            //check if the description is too long
            else if (!problem.checkDescriptionLength(problem.getDescription())){
                showAlertDialog("Error: Too Long Description","The description can be a maximum of 300 characters. Please shorten it");
            }

            else {
                //Update the problem's list
                ProblemRecordListController.getProblemList().update(problem,getApplicationContext());
                changeActivity(v);
            }
        }
    }

    /**
     * @author Alexandra Tyrrell
     *
     * This method checks whether a string is empty or not. It returns true if the string is empty
     * and false otherwise.
     *
     * @param string String
     */
    public boolean checkEmptyString(String string){
        if (string.length() == 0){
            return true;
        }
        return false;
    }

    /**
     * @author Alexandra Tyrrell
     *
     * Show the Date and Time Picker.
     *
     * @param v View
     */
    public void clickPickDate(View v){
        datePickerFragment.show(getSupportFragmentManager(), "Edit Date Fragment");
    }

    /**
     * @author Alexandra Tyrrell
     *
     * Uses the Dialog Problem class to show an error dialog with the specified string and message.
     *
     * @param title String
     * @param message String
     */
    private void showAlertDialog( String title, String message){
        Bundle messageArgs = new Bundle();
        messageArgs.putString(DialogProblem.TITLE_ID, title);
        messageArgs.putString(DialogProblem.MESSAGE_ID, message);

        DialogProblem dialog = new DialogProblem();
        dialog.setArguments(messageArgs);
        dialog.show(getSupportFragmentManager(), "error_dialog");
    }

    /**
     * @author = Alexandra Tyrrell
     *
     * The method will change the Activity to Activity View Problem.
     *
     * @param v View
     */
    public void changeActivity(View v){
        Intent intent = new Intent(this, ActivityViewProblem.class);
        intent.putExtra("Position", problemID);
        finish();
    }

    /**
     * @author = Alexandra Tyrrell
     * Sets the timestamp of the problem. This method creates a LocalDateTime object that stores the
     * date of the problem. The day, month and year is taken from the datePickerFragment.
     *
     * @param datePickerFragment the object that holds the date of the problem
     */
    public LocalDateTime formatDateAndTime (DatePickerFragment datePickerFragment,
                                            TimePickerFragment timePickerFragment){

        LocalDateTime date = LocalDateTime.now();

        if(datePickerFragment.getSet()){
            date = date.withDayOfMonth(datePickerFragment.getDay())
                    .withMonth(datePickerFragment.getMonth()).withYear(datePickerFragment.getYear());
        }

        if (timePickerFragment.getIsSet()){
            date = date.withHour(timePickerFragment.getHour())
                    .withMinute(timePickerFragment.getMinute());
        }

        return date;
    }

}
