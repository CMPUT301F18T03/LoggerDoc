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

    static final int VIEW_RECORD_RESULT = 1;
    static final int ADD_RECORD_RESULT = 2;
    private Problem problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record_list);
    }

    @Override
    public void onResume(){
        super.onResume();

        Intent intent = getIntent();
        problem = (Problem) intent.getSerializableExtra("Problem");

        //Initialize the problem title
        TextView problemTitle = (TextView) findViewById(R.id.viewRecordListProblemTitle);
        problemTitle.setText(problem.getTitle());

        FloatingActionButton addRecordButton = (FloatingActionButton) findViewById(R.id.addProblemButton);
        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddRecord(v);
            }
        });

        //Initialize and set the adapter for the records
        ArrayAdapter<Record> recordAdapter = new AdapterListRecords(this, problem.getRecordList().getRecordArrayList());
        ListView recordList = (ListView) findViewById(R.id.recordsListView);
        recordList.setAdapter(recordAdapter);
        //Set the onClickListener for the listView. This will call changeToViewProblemActivity().
        recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goViewRecord(view, position);
            }
        });

        recordAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_RECORD_RESULT) {
            if (resultCode == RESULT_OK) {
                Record r = (Record) data.getSerializableExtra("Record");
                problem.addRecord(r);
            }
        }
    }

    public void goViewRecord(View v, int position){
        Intent intent = new Intent(this, ActivityViewRecord.class);
        intent.putExtra("Problem", problem);
        intent.putExtra("Position", position);
        startActivityForResult(intent, VIEW_RECORD_RESULT);
    }

    //Change to AddRecord Activity
    public void goAddRecord (View v){
        Intent intent = new Intent(this, ActivityAddRecord.class);
        intent.putExtra("Flag", "ViewRecordList");
        startActivityForResult(intent, ADD_RECORD_RESULT);
    }
}
