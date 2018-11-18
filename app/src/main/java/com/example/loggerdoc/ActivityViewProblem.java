package com.example.loggerdoc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityViewProblem extends AppCompatActivity {

    private Patient patient;
    private Problem problem;
    private int position;
    private ArrayAdapter<Record> recordAdapter;
    private ArrayAdapter<CaregiverComment> commentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problem);
    }

    @Override
    protected void onResume(){
        super.onResume();

        //Set the patient and the problem
        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("Patient");
        position = (int) intent.getSerializableExtra("Position");
        problem = patient.getProblems().getProblemArrayList().get(position);

        TextView problemTitleView = (TextView) findViewById(R.id.UpdateContactInfoUsernameView);
        problemTitleView.setText(problem.getTitle());

        TextView problemDateView = (TextView) findViewById(R.id.dateProblemView);
        problemDateView.setText(problem.getTimestamp().toString());

        TextView problemDescriptionView = (TextView) findViewById(R.id.descriptionProblemView);
        problemDescriptionView.setText(problem.getDescription());

        //Initialize and set the adapter for the records
        recordAdapter = new AdapterListRecords(this, problem.getRecordList().getRecordArrayList());
        ListView recordList = (ListView) findViewById(R.id.recordsListView);
        recordList.setAdapter(recordAdapter);
        recordAdapter.notifyDataSetChanged();

        //Initialize and set the adapter for the caregiver's comments
        commentAdapter = new AdapterListComments(this, problem.getCommentList().getComments());
        ListView commentList = (ListView) findViewById(R.id.commentProblemListView);
        commentList.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();
    }

    //Change to EditProblem activity
    public void goEditProblem (View v){
        Intent intent = new Intent(this, ActivityEditProblem.class);
        intent.putExtra("Patient", patient);
        intent.putExtra("Position", position);
        startActivity(intent);
    }

    public void goDeleteProblem (final View v){

        //Show an alert dialog to ask for user's confirmation whether they would like to delete
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you would like to delete this problem?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                patient.getProblems().remove(problem);
                goBrowseProblems(v);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    //Change to BrowseProblems activity
    public void goBrowseProblems(View v){
        Intent intent = new Intent(this, ActivityBrowseProblems.class);
        intent.putExtra("Patient", patient);
        startActivity(intent);
    }

    //Change to AddRecord Activity
    public void goAddRecord (View v){
        Intent intent = new Intent(this, ActivityAddRecord.class);
        intent.putExtra("Flag", "b");
        startActivity(intent);
    }
}
