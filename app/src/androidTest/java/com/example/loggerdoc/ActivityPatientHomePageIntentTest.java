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

public class ActivityPatientHomePageIntentTest {

    private Patient p;

    @Rule
    public IntentsTestRule<ActivityPatientHomePage> intentsTestRule =
            new IntentsTestRule<>(ActivityPatientHomePage.class, false, false);

    @Before
    // create mock patient
    public void setup() {
        p = new Patient("Patty2222", "testpatient@example.com", "555-123-4567", "Patient");
        UserListController.getUserList().add_internal(p);
        UserListController.setCurrentUser(p);

        Intent i = new Intent();
        i.putExtra("Patient", p.getElasticID());
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestViewProfile() {
        onView(withId(R.id.PViewProfileButton))
                .perform(click());
        intended(hasComponent(ActivityUserProfile.class.getName()));
    }

    @Test
    public void TestBrowseProblems() {
        onView(withId(R.id.browse_problems_button))
                .perform(click());
        intended(hasComponent(ActivityBrowseProblems.class.getName()));
    }

    @After
    public void after() {
        UserListController.getUserList().remove_internal(p);
    }
}
