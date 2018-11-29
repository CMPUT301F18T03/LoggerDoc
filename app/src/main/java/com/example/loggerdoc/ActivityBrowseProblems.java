package com.example.loggerdoc;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/*This class displays the problems that the user has entered/saved using a custom array adapter
 *(located in the AdapterListProblems class). The problem list of the user is used in the adapter to
 *display the problems
 */

public class ActivityBrowseProblems extends AppCompatActivity {

    private AdapterListProblems adapter;
    private Patient patient;
    private Integer patient_ID;
    private ListView problemsList;
    private FloatingActionButton addProblemButton;

    //To be called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_problems);

        //get the patient from the intent
        Intent intent = getIntent();
        patient_ID = intent.getIntExtra("Patient",0);
        patient = (Patient) UserListController.getUserList().get(patient_ID);

        /*
         * Set the Add Problem button. When this button is pressed it will call
         * changeToAddProblemActivity().
         */
        addProblemButton = (FloatingActionButton) findViewById(R.id.addProblemButton);
        addProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToAddProblemActivity(v);
            }
        });

        /*
         * Set the Search Problem button. When this button is pressed it will call
         * changeToSearchActivity().
         */
        FloatingActionButton searchButton = (FloatingActionButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToSearchActivity(v);
            }
        });

        /*
         * Display the patient's userID. If this userID is clicked, it will call
         * changeToUserProfile().
         */
        TextView username = (TextView) findViewById(R.id.usernameText);
        username.setText(patient.getUserID());
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToUserProfile(v);
            }
        });
    }

    //To be called when the activity is resumed
    @Override
    protected void onResume(){
        super.onResume();
        /*
         * Check whether the currently logged in user is a patient or a caregiver. If a caregiver,
         * make the add a problem button invisible.
         */
        User user = UserListController.getUserList().get(UserListController.getCurrentUserID());

        if (user.getRole().equals("Caregiver")){
            addProblemButton.setVisibility(View.INVISIBLE);
        }
        else{
            addProblemButton.setVisibility(View.VISIBLE);
        }

        //TODO: fix if we get listeners working
        adapter = new AdapterListProblems(this, ProblemRecordListController.getProblemList().getArray());
        problemsList = (ListView) findViewById(R.id.ProblemList);
        problemsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //Set the onClickListener for the listView. This will call changeToViewProblemActivity().
        problemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeToViewProblemActivity(view, patient, position);
            }
        });
    }

    //Change to ActivityViewProblem.
    public void changeToViewProblemActivity(View view, Patient patient, int position){
        Intent intent = new Intent(this, ActivityViewProblem.class);
        intent.putExtra("Patient", patient_ID);
        intent.putExtra("Position",
                ProblemRecordListController.getProblemList().getArray().get(position).getElasticID());
        startActivity(intent);
    }

    //Change to ActivityAddProblem.
    public void changeToAddProblemActivity (View view){
        Intent intent = new Intent(this, ActivityAddProblem.class);
        startActivity(intent);
    }

    //Change to ActivitySearch.
    public void changeToSearchActivity(View view){
        Intent intent = new Intent (this, ActivitySearch.class);
        startActivity(intent);
    }

    //Change to ActivityUserProfile.
    public void changeToUserProfile (View view){
        Intent intent = new Intent (this, ActivityUserProfile.class);
        intent.putExtra("Patient", patient_ID);
        startActivity(intent);
    }

}
