package com.example.loggerdoc;

import org.junit.Test;

import static org.junit.Assert.*;

public class CaregiverCommentListUnitTest {

    @Test
    public void testCaregiverCommentListAdd(){

        CaregiverCommentList commentList = new CaregiverCommentList();

        CaregiverComment caregiverComment = new CaregiverComment("Looks good");

        commentList.addComment(caregiverComment);

        assertTrue("Comment List should contain caregiver comment", commentList.containsComment(caregiverComment));
    }

    @Test
    public void testCaregiverCommentListDelete(){
        CaregiverCommentList commentList = new CaregiverCommentList();

        CaregiverComment caregiverComment = new CaregiverComment("Looks good");

        commentList.addComment(caregiverComment);
        commentList.deleteComment(caregiverComment);

        assertFalse("Comment List should contain caregiver comment", commentList.containsComment(caregiverComment));
    }

    @Test
    public void testCaregiverCommentListGetSize(){
        CaregiverCommentList commentList = new CaregiverCommentList();

        CaregiverComment caregiverComment = new CaregiverComment("Looks good");

        commentList.addComment(caregiverComment);

        CaregiverComment secondComment = new CaregiverComment("You should go to emergency");

        commentList.addComment(secondComment);

        assertEquals("The size of the comment list should be", 2, commentList.getSizeOfComments());
    }
}
