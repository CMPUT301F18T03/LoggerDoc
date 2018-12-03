package com.example.loggerdoc;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/*This class displays the records associated with the selected problem using a custom array adapter
 *(located in the AdapterListRecords class). The record list of the selected problem is used in the
 * adapter to display the records.
 */

public class ActivityViewRecordList extends AppCompatActivity {

    private int problemID;
    private FloatingActionButton addRecordButton;
    private ArrayList<Record> recordArrayList;
    private ArrayList<Record> searchResult;
    ArrayAdapter<Record> recordAdapter;

    //To be called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record_list);

        //Get the correct problem from the intent
        Intent intent = getIntent();
        problemID = intent.getIntExtra("Problem",0);
        Problem problem = ProblemRecordListController.getProblemList().get(problemID);

        //Initialize the problem title
        TextView problemTitle = (TextView) findViewById(R.id.viewRecordListProblemTitle);
        problemTitle.setText(problem.getTitle());

        //Initialize the add record button
        addRecordButton = (FloatingActionButton) findViewById(R.id.addRecordButton);
        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddRecord(v);
            }
        });

        //Intialize the search record button
        FloatingActionButton searchRecordsButton = (FloatingActionButton)findViewById(R.id.searchRecordButton);
        searchRecordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSearchRecord(v);
            }
        });
    }

    //To be called when the activity is resumed
    @Override
    public void onResume(){
        super.onResume();

        /*
         * Check whether the currently logged in user is a patient or a caregiver. If a caregiver,
         * make the add a record button invisible.
         */
        User user = UserListController.getUserList().get(UserListController.getCurrentUserID());

        if (user.getRole().equals("Caregiver")){
            addRecordButton.setVisibility(View.INVISIBLE);
        }
        else{
            addRecordButton.setVisibility(View.VISIBLE);
        }

        //Initialize and set the adapter for the records
        if (searchResult != null){
            recordAdapter = new AdapterListRecords(this, searchResult);
        }else {
            recordAdapter = new AdapterListRecords(this, ProblemRecordListController.getRecordList().getRecords(problemID));
        }

        ListView recordList = (ListView) findViewById(R.id.recordsListView);
        recordList.setAdapter(recordAdapter);
        //Set the onClickListener for the listView. This will call goViewRecord().
        recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goViewRecord(view, position);
            }
        });
        recordAdapter.notifyDataSetChanged();
    }

    //Change to ViewRecord Activity
    public void goViewRecord(View v, int position){
        searchResult = null;
        Intent intent = new Intent(this, ActivityViewRecord.class);
        intent.putExtra("Problem", problemID);
        Record next = ProblemRecordListController.getRecordList().getRecords(problemID).get(position);
        intent.putExtra("Record", next.getElasticID());
        ProblemRecordListController.getRecordPhotoList().loadRecord(next,getApplicationContext());
        startActivity(intent);
    }

    //Change to AddRecord Activity
    public void goAddRecord (View v){
        searchResult = null;
        Intent intent = new Intent(this, ActivityAddRecord.class);
        intent.putExtra("Problem", problemID);
        startActivity(intent);
    }

    //Change to ActivitySearch.

    public void goSearchRecord(View view){
        searchResult = null;
        Intent intent = new Intent (this, ActivitySearchRecords.class);
        startActivityForResult(intent, 0);;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent prevIntent) {
        super.onActivityResult(requestCode, resultCode, prevIntent);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                ArrayList<Integer> contents = prevIntent.getIntegerArrayListExtra("searchResult");
                ArrayList<Record> searchResult = ProblemRecordListController.getRecordList().getList(contents);
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }
}
