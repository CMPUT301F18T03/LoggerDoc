package com.example.loggerdoc;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ProblemListUnitTest {

    @Test
    public void TestProblemListAdd() {
        ProblemList problemList = new ProblemList();
        Problem problem = new Problem("",LocalDateTime.now(),"",0);
        problemList.add_internal(problem);
        assertTrue("ProblemList should contain added problem", problemList.contains(problem));
    }

    @Test
    public void TestProblemListRemove() {
        ProblemList problemList = new ProblemList();
        Problem problem = new Problem("",LocalDateTime.now(),"",0);
        problemList.add_internal(problem);
        assertTrue("ProblemList should contain added problem", problemList.contains(problem));

        problemList.remove(problem);
        assertFalse("ProblemList should not contain added problem", problemList.contains(problem));
    }

    @Test
    public void TestProblemListSize() {
        ProblemList problemList = new ProblemList();
        assertEquals("ProblemList should have size 0", 0, problemList.size());

        Problem problemA = new Problem("",LocalDateTime.now(),"",0);
        problemList.add_internal(problemA);
        assertEquals("RecordList should have size 1", 1, problemList.size());

        Problem problemB = new Problem("",LocalDateTime.now(),"",0);
        problemList.add_internal(problemB);
        assertEquals("RecordList should have size 2", 2, problemList.size());

        problemList.remove(problemA);
        assertEquals("RecordList should have size 1 after deletion", 1, problemList.size());
    }
}
