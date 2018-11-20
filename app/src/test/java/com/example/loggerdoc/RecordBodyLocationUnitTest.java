package com.example.loggerdoc;

import android.net.Uri;

import org.junit.Test;

import static java.lang.Boolean.TRUE;
import static org.junit.Assert.assertEquals;

public class RecordBodyLocationUnitTest {
    @Test
    public void TestsetPhoto() {
        Uri image = null;
        BodyLocationPhoto photo = new BodyLocationPhoto();
        photo.setPhoto(image);
        assertEquals("should be the same",image, photo.getPhoto() );

    }

}
