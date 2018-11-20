package com.example.loggerdoc;

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RecordPhotoListUnitTest {
@Test
public void TestsetPhoto() {

    RecordPhotoList list = new RecordPhotoList();
        RecordPhoto recordPhoto = new RecordPhoto();
        list.addPhoto(recordPhoto);
        assertTrue("Photo list should contain added photo", list.containsPhoto(recordPhoto));
}
    @Test
    public void TestProblemListRemove() {
        RecordPhotoList list = new RecordPhotoList();
        RecordPhoto photo = new RecordPhoto();
        list.addPhoto(photo);
        Assert.assertTrue("ProblemList should contain added problem", list.containsPhoto(photo));

        list.removePhoto(photo);
        assertFalse("ProblemList should not contain added problem", list.containsPhoto(photo));
    }

    @Test
    public void TestProblemListSize() {
        RecordPhotoList list = new RecordPhotoList();
        assertEquals("ProblemList should have size 0", 0, list.size());

        RecordPhoto photo1 = new RecordPhoto();
        list.addPhoto(photo1);
        assertEquals("RecordList should have size 1", 1, list.size());

        RecordPhoto photo2 = new RecordPhoto();
        list.addPhoto(photo2);
        assertEquals("RecordList should have size 2", 2, list.size());

        list.removePhoto(photo1);
        assertEquals("RecordList should have size 1 after deletion", 1, list.size());
    }
}
