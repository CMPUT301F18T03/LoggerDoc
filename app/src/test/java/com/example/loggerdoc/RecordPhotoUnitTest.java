package com.example.loggerdoc;


import android.net.Uri;

import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class RecordPhotoUnitTest {
    @Test
    public void TestsetPhoto() {
        File file = new File("something.png");
        RecordPhoto recordPhoto = new RecordPhoto();
        recordPhoto.setPhoto(file);
        assertEquals("should be the same",file, recordPhoto.getPhoto() );

    }

}


