package com.example.loggerdoc;

import org.junit.Test;

import static org.junit.Assert.*;

public class RecordListUnitTest {

    @Test
    public void TestRecordListAdd() {
        RecordList recordList = new RecordList();
        Record record = new Record("ha",0,0);
        recordList.add_internal(record);
        assertTrue("RecordList should contain added record", recordList.contains(record));
    }

    @Test
    public void TestRecordListRemove() {
        RecordList recordList = new RecordList();
        Record record = new Record("ha",0,0);
        recordList.add_internal(record);
        assertTrue("RecordList should contain added record", recordList.contains(record));

        recordList.remove(record);
        assertFalse("RecordList should not contain added record", recordList.contains(record));
    }

    @Test
    public void TestRecordListSize() {
        RecordList recordList = new RecordList();
        assertEquals("RecordList should have size 0", 0, recordList.size());

        Record recordA = new Record("ha",0,0);
        recordList.add_internal(recordA);
        assertEquals("RecordList should have size 1", 1, recordList.size());

        Record recordB = new Record("he",1,0);
        recordList.add_internal(recordB);
        assertEquals("RecordList should have size 2", 2, recordList.size());

        recordList.remove(recordA);
        assertEquals("RecordList should have size 1 after deletion", 1, recordList.size());
    }
}
