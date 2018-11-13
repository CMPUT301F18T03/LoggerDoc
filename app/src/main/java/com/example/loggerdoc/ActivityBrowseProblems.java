package com.example.loggerdoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityBrowseProblems extends AppCompatActivity {

    private ArrayAdapter<Problem> adapter;
    private ListView problemsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_problems);

        problemsList = (ListView) findViewById(R.id.problemsList);
    }

    //@Override
    /*protected void onResume (){
        super.onResume();
        adapter = new ArrayAdapter<Problem>(this, R.layout.browse_problem_list_view, user.problemList);
        adapter.notifyDataSetChanged();
        problemsList.setAdapter(adapter);

        problemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Feelings currentfeeling = RecordNewFeelingActivity.feelingslist.getFeeling(position);
                changeActivity(View view, );
            }
        });
    }

    public void changeActivity(View v, Problem problem){

    }*/

}
