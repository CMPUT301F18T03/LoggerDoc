package com.example.loggerdoc;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ProblemUnitTest {

    @Test
    public void TestProblemTitle() {
        String title = "A test problem";
        Problem problem = new Problem(title,new DatePickerFragment(),"desc");
        assertEquals("Problem title should be read from problem", title, problem.getTitle());

        String newTitle = "An updated title";
        problem.setTitle(newTitle);
        assertEquals("Updated title should be read from problem", newTitle, problem.getTitle());
    }

    @Test
    public void TestProblemDescription() {
        String desc = "A test description";
        Problem problem = new Problem("title",new DatePickerFragment(),desc);
        problem.setDescription(desc);
        assertEquals("Problem description should be read", desc, problem.getDescription());
    }

    @Test
    public void TestProblemTimestamp() {
        LocalDateTime beforeTime = LocalDateTime.now();
        Problem problem = new Problem("",new DatePickerFragment(),"");
        LocalDateTime problemTime = problem.getTimestamp();
        LocalDateTime afterTime = LocalDateTime.now();

        assertTrue("Before time should be before or equal to problem timestamp",
                beforeTime.isBefore(problemTime) || beforeTime.isEqual(problemTime));
        assertTrue("After time should be after or equal to problem timestamp",
                afterTime.isAfter(problemTime) || afterTime.isEqual(problemTime));
    }

    @Test
    public void testGetRecordsList(){
        Problem problem = new Problem("",new DatePickerFragment(),"");
        Record record = new Record();
        RecordList recordList = new RecordList();

        problem.addRecord(record);
        recordList.add(record);

        assertEquals(recordList, problem.getRecordList());
    }

    @Test
    public void TestProblemRecordListAdd() {
        Problem problem = new Problem("",new DatePickerFragment(),"");
        Record record = new Record();
        problem.addRecord(record);
        assertTrue("Problem's record list should contain record",
                problem.getRecordList().contains(record));
    }

    @Test
    public void getCaregiverCommetnList(){
        Problem problem = new Problem("",new DatePickerFragment(),"");
        CaregiverComment caregiverComment = new CaregiverComment(new CareGiver("test",
                "test", "test","test", new PatientList()),
                "Looks good");

        CaregiverCommentList comments = new CaregiverCommentList();

        comments.addComment(caregiverComment);
        problem.addComment(caregiverComment);

        assertEquals(comments, problem.getCommentList());
    }

    @Test
    public void testCaregiverCommentAdd(){
        Problem problem = new Problem("",new DatePickerFragment(),"");
        CaregiverComment caregiverComment = new CaregiverComment(new CareGiver("test",
                "test", "test","test", new PatientList()),
                "Looks good");

        problem.addComment(caregiverComment);

        assertTrue("Problem's caregiver comment list should contain comment",
                problem.getCommentList().contains(caregiverComment));
    }




}
