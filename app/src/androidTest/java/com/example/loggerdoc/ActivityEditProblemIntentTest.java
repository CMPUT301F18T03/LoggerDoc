package com.example.loggerdoc;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class ActivityEditProblemIntentTest {

    private Patient p;
    private Problem pr;

    private String problemTitle = "Possible concussion";
    private String problemDesc = "From car accident";

    private String newTitle = "Brain aneurysm";
    private String newDesc = "Potentially fatal";

    @Rule
    public IntentsTestRule<ActivityEditProblem> intentsTestRule =
            new IntentsTestRule<>(ActivityEditProblem.class, false, false);

    @Before
    // create mock patient with mock problem
    public void setup() {
        p = new Patient("Patty2222", "testPatient@example.com", "555-123-4567", "Patient");
        UserListController.getUserList().add_internal(p);
        UserListController.setCurrentUser(p);

        pr = new Problem(problemTitle, LocalDateTime.now(),
                problemDesc, p.getElasticID());
        ProblemRecordListController.getProblemList().add_internal(pr);

        Intent i = new Intent();
        i.putExtra("Patient", p.getElasticID());
        i.putExtra("Problem", pr.getElasticID());
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestEditProblemTitle() {
        onView(withId(R.id.editTitle))
                .check(matches(withText(problemTitle)));
        onView(withId(R.id.edit_prob_desc))
                .check(matches(withText(problemDesc)));

        onView(withId(R.id.editTitle))
                .perform(clearText(), typeText(newTitle), closeSoftKeyboard());

        onView(withId(R.id.edit_problem_save))
                .perform(click());

        assertTrue(pr.getTitle().equals(newTitle));
        assertTrue(pr.getDescription().equals(problemDesc));
        assertTrue(intentsTestRule.getActivity().isFinishing());
    }

    @Test
    public void TestEditProblemTitleAndDescription() {
        onView(withId(R.id.editTitle))
                .check(matches(withText(problemTitle)));
        onView(withId(R.id.edit_prob_desc))
                .check(matches(withText(problemDesc)));

        onView(withId(R.id.editTitle))
                .perform(clearText(), typeText(newTitle), closeSoftKeyboard());
        onView(withId(R.id.edit_prob_desc))
                .perform(clearText(), typeText(newDesc), closeSoftKeyboard());

        onView(withId(R.id.edit_problem_save))
                .perform(click());

        assertTrue(pr.getTitle().equals(newTitle));
        assertTrue(pr.getDescription().equals(newDesc));
        assertTrue(intentsTestRule.getActivity().isFinishing());
    }

    @Test
    public void TestEditProblemTitleAndCancel() {
        onView(withId(R.id.editTitle))
                .check(matches(withText(problemTitle)));
        onView(withId(R.id.edit_prob_desc))
                .check(matches(withText(problemDesc)));

        onView(withId(R.id.editTitle))
                .perform(clearText(), typeText(newTitle), closeSoftKeyboard());

        onView(withId(R.id.edit_problem_cancel))
                .perform(click());

        assertTrue(pr.getTitle().equals(problemTitle));
        assertTrue(pr.getDescription().equals(problemDesc));
        assertTrue(intentsTestRule.getActivity().isFinishing());
    }

    @After
    public void after() {
        UserListController.getUserList().remove(p,
                intentsTestRule.getActivity().getBaseContext());
        ProblemRecordListController.getProblemList().remove(pr,
                intentsTestRule.getActivity().getBaseContext());
    }
}
