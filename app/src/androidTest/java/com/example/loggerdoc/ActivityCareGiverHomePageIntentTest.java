package com.example.loggerdoc;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ActivityCareGiverHomePageIntentTest {

    private CareGiver c;

    @Rule
    public IntentsTestRule<ActivityCareGiverHomePage> intentsTestRule =
            new IntentsTestRule<>(ActivityCareGiverHomePage.class, false, false);

    @Before
    // create mock caregiver
    public void setup() {
        c = new CareGiver("CareBear", "testcaregiver@example.com", "555-555-1234", "Caregiver");
        UserListController.getUserList().add_internal(c);

        Intent i = new Intent();
        i.putExtra("Caregiver", c.getElasticID());
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestViewProfile() {
        onView(withId(R.id.CViewProfileButton))
                .perform(click());
        intended(hasComponent(ActivityUserProfile.class.getName()));
    }

    @Test
    public void TestBrowsePatients() {
        onView(withId(R.id.browse_patients_button2))
                .perform(click());
        intended(hasComponent(ActivityCareGiverBrowsePatients.class.getName()));
    }

    @After
    public void after() {
        UserListController.getUserList().remove_internal(c);
    }
}
