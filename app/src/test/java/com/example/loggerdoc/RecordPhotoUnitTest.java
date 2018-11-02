package com.example.loggerdoc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RecordPhotoUnitTest {

    @Test
    public void TestsetPhoto() {
        Bitmap image = BitmapFactory.decodeFile("someimage.png");
        RecordPhoto recordPhoto = new RecordPhoto();
        recordPhoto.setPhoto(image);
        assertEquals("Problem description should be read", image, recordPhoto.getPhoto("someimage.png"));

    }


    @Test
    public void TestDelete() {
        Bitmap image = BitmapFactory.decodeFile("someimage.png");
        RecordPhoto recordPhoto = new RecordPhoto();
        recordPhoto.setPhoto(image);
        recordPhoto.Delete();
        assertEquals(BitmapFactory.decodeFile("someimage.png"), recordPhoto.getPhoto("someimage.png"));
    }

}

