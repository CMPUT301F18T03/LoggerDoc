package com.example.loggerdoc;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BodyLocationPhotoUnitTest {
    @Test
    public void TestsetPhoto() {
        String image = "someimage.png";
        BodyLocationPhoto BLphoto = new BodyLocationPhoto();
        BLphoto.setPhoto(image);
        BodyLocationPhotoList list = new BodyLocationPhotoList();
        list.add(BLphoto);
        assertTrue("size should be 1", list.contains(BLphoto));

    }
    @Test
    public void TestgetPhoto() {
        String image = "someimage.png";
        BodyLocationPhoto BLphoto = new BodyLocationPhoto();
        BLphoto.setPhoto(image);
        String photo = BLphoto.getPhoto();
        assertEquals("Should be same file name", image,photo);

    }
}