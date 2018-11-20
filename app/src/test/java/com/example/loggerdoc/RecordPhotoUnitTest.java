package com.example.loggerdoc;


import android.net.Uri;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class RecordPhotoUnitTest {
    @Test
    public void TestsetPhoto() {
        Uri image = null;
        RecordPhoto recordPhoto = new RecordPhoto();
        recordPhoto.setPhoto(image);
        assertEquals("should be the same",image, recordPhoto.getPhoto() );

    }

}


