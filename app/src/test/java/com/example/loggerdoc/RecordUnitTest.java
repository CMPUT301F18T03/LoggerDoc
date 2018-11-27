package com.example.loggerdoc;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class RecordUnitTest {

    @Test
    public void testSetRecordTitle() {
        String title = "A test record";
        Record record = new Record(title,0,0);
        assertEquals("Record title should be read from record", title, record.getTitle());

        String newTitle = "An updated title";
        record.setTitle(newTitle);
        assertEquals("Updated title should be read from record", newTitle, record.getTitle());
    }

    @Test
    public void testSetRecordComment() {
        String comment = "A test comment";
        Record record = new Record("ha",0,0);
        record.setComment(comment);
        assertEquals("Record comment should be read from record", comment, record.getComment());
    }

    @Test
    public void testSetRecordTimestamp() {
        LocalDateTime beforeTime = LocalDateTime.now();
        Record record = new Record("ha",0,0);
        LocalDateTime recordTime = record.getTimestamp();
        LocalDateTime afterTime = LocalDateTime.now();

        assertTrue("Before time should be before or equal to record timestamp",
                beforeTime.isBefore(recordTime) || beforeTime.isEqual(recordTime));
        assertTrue("After time should be after or equal to record timestamp",
                afterTime.isAfter(recordTime) || afterTime.isEqual(recordTime));
    }

    @Test
    public void testAddGeolocation(){
        Record record = new Record("title",0,0);
        RecordGeoLocation geoLocation = new RecordGeoLocation(new LatLng(80.000, 90.12));
        record.setRecordGeoLocation(geoLocation);

        assertEquals(geoLocation, record.getRecordGeoLocation());
    }

}
