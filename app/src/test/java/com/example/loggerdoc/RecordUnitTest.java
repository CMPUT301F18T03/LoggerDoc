package com.example.loggerdoc;

import org.junit.Test;

import static org.junit.Assert.*;

public class RecordUnitTest {

    @Test
    public void TestRecordTitle() {
        String title = "A test record";
        Record record = new Record(title);
        assertEquals("Record title should be read from record", title, record.getTitle());

        String newTitle = "An updated title";
        record.setTitle(newTitle);
        assertEquals("Updated title should be read from record", newTitle, record.getTitle());
    }

    @Test
    public void TestRecordComment() {
        String comment = "A test comment";
        Record record = new Record();
        record.setComment(comment);
        assertEquals("Record comment should be read from record", comment, record.getComment());
    }
}
