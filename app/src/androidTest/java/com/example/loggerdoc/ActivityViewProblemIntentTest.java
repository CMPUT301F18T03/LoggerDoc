package com.example.loggerdoc;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class ActivityViewProblemIntentTest {

    private Problem pr;

    @Rule
    public IntentsTestRule<ActivityViewProblem> intentsTestRule =
            new IntentsTestRule<>(ActivityViewProblem.class, false, false);

    @Before
    // create mock patient with mock problem
    public void setup() {
        Patient p = new Patient("Patty2222", "testPatient@example.com", "555-123-4567", "Patient");
        pr = new Problem("Possible concussion", LocalDateTime.now(),
                "From car accident", p.getElasticID());
        ProblemRecordListController.getProblemList().add_internal(pr);

        Intent i = new Intent();
        i.putExtra("Patient", p.getElasticID());
        i.putExtra("Position", pr.getElasticID());
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestEditProblemFromView() {
        onView(withId(R.id.editButton))
                .perform(click());
        intended(hasComponent(ActivityEditProblem.class.getName()));
    }

    @Test
    public void TestViewRecordsFromProblem() {
        onView(withId(R.id.viewRecord))
                .perform(click());
        intended(hasComponent(ActivityViewRecordList.class.getName()));
    }

    @Test
    public void TestDeleteProblem() {
        onView(withId(R.id.deleteButton))
                .perform(click());
        onView(withId(android.R.id.button1))
                .perform(click());
        assertFalse(ProblemRecordListController.getProblemList().contains(pr));
        assertTrue(intentsTestRule.getActivity().isFinishing());
    }
}
