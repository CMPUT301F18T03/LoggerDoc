package com.example.loggerdoc;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.GrantPermissionRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.assertFalse;

public class ActivityViewRecordIntentTest {

    // ensure proper permissions have been granted
    @Rule
    public GrantPermissionRule photoPerm = GrantPermissionRule.grant(android.Manifest.permission.READ_EXTERNAL_STORAGE);

    private Patient p;
    private Problem pr;
    private Record r;

    @Rule
    public IntentsTestRule<ActivityViewRecord> intentsTestRule =
            new IntentsTestRule<>(ActivityViewRecord.class, false, false);

    @Before
    public void setup() {
        p = new Patient("Patty2222", "testPatient@example.com", "555-123-4567", "Patient");
        UserListController.getUserList().add_internal(p);
        UserListController.setCurrentUser(p);

        pr = new Problem("Possible concussion", LocalDateTime.now(),
                "From car accident", p.getElasticID());
        ProblemRecordListController.getProblemList().add_internal(pr);

        r = new Record("Car crash", pr.getElasticID());
        ProblemRecordListController.getRecordList().add_internal(r);

        Intent i = new Intent();
        i.putExtra("Problem", pr.getElasticID());
        i.putExtra("Record", r.getElasticID());
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestViewImagesFromRecord() {
        onView(withId(R.id.showRecordImage))
                .perform(click());
        intended(hasComponent(ActivityPhotoGrid.class.getName()));
    }

    @Test
    public void TestEditRecordFromRecord() {
        onView(withId(R.id.editRecordButton))
                .perform(click());
        intended(hasComponent(ActivityEditRecord.class.getName()));
    }

    @Test
    public void TestDeleteRecord() {
        onView(withId(R.id.deleteRecordButton))
                .perform(click());
        onView(withId(android.R.id.button1))
                .perform(click());
        assertFalse(ProblemRecordListController.getRecordList().contains(r));
        assertTrue(intentsTestRule.getActivity().isFinishing());
    }

    @After
    public void after() {
        UserListController.getUserList().remove_internal(p);
        ProblemRecordListController.getProblemList().remove_internal(pr);
        ProblemRecordListController.getRecordList().remove_internal(r);
    }
}
