package com.example.loggerdoc;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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
    private ArrayList<Problem> problemArrayList;
    private ArrayList<Problem> searchResult;

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
        FloatingActionButton searchButton = (FloatingActionButton) findViewById(R.id.searchProblemButton);
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
            ProblemRecordListController.loadUser(patient_ID,getApplicationContext());
        }
        else{
            addProblemButton.setVisibility(View.VISIBLE);
        }

        //TODO: fix if we get listeners working
        problemArrayList = ProblemRecordListController.getProblemList().sort();
        if (searchResult!= null){
            adapter = new AdapterListProblems(this, searchResult);
        }else{
            adapter = new AdapterListProblems(this, problemArrayList);
        }

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

    /** @author = Alexandra Tyrrell
     *
     * The method will change the Activity to Activity View Problem
     *
     * @param view View
     */
    public void changeToViewProblemActivity(View view, Patient patient, int position){
        searchResult = null;
        Intent intent = new Intent(this, ActivityViewProblem.class);
        intent.putExtra("Patient", patient_ID);
        intent.putExtra("Position",
                problemArrayList.get(position).getElasticID());
        startActivity(intent);
    }

    /** @author = Alexandra Tyrrell
     *
     * The method will change the Activity to Add Problem Activity
     *
     * @param view View
     */
    public void changeToAddProblemActivity (View view){
        Intent intent = new Intent(this, ActivityAddProblem.class);
        startActivity(intent);
    }

    /** @author = Alexandra Tyrrell
     *
     * The method will change the Activity to Search Activity
     *
     * @param view View
     */
    public void changeToSearchActivity(View view){
        Intent intent = new Intent (this, ActivitySearchProblems.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent prevIntent) {
        super.onActivityResult(requestCode, resultCode, prevIntent);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                ArrayList<Integer> contents = prevIntent.getIntegerArrayListExtra("searchResult");
                ArrayList<Problem> searchResult = ProblemRecordListController.getProblemList().getList(contents);
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }

    /** @author = Alexandra Tyrrell
     *
     * The method will change the Activity to Activity User Profile
     *
     * @param view View
     */
    public void changeToUserProfile (View view){
        searchResult = null;
        Intent intent = new Intent (this, ActivityUserProfile.class);
        intent.putExtra("Patient", patient_ID);
        startActivity(intent);
    }

}
