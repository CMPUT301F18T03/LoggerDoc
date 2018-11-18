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
    public void TestProblemRecordListAdd() {
        Problem problem = new Problem("",new DatePickerFragment(),"");
        Record record = new Record();
        problem.addRecord(record);
        assertTrue("Problem's record list should contain record",
                problem.getRecordList().contains(record));
    }

    @Test
    public void testCaregiverCommentAdd(){
        Problem problem = new Problem("",new DatePickerFragment(),"");
        CaregiverComment caregiverComment = new CaregiverComment(new CareGiver("test",
                "test", "test","test", new PatientList()),
                "Looks good");

        problem.addComment(caregiverComment);

        assertTrue("Problem's caregiver comment list should contain comment",
                problem.getCommentList().containsComment(caregiverComment));
    }

    public void testTitleTooLong(){
        Problem problem = new Problem("yuUAuGVmRMKiGcFxuSY8RfS1YedXJB5\n",new DatePickerFragment(),"");

        assertFalse("The problem title is too long",problem.checkTitleLength(problem.getTitle()));
    }

    public void testCommentTooLong(){
        Problem problem = new Problem("",new DatePickerFragment(),
                "mDiy1zVeCt8hAjXX3f9kpdvx8sD08qEi9emAWP4VBNOYJmZTkf2EI2A8GYXLODIgAmt1rLFLr7y546tDqUyJHJRvy8W1iQHVvj5359rld5BT9qXPdEet5qxA0Sv7pMHUJ8Ff8Pl00uwHT5ANgfiqV9LeG1yA1BJfhdaclMI4cwjSvw4FoThOjeWuePILfELyO57h9bZyNJs730NYraUi1AjJBNZhPx83UgqKt9Vvw7nWujrejFNxi8zDR4txwpV6wU779Z6sjVE8gQUwc4BMcNVbQ7b4zKt97GIpjJOIODo1Q");

        assertFalse("The problem description is too long", problem.checkDescriptionLength(problem.getDescription()));
    }

}
