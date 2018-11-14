package com.example.loggerdoc;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ActivityBrowseProblems extends AppCompatActivity {

    private ArrayAdapter<Problem> adapter;
    private ListView problemsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_problems);

        problemsList = (ListView) findViewById(R.id.ProblemList);
    }

    //@Override
    protected void onResume (){
        super.onResume();
        Intent intent = getIntent();
        final Patient patient = (Patient) intent.getSerializableExtra("Patient");

        adapter = new ArrayAdapter<Problem>(this, R.layout.browse_problem_list_view, patient.getProblems().getProblemArrayList());
        adapter.notifyDataSetChanged();
        problemsList.setAdapter(adapter);

        problemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeToViewProblemActivity(view, patient,position);
            }
        });

        FloatingActionButton addProblemButton = (FloatingActionButton) findViewById(R.id.addProblemButton);
        addProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToAddProblemActivity(v, patient);
            }
        });
    }

    public void changeToViewProblemActivity(View v, Patient patient, int position){
        Intent intent = new Intent(this, ActivityViewProblem.class);
        intent.putExtra("Patient", patient);
        intent.putExtra("Position", position);
        startActivity(intent);
    }

    public void changeToAddProblemActivity (View v, Patient patient){
        Intent intent = new Intent(this, ActivityAddProblem.class);
        intent.putExtra("Patient", patient);
        startActivity(intent);
    }

}
