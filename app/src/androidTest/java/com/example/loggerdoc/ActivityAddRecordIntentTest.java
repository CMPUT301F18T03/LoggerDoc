package com.example.loggerdoc;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.ActivityResultMatchers.hasResultCode;
import static android.support.test.espresso.contrib.ActivityResultMatchers.hasResultData;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ActivityAddRecordIntentTest {

    private Problem pr;

    @Rule
    public IntentsTestRule<ActivityAddRecord> intentsTestRule =
            new IntentsTestRule<>(ActivityAddRecord.class, false, false);

    @Before
    // create mock intent with mock patient and mock problem
    public void before() {
        Patient p = new Patient("Patty2222", "testPatient@example.com", "555-123-4567", "Patient");
        Problem pr = new Problem("Possible concussion", LocalDateTime.now(),
                "From car accident", p.getElasticID());
        ProblemRecordListController.getProblemList().add_internal(pr);

        Intent i = new Intent();
        i.putExtra("Problem", pr.getElasticID());
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestAddRecord() {
        String testRecordTitle = "Cracked face";
        String testRecordComment = "Really hurts";

        onView(withId(R.id.record_title_text))
                .perform(typeText(testRecordTitle), closeSoftKeyboard());
        onView(withId(R.id.record_comment_text))
                .perform(typeText(testRecordComment), closeSoftKeyboard());
        onView(withId(R.id.record_create_button))
                .perform(click());

        Record r = ProblemRecordListController.getRecordList().getArray().get(0);
        assertTrue(r.getTitle().equals(testRecordTitle));
        assertTrue(intentsTestRule.getActivity().isFinishing());
    }

    @After
    public void after() {
        ProblemRecordListController.getProblemList().remove_internal(pr);
    }
}
