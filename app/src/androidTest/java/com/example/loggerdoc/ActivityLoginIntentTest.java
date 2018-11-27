package com.example.loggerdoc;

import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ActivityLoginIntentTest {

    @Rule
    public IntentsTestRule<ActivityLogin> intentsTestRule =
            new IntentsTestRule<>(ActivityLogin.class);

    @Before
    // create mock users and mock userlist
    public void setup() {
        Patient p = new Patient("Test Patient", "test@example.com", "555-555-1234", "Patient");
        CareGiver c = new CareGiver("Test Caregiver", "test@example.com", "555-555-1234", "Caregiver");

        UserListController.getUserList().add_internal(p);
        UserListController.getUserList().add_internal(c);
    }

    @Test
    public void TestLoginToPatientHomePage() {
        onView(withId(R.id.Username_Field))
                .perform(typeText("Test Patient"), closeSoftKeyboard());
        onView(withId(R.id.Login_Button))
                .perform(click());
        intended(hasComponent(ActivityPatientHomePage.class.getName()));
    }

    @Test
    public void TestLoginToCareGiverHomePage() {
        onView(withId(R.id.Username_Field))
                .perform(typeText("Test Caregiver"), closeSoftKeyboard());
        onView(withId(R.id.Login_Button))
                .perform(click());
        intended(hasComponent(ActivityCareGiverHomePage.class.getName()));
    }
}
