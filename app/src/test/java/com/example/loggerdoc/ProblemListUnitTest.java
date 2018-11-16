package com.example.loggerdoc;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProblemListUnitTest {

    @Test
    public void TestProblemListAdd() {
        ProblemList problemList = new ProblemList();
        Problem problem = new Problem("",new DatePickerFragment(),"");
        problemList.add(problem);
        assertTrue("ProblemList should contain added problem", problemList.contains(problem));
    }

    @Test
    public void TestProblemListRemove() {
        ProblemList problemList = new ProblemList();
        Problem problem = new Problem("",new DatePickerFragment(),"");
        problemList.add(problem);
        assertTrue("ProblemList should contain added problem", problemList.contains(problem));

        problemList.remove(problem);
        assertFalse("ProblemList should not contain added problem", problemList.contains(problem));
    }

    @Test
    public void TestProblemListSize() {
        ProblemList problemList = new ProblemList();
        assertEquals("ProblemList should have size 0", 0, problemList.size());

        Problem problemA = new Problem("",new DatePickerFragment(),"");
        problemList.add(problemA);
        assertEquals("RecordList should have size 1", 1, problemList.size());

        Problem problemB = new Problem("",new DatePickerFragment(),"");
        problemList.add(problemB);
        assertEquals("RecordList should have size 2", 2, problemList.size());

        problemList.remove(problemA);
        assertEquals("RecordList should have size 1 after deletion", 1, problemList.size());
    }
}
