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

public class ActivityViewRecordList extends AppCompatActivity {

    private int problemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record_list);
    }

    @Override
    public void onResume(){
        super.onResume();

        Intent intent = getIntent();
        problemID = intent.getIntExtra("Problem",0);
        Problem problem = ProblemRecordListController.getProblemList().get(problemID);

        //TODO: somehow get the recordlist of the problem

        //Initialize the problem title
        TextView problemTitle = (TextView) findViewById(R.id.viewRecordListProblemTitle);
        problemTitle.setText(problem.getTitle());

        FloatingActionButton addRecordButton = (FloatingActionButton) findViewById(R.id.addRecordButton);
        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddRecord(v);
            }
        });

        //Initialize and set the adapter for the records
        /*ArrayAdapter<Record> recordAdapter = new AdapterListRecords(this, problem.getRecordList().getRecordArrayList());
        ListView recordList = (ListView) findViewById(R.id.recordsListView);
        recordList.setAdapter(recordAdapter);
        //Set the onClickListener for the listView. This will call changeToViewProblemActivity().
        recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goViewRecord(view, position);
            }
        });

        recordAdapter.notifyDataSetChanged();*/
    }

    public void goViewRecord(View v, int position){
        Intent intent = new Intent(this, ActivityViewRecord.class);
        intent.putExtra("Problem", problemID);
        intent.putExtra("Record", position);
        startActivity(intent);
    }

    //Change to AddRecord Activity
    public void goAddRecord (View v){
        Intent intent = new Intent(this, ActivityAddRecord.class);
        intent.putExtra("Problem", problemID);
        startActivity(intent);
    }
}
