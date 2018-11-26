package com.example.loggerdoc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityViewProblem extends AppCompatActivity {

    private Patient patient;
    private Problem problem;
<<<<<<< HEAD
    private int position;
=======
    private int problem_ID;
    private ArrayAdapter<Record> recordAdapter;
>>>>>>> origin
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
        problem_ID = (int) intent.getSerializableExtra("Position");
        problem = ProblemRecordListController.getProblemList().get(problem_ID);

        TextView problemTitleView = (TextView) findViewById(R.id.TitleView);
        problemTitleView.setText(problem.getTitle());

        TextView problemDateView = (TextView) findViewById(R.id.dateProblemView);
        problemDateView.setText(problem.getTimestamp().toString());

        TextView problemDescriptionView = (TextView) findViewById(R.id.descriptionProblemView);
        problemDescriptionView.setText(problem.getDescription());

<<<<<<< HEAD
=======
        //Initialize and set the adapter for the records
        recordAdapter = new AdapterListRecords(this, problem.getRecordList().getArray());
        ListView recordList = (ListView) findViewById(R.id.recordsListView);
        recordList.setAdapter(recordAdapter);
        //Set the onClickListener for the listView. This will call changeToViewProblemActivity().
        recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goViewRecord(view);
            }
        });

        recordAdapter.notifyDataSetChanged();

>>>>>>> origin
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
        intent.putExtra("Position", problem_ID);
        startActivity(intent);
    }

    public void goDeleteProblem (final View v){
        //Show an alert dialog to ask for user's confirmation whether they would like to delete
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you would like to delete this problem?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.putExtra("Position", problem_ID);
                setResult(RESULT_OK, intent);
                finish();
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

<<<<<<< HEAD
    public void goViewRecordList(View v, Problem problem){
=======

    //Change to AddRecord Activity
    public void goAddRecord (View v){
        Intent intent = new Intent(this, ActivityAddRecord.class);
        intent.putExtra("Patient", patient);
        intent.putExtra("Position", problem_ID);
        intent.putExtra("Flag", "a");
        startActivityForResult(intent, ADD_RECORD_RESULT);
    }

    public void goViewRecord(View v){
>>>>>>> origin
        Intent intent = new Intent(this, ActivityViewRecord.class);
        intent.putExtra("Problem", problem);
        startActivity(intent);
    }
}
