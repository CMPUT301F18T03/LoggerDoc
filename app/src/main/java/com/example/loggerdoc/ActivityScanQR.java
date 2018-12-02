package com.example.loggerdoc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.zxing.Result;
import java.util.logging.Logger;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/* @Author Stephen Zuk
 * This activity handles the QR code scanning functionality. The activity opens the camera
 * scanner view and waits for a qr code to be recognized. When it is recognized, the qr code
 * is read and returned to the previous activity
 */

public class ActivityScanQR extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        /*
         *Check to see if permissions are enabled for camera usage. If not, enable camera usage
         */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 1);
        }

        /*
         *Initialize the scanner view and set the activity view to the scanner
         */
        scannerView = new ZXingScannerView(this);
        // Set the scanner view as the content view
        setContentView(scannerView);
    }

    @Override
    public void onResume() {
        super.onResume();

        /*
         * Set this activity to handle results received by the scanner. Results will
         * automatically be handled by the handleResult method, which is overridden below
         */
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }


    /*
     * This method handles results received by the scanner. It is called when a QR code
     * is recognized and scanned. The parameter rawResult is the result of the scan
     * and is converted to a string with getText() and returned to the previous method
     */
    @Override
    public void handleResult(Result rawResult) {

        Log.d("scan result", rawResult.getBarcodeFormat().toString());

        Intent intent = new Intent();
        intent.putExtra("SCAN_RESULT", rawResult.getText());
        setResult(RESULT_OK, intent);
        finish();
    }
}
