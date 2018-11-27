package com.example.loggerdoc;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.ActivityResultMatchers.hasResultCode;
import static android.support.test.espresso.contrib.ActivityResultMatchers.hasResultData;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.MatcherAssert.assertThat;

public class ActivityAddRecordIntentTest {

    @Rule
    public IntentsTestRule<ActivityAddRecord> intentsTestRule =
            new IntentsTestRule<>(ActivityAddRecord.class, false, false);

    /*
    @Before
    // create mock intent with mock patient and mock problem
    public void before() {
        Patient p = new Patient("Test Patient", "test@example.com", "555-555-1234", "Patient");
        Problem pr = new Problem("Test Problem", new DatePickerFragment(), "Test Problem's Description");
        p.getProblems().add(pr);

        Intent intent = new Intent();
        intent.putExtra("Patient", p);
        intent.putExtra("Position", 0);
        intent.putExtra("Flag", "a");
        intentsTestRule.launchActivity(intent);
    }
    */

    @Test
    public void TestResultFromActivity() {
        String testRecordTitle = "Record Title";
        String testRecordComment = "Record Comment";

        onView(withId(R.id.record_title_text))
                .perform(typeText(testRecordTitle), closeSoftKeyboard());
        onView(withId(R.id.record_comment_text))
                .perform(typeText(testRecordComment), closeSoftKeyboard());
        onView(withId(R.id.record_create_button))
                .perform(click());
        assertThat(intentsTestRule.getActivityResult(), hasResultCode(Activity.RESULT_OK));
        assertThat(intentsTestRule.getActivityResult(),
                hasResultData(IntentMatchers.hasExtraWithKey("Record")));
    }
}
