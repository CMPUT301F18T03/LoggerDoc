package com.example.loggerdoc;

import android.net.Uri;

import org.junit.Test;

import java.io.File;

import static java.lang.Boolean.TRUE;
import static org.junit.Assert.assertEquals;

public class RecordBodyLocationUnitTest {
    @Test
    public void TestsetPhoto() {
        File path  = new File("something.png");
        BodyLocationPhoto photo = new BodyLocationPhoto();
        photo.setPhoto(path);
        assertEquals("should be the same",path, photo.getPhoto() );

    }

}
