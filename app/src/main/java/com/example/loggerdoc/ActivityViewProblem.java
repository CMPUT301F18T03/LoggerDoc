package com.example.loggerdoc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityViewProblem extends AppCompatActivity {

    private Problem problem;
    private int problemID;
    private AdapterListComments commentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problem);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Button addCommentButton = findViewById(R.id.addComment);
        Button editProblemButton = findViewById(R.id.editButton);
        Button deleteProblemButton = findViewById(R.id.deleteButton);

        /*
         * Check whether the currently logged in user is a patient or a caregiver. If a caregiver,
         * make the edit and delete problem buttons invisible and add comment button visible.
         */
        User user = UserListController.getUserList().get(UserListController.getCurrentUserID());
        if (user.getRole().equals("Caregiver")){
            addCommentButton.setVisibility(View.VISIBLE);
            editProblemButton.setVisibility(View.INVISIBLE);
            deleteProblemButton.setVisibility(View.INVISIBLE);
        }
        else{
            addCommentButton.setVisibility(View.INVISIBLE);
            editProblemButton.setVisibility(View.VISIBLE);
            deleteProblemButton.setVisibility(View.VISIBLE);
        }

        //Get the correct problem from the intent, and initialize the text fields accordingly.
        Intent intent = getIntent();
        problemID= intent.getIntExtra("Position",0);
        problem = ProblemRecordListController.getProblemList().get(problemID);

        TextView problemTitleView = (TextView) findViewById(R.id.TitleView);
        problemTitleView.setText(problem.getTitle());

        TextView problemDateView = (TextView) findViewById(R.id.dateProblemView);
        problemDateView.setText(problem.getTimestamp().toString());

        TextView problemDescriptionView = (TextView) findViewById(R.id.descriptionProblemView);
        problemDescriptionView.setText(problem.getDescription());

        //Initialize and set the adapter for the caregiver's comments
        commentAdapter = new AdapterListComments(this, problem.getCommentList().getComments());
        ListView commentList = (ListView) findViewById(R.id.commentProblemListView);
        commentList.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();
    }

    /*
     * @author = Alexandra Tyrrell
     *
     * Show an alert dialog to ask for user's confirmation whether they would like to delete the
     * selected problem
     */
    public void goDeleteProblem (final View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you would like to delete this problem?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProblemRecordListController.getProblemList().remove(problem,getApplicationContext());
                finish();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /*
     * @author = Alexandra Tyrrell
     *
     * Show an alert dialog to ask for the caregiver to add a comment to the specified problem.
     */
    public void addCaregiverComment (final View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityViewProblem.this);
        builder.setTitle("ADD CAREGIVER COMMENT: ");
        final EditText input = new EditText(ActivityViewProblem.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(layoutParams);
        builder.setView(input);

        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CareGiver caregiver = (CareGiver) UserListController.getUserList().get(UserListController.getCurrentUserID());
                problem.addComment(new CaregiverComment(caregiver, input.getText().toString()));
                ProblemRecordListController.getProblemList().update(problem, getApplicationContext());
                commentAdapter.refresh(ProblemRecordListController.getProblemList().get(problemID)
                        .getCommentList().getComments());
                commentAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*
     * @author = Alexandra Tyrrell
     *
     * Change to the Edit Problem Activity.
     */
    public void goEditProblem (View v){
        Intent intent = new Intent(this, ActivityEditProblem.class);
        intent.putExtra("Problem", problem.getElasticID());
        startActivity(intent);
    }

    /*
     * @author = Alexandra Tyrrell
     *
     * Change to the View Record List activity.
     */
    public void goViewRecordList(View v){
        Intent intent = new Intent(this, ActivityViewRecordList.class);
        intent.putExtra("Problem", problem.getElasticID());
        startActivity(intent);
    }

    /*
     * @author = Alexandra Tyrrell
     *
     * Change to the View Record Map activity.
     */
    public void goRecordMap(View v){
        Intent intent = new Intent(this, ActivityViewRecordMap.class);
        intent.putExtra("Problem", problem.getElasticID());
        startActivity(intent);
    }

    /*
     * @author = Alexandra Tyrrell
     *
     * Change to the slideshow activity.
     */
    public void goSlideshow(View v){
        Intent intent = new Intent(this, ActivitySlideShow.class);
        intent.putExtra("Problem", problem.getElasticID());
        startActivity(intent);
    }
}
