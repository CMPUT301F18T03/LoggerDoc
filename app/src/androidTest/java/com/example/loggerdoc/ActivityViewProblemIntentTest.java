package com.example.loggerdoc;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class ActivityViewProblemIntentTest {

    @Rule
    public IntentsTestRule<ActivityViewProblem> intentsTestRule =
            new IntentsTestRule<>(ActivityViewProblem.class, false, false);

    @Before
    // create mock patient with mock problem
    public void setup() {
        Problem pr = new Problem("Test Problem", new DatePickerFragment(), "Test Problem's Description");
        Patient p = new Patient("Test Patient", "test@example.com", "555-555-1234", "Patient");
        p.getProblems().add(pr);
        Intent i = new Intent();
        i.putExtra("Patient", p);
        i.putExtra("Position", 0);
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestEditProblemFromView() {
        onView(withId(R.id.editButton))
                .perform(click());
        intended(hasComponent(ActivityEditProblem.class.getName()));
    }

    @Test
    public void TestAddRecordFromViewProblem() {
        onView(withId(R.id.addRecordButton))
                .perform(click());
        intended(hasComponent(ActivityAddRecord.class.getName()));
    }

    @Test
    public void TestDeleteProblem() {
        onView(withId(R.id.deleteButton))
                .perform(click());
        onView(withId(android.R.id.button1))
                .perform(click());
        intended(hasComponent(ActivityBrowseProblems.class.getName()));
    }
}
