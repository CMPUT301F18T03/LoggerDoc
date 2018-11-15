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
import android.widget.TextView;

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

        //Initialize and set the custom adapter
        adapter = new AdapterListProblems(this, patient.getProblems().getProblemArrayList());
        problemsList.setAdapter(adapter);

        adapter.notifyDataSetChanged();


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

        FloatingActionButton searchButton = (FloatingActionButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToSearchActivity(v,patient);
            }
        });

        TextView username = (TextView) findViewById(R.id.usernameText);
        username.setText(patient.getUserID());
        setOnClick(username, patient);
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

    public void changeToSearchActivity(View v, Patient patient){
        Intent intent = new Intent (this, ActivitySearch.class);
        intent.putExtra("Patient", patient);
        startActivity(intent);
    }

    public void setOnClick(final TextView username, final Patient patient){
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToUserProfile(v, patient);
            }
        });
    }

    public void changeToUserProfile(View v, Patient patient){
        Intent intent = new Intent (this, ActivityUserProfile.class);
        intent.putExtra("Patient", patient);
        startActivity(intent);
    }

}
