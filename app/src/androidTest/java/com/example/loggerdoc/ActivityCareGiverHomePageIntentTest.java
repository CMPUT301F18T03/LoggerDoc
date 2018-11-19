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

public class ActivityCareGiverHomePageIntentTest {

    @Rule
    public IntentsTestRule<ActivityCareGiverHomePage> intentsTestRule =
            new IntentsTestRule<>(ActivityCareGiverHomePage.class, false, false);

    @Before
    // create mock caregiver
    public void setup() {
        CareGiver c = new CareGiver("Test Caregiver", "test@example.com", "555-555-1234", "Caregiver");
        Intent i = new Intent();
        i.putExtra("Caregiver", c);
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestUpdateContactInfo() {
        onView(withId(R.id.CHomePageEditContactInfo))
                .perform(click());
        intended(hasComponent(ActivityUpdateContactInfo.class.getName()));
    }

    @Test
    public void TestBrowsePatients() {
        onView(withId(R.id.browse_patients_button2))
                .perform(click());
        intended(hasComponent(ActivityCareGiverBrowsePatients.class.getName()));
    }
}
