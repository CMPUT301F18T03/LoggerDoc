package com.example.loggerdoc;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

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
import static org.hamcrest.CoreMatchers.anything;

public class ActivityBrowseProblemsIntentTest {

    private Problem pr;

    @Rule
    public IntentsTestRule<ActivityBrowseProblems> intentsTestRule =
            new IntentsTestRule<>(ActivityBrowseProblems.class, false, false);

    @Before
    // create mock patient with mock problem
    public void setup() {
        Patient p = new Patient("Patty2222", "testpatient@example.com", "555-123-4567", "Patient");
        pr = new Problem("Ear Infection", LocalDateTime.now(), "Right ear", p.getElasticID());
        p.getProblems().add(pr.getElasticID());

        UserListController.getUserList().add_internal(p);
        ProblemRecordListController.getProblemList().add_internal(pr);

        Intent i = new Intent();
        i.putExtra("Patient", p.getElasticID());
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestViewProblemFromBrowse() {
        onData(anything()).inAdapterView(withId(R.id.ProblemList)).atPosition(0)
                .perform(click());
        intended(hasComponent(ActivityViewProblem.class.getName()));
    }

    @Test
    public void TestAddProblemFromBrowse() {
        onView(withId(R.id.addProblemButton))
                .perform(click());
        intended(hasComponent(ActivityAddProblem.class.getName()));
    }

    @Test
    public void TestSearchProblemFromBrowse() {
        onView(withId(R.id.searchButton))
                .perform(click());
        intended(hasComponent(ActivitySearch.class.getName()));
    }

    @Test
    public void TestViewProfileFromBrowse() {
        onView(withId(R.id.usernameText))
                .perform(click());
        intended(hasComponent(ActivityUserProfile.class.getName()));
    }

    @After
    public void after() {
        ProblemRecordListController.getProblemList().remove(pr);
    }

}
