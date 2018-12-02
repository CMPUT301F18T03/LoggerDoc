package com.example.loggerdoc;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/* @Author Stephen Zuk
 * This class holds all of the functionality for qr code generation
 * At the time of creating, it was assumed that this would require more effort.
 */

public class QRCodeController {
    private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";


    /*
     * This method takes a String, generates a bitmatrix object that represents
     * the qr code for that string, then converts that bitmatrix to a bitmap.
     * The method returns the bitmap
     */
    public static Bitmap generateQRCodeImage(String userID)
            throws WriterException, IOException {
        int width = 350;
        int height = 350;

        /*
         * Create a bitmatrix object representing the qr code using the string input
         */
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(userID, BarcodeFormat.QR_CODE, width, height);


        /*
         * Create the return bitmap and convert the bitmatrix to a bitmap by setting all
         * the colour of all pixels
         */
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                bmp.setPixel(x, y, bitMatrix.get(x,y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }
}
