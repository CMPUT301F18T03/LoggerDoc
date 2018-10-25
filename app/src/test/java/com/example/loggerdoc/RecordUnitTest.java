package com.example.loggerdoc;

import org.junit.Test;

import java.time.LocalDateTime;

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

    @Test
    public void TestRecordTimestamp() {
        LocalDateTime beforeTime = LocalDateTime.now();
        Record record = new Record();
        LocalDateTime recordTime = record.getTimestamp();
        LocalDateTime afterTime = LocalDateTime.now();

        assertTrue("Before time should be before or equal to record timestamp",
                beforeTime.isBefore(recordTime) || beforeTime.isEqual(recordTime));
        assertTrue("After time should be after or equal to record timestamp",
                afterTime.isAfter(recordTime) || afterTime.isEqual(recordTime));
    }
}
