package com.example.loggerdoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityViewProblem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problem);
    }

    @Override
    protected void onResume(){
        super.onResume();

        Intent intent = getIntent();
        problem = (Problem) intent.getSerializableExtra("problemToEdit");

        TextView problemTitleView = (TextView) findViewById(R.id.ProblemTitleView);
        problemTitleView.setText(problem.getTitle());

        TextView problemDateView = (TextView) findViewById(R.id.dateView);
        problemDateView.setText(problem.getDate());

        TextView problemDescriptionView = (TextView) findViewById(R.id.ProblemDescriptionText);
        problemDescriptionView.setText(problem.getDescription());

        Button editProblemButton = (Button) findViewById(R.id.editButton);
        Button deleteProblemButton = (Button) findViewById(R.id.deleteButton);
        Button addRecordButton = (Button) findViewById(R.id.addRecordButton);
    }

    public void goEditProblem (View v){
        Intent intent = new Intent(this, ActivityEditProblem.class);
        startActivity(intent);
    }

    public void goDeleteProblem (View v){
        //TODO: create an alert dialog
    }

    public void goAddRecord (View v){
        Intent intent = new Intent(this, ActivityEditProblem.class);
        startActivity(intent);
    }
}
