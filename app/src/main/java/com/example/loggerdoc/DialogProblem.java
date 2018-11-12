package com.example.loggerdoc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DialogProblem extends DialogFragment {
    public static final String TITLE_ID = "title";
    public static final String MESSAGE_ID = "message";

    @Override

    public Dialog onCreateDialog (Bundle savedInstanceState){

        //Get supplied title and message body.
        Bundle messages = getArguments();
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if(messages != null) {
            //Add the arguments. Supply a default in case the wrong key was used, or only one was set.
            builder.setTitle(messages.getString(TITLE_ID, "Error"));
            builder.setMessage(messages.getString(MESSAGE_ID, "There was an error."));
        }
        else {
            //Supply default text if no arguments were set.
            builder.setTitle("Error");
            builder.setMessage("There was an error.");
        }

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
