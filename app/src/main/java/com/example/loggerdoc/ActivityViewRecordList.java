package com.example.loggerdoc;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityViewRecordList extends AppCompatActivity {

    private int problemID;
    private FloatingActionButton addRecordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record_list);

        Intent intent = getIntent();
        problemID = intent.getIntExtra("Problem",0);
        Problem problem = ProblemRecordListController.getProblemList().get(problemID);

        //Initialize the problem title
        TextView problemTitle = (TextView) findViewById(R.id.viewRecordListProblemTitle);
        problemTitle.setText(problem.getTitle());

        addRecordButton = (FloatingActionButton) findViewById(R.id.addRecordButton);
        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddRecord(v);
            }
        });

        FloatingActionButton searchRecordsButton = (FloatingActionButton)findViewById(R.id.recordSearchButton);
        searchRecordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSearchRecord(v);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        User user = UserListController.getUserList().get(UserListController.getCurrentUserID());

        if (user.getRole().equals("Caregiver")){
            addRecordButton.setVisibility(View.INVISIBLE);
        }
        else{
            addRecordButton.setVisibility(View.VISIBLE);
        }

        //Initialize and set the adapter for the records
        Log.d ("The size of the record list is", String.valueOf(ProblemRecordListController.getRecordList().getRecords(problemID).size()));
        ArrayAdapter<Record> recordAdapter = new AdapterListRecords(this, ProblemRecordListController.getRecordList().getRecords(problemID));
        ListView recordList = (ListView) findViewById(R.id.recordsListView);
        recordList.setAdapter(recordAdapter);
        //Set the onClickListener for the listView. This will call changeToViewProblemActivity().
        recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("The position is", String.valueOf(position));
                Log.d("The title of the second record is", String.valueOf(ProblemRecordListController.getRecordList().getRecords(problemID).get(position).getTitle()));
                goViewRecord(view, position);
            }
        });

        recordAdapter.notifyDataSetChanged();
    }

    public void goViewRecord(View v, int position){
        Intent intent = new Intent(this, ActivityViewRecord.class);
        intent.putExtra("Problem", problemID);
        intent.putExtra("Record", ProblemRecordListController.getRecordList().getRecords(problemID).get(position).getElasticID());
        startActivity(intent);
    }

    //Change to AddRecord Activity
    public void goAddRecord (View v){
        Intent intent = new Intent(this, ActivityAddRecord.class);
        intent.putExtra("Problem", problemID);
        startActivity(intent);
    }

    //Change to ActivitySearch.
    public void goSearchRecord(View view){
        Intent intent = new Intent (this, ActivitySearch.class);
        startActivity(intent);
    }
}
