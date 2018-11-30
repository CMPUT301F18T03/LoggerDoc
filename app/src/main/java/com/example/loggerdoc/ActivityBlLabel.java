package com.example.loggerdoc;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityBlLabel extends AppCompatActivity {
    private EditText label;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bl_label);
        final Button SaveButton = (Button) findViewById(R.id.saveLabel);
        label = (EditText) findViewById(R.id.Label);

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit = (EditText) findViewById(R.id.Label);
                String label1 = edit.getText().toString();
                if (label1.length() != 0){
                    setResult(RESULT_OK);
                    Intent intent = new Intent();
                    intent.putExtra("THELABEL", label1);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
                else{
                    Toast.makeText(ActivityBlLabel.this, "You need to enter a label", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
