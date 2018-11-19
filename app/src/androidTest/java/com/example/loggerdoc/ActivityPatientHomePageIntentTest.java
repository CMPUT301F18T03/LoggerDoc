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

public class ActivityPatientHomePageIntentTest {

    @Rule
    public IntentsTestRule<ActivityPatientHomePage> intentsTestRule =
            new IntentsTestRule<>(ActivityPatientHomePage.class, false, false);

    @Before
    // create mock patient
    public void setup() {
        Patient p = new Patient("Test Patient", "test@example.com", "555-555-1234", "Patient");
        Intent i = new Intent();
        i.putExtra("Patient", p);
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestUpdateContactInfo() {
        onView(withId(R.id.PHomePageEditContactInfo))
                .perform(click());
        intended(hasComponent(ActivityUpdateContactInfo.class.getName()));
    }

    @Test
    public void TestBrowseProblems() {
        onView(withId(R.id.browse_problems_button))
                .perform(click());
        intended(hasComponent(ActivityBrowseProblems.class.getName()));
    }
}
