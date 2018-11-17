package com.example.loggerdoc;

import static android.support.test.espresso.Espresso.onView;
import android.support.test.filters.LargeTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityCareGiverBrowsePatientIntentTest {

    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<ActivityCareGiverBrowsePatients> mActivityRule
            = new ActivityTestRule<>(ActivityCareGiverBrowsePatients.class);



    @Test
    public void testTestTest() {

        onView(withId(R.id.addProblemButton))
                .perform(click());



    }
}