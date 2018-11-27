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

    static final int ADD_RECORD_RESULT = 1;

    private Integer position;
    private Problem problem;
    private ArrayAdapter<CaregiverComment> commentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problem);

        //Set the problem
        Intent intent = getIntent();
        position = intent.getIntExtra("Position",0);
        problem = ProblemRecordListController.getProblemList().getArray().get(position);

        TextView problemTitleView = (TextView) findViewById(R.id.TitleView);
        problemTitleView.setText(problem.getTitle());

        TextView problemDateView = (TextView) findViewById(R.id.dateProblemView);
        problemDateView.setText(problem.getTimestamp().toString());

        TextView problemDescriptionView = (TextView) findViewById(R.id.descriptionProblemView);
        problemDescriptionView.setText(problem.getDescription());

        //Initialize and set the adapter for the caregiver's comments
        commentAdapter = new AdapterListComments(this, problem.getCommentList().getComments());
        ListView commentList = (ListView) findViewById(R.id.commentProblemListView);
        commentList.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_RECORD_RESULT) {
            if (resultCode == RESULT_OK) {
                //Record r = (Record) data.getSerializableExtra("Record");
                //problem.addRecord(r);
            }
        }
    }

    //Change to EditProblem activity
    public void goEditProblem (View v){
        Intent intent = new Intent(this, ActivityEditProblem.class);
        intent.putExtra("Problem", problem.getElasticID());
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
                intent.putExtra("Problem", problem.getElasticID());
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


    //Change to AddRecord Activity
    public void goAddRecord (View v){
        Intent intent = new Intent(this, ActivityAddRecord.class);
        intent.putExtra("Position", problem.getElasticID());
        intent.putExtra("Flag", "a");
        startActivityForResult(intent, ADD_RECORD_RESULT);
    }

    public void goViewRecord(View v){
        Intent intent = new Intent(this, ActivityViewRecord.class);
        startActivity(intent);
    }
}
