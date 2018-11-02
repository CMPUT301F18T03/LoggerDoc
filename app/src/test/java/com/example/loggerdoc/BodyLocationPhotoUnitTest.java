package com.example.loggerdoc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BodyLocationPhotoUnitTest {

    @Test
    public void TestsetPhoto() {
        Bitmap image = BitmapFactory.decodeFile("someimage.png");
        BodyLocationPhoto BLphoto = new BodyLocationPhoto();
        BLphoto.setPhoto(image);
        assertEquals("Problem description should be read", image, BLphoto.getPhoto("someimage.png"));

    }


    @Test
    public void TestDelete() {
        Bitmap image = BitmapFactory.decodeFile("someimage.png");
        BodyLocationPhoto BLphoto = new BodyLocationPhoto();
        BLphoto.setPhoto(image);
        BLphoto.Delete();
        assertEquals(BitmapFactory.decodeFile("someimage.png"), BLphoto.getPhoto("someimage.png"));
    }

}
