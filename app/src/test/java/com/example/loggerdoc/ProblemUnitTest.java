package com.example.loggerdoc;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProblemUnitTest {

    @Test
    public void TestProblemTitle() {
        String title = "A test problem";
        Problem problem = new Problem(title);
        assertEquals("Problem title should be read from problem", title, problem.getTitle());

        String newTitle = "An updated title";
        problem.setTitle(newTitle);
        assertEquals("Updated title should be read from problem", newTitle, problem.getTitle());
    }

    @Test
    public void TestProblemDescription() {
        String desc = "A test description";
        Problem problem = new Problem();
        problem.setDescription(desc);
        assertEquals("Problem description should be read", desc, problem.getDescription());
    }

    @Test
    public void TestProblemRecordListAdd() {
        Problem problem = new Problem();
        Record record = new Record();
        problem.addRecord(record);
        assertTrue("Problem's record list should contain record",
                problem.getRecordList().contains(record));
    }
}
