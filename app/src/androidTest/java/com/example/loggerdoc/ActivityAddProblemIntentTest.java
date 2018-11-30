package com.example.loggerdoc;

import android.app.Activity;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.contrib.ActivityResultMatchers.hasResultCode;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.ActivityResultMatchers.hasResultData;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ActivityAddProblemIntentTest {

    @Rule
    public IntentsTestRule<ActivityAddProblem> intentsTestRule =
            new IntentsTestRule<>(ActivityAddProblem.class);

    @Test
    public void TestResultFromActivity() {
        String testProblemTitle = "Broken kneecaps";
        String testProblemDesc = "Bicycle accident";

        onView(withId(R.id.problem_Title_Text))
                .perform(typeText(testProblemTitle), closeSoftKeyboard());
        onView(withId(R.id.problem_desc_text))
                .perform(typeText(testProblemDesc), closeSoftKeyboard());
        onView(withId(R.id.button))
                .perform(click());

        Problem pr = ProblemRecordListController.getProblemList().getArray().get(0);
        assertTrue(pr.getTitle().equals(testProblemTitle));
        assertTrue(pr.getDescription().equals(testProblemDesc));

        ProblemRecordListController.getProblemList().remove(pr,
                intentsTestRule.getActivity().getBaseContext());
    }
}
