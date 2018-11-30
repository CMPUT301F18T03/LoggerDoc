package com.example.loggerdoc;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.GrantPermissionRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ActivityEditRecordIntentTest {

    // ensure proper permissions have been granted
    @Rule
    public GrantPermissionRule photoPerm = GrantPermissionRule.grant(android.Manifest.permission.READ_EXTERNAL_STORAGE);

    private Patient p;
    private Problem pr;
    private Record r;

    private String recordTitle = "Car crash";
    private String recordComment = "Blue Honda Civic";
    private String newTitle = "Got rear-ended";
    private String newComment = "Red Honda Civic";


    @Rule
    public IntentsTestRule<ActivityEditRecord> intentsTestRule =
            new IntentsTestRule<>(ActivityEditRecord.class, false, false);

    @Before
    public void setup() {
        p = new Patient("Patty2222", "testPatient@example.com", "555-123-4567", "Patient");
        UserListController.getUserList().add_internal(p);
        UserListController.setCurrentUser(p);

        pr = new Problem("Possible concussion", LocalDateTime.now(),
                "From car accident", p.getElasticID());
        ProblemRecordListController.getProblemList().add_internal(pr);

        r = new Record("Car crash", pr.getElasticID());
        r.setComment(recordComment);
        ProblemRecordListController.getRecordList().add_internal(r);

        Intent i = new Intent();
        i.putExtra("Problem", pr.getElasticID());
        i.putExtra("Record", r.getElasticID());
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestEditRecordTitle() {
        onView(withId(R.id.editRecordTitleView))
                .check(matches(withText(recordTitle)));
        onView(withId(R.id.editRecordComment))
                .check(matches(withText(recordComment)));

        onView(withId(R.id.editRecordTitleView))
                .perform(clearText(), typeText(newTitle), closeSoftKeyboard());

        onView(withId(R.id.editRecordSaveButton))
                .perform(click());

        assertTrue(r.getTitle().equals(newTitle));
        assertTrue(r.getComment().equals(recordComment));
        assertTrue(intentsTestRule.getActivity().isFinishing());
    }

    @Test
    public void TestEditRecordTitleAndComment() {
        onView(withId(R.id.editRecordTitleView))
                .check(matches(withText(recordTitle)));
        onView(withId(R.id.editRecordComment))
                .check(matches(withText(recordComment)));

        onView(withId(R.id.editRecordTitleView))
                .perform(clearText(), typeText(newTitle), closeSoftKeyboard());
        onView(withId(R.id.editRecordComment))
                .perform(clearText(), typeText(newComment), closeSoftKeyboard());

        onView(withId(R.id.editRecordSaveButton))
                .perform(click());

        assertTrue(r.getTitle().equals(newTitle));
        assertTrue(r.getComment().equals(newComment));
        assertTrue(intentsTestRule.getActivity().isFinishing());
    }

    @After
    public void after() {
        UserListController.getUserList().remove_internal(p);
        ProblemRecordListController.getProblemList().remove_internal(pr);
        ProblemRecordListController.getRecordList().remove_internal(r);
    }
}
