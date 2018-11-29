package com.example.loggerdoc;

import android.content.Intent;
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
import static com.example.loggerdoc.IntentTestUtils.waitForUserListUpdate;

public class ActivityLoginIntentTest {

    @Rule
    public IntentsTestRule<ActivityLogin> intentsTestRule =
            new IntentsTestRule<>(ActivityLogin.class, false, false);

    @Before
    public void before() {
        intentsTestRule.launchActivity(new Intent());
        waitForUserListUpdate();
    }

    @Test
    public void TestLoginToPatientHomePage() {
        Patient p = new Patient("Patty2222", "testpatient@example.com", "555-123-4567", "Patient");
        UserListController.getUserList().add_internal(p);

        onView(withId(R.id.Username_Field))
                .perform(typeText("Patty2222"), closeSoftKeyboard());
        onView(withId(R.id.Login_Button))
                .perform(click());
        intended(hasComponent(ActivityPatientHomePage.class.getName()));
    }

    @Test
    public void TestLoginToCareGiverHomePage() {
        CareGiver c = new CareGiver("CareBear", "testcaregiver@example.com", "555-555-1234", "Caregiver");
        UserListController.getUserList().add_internal(c);

        onView(withId(R.id.Username_Field))
                .perform(typeText("CareBear"), closeSoftKeyboard());
        onView(withId(R.id.Login_Button))
                .perform(click());
        intended(hasComponent(ActivityCareGiverHomePage.class.getName()));
    }
}
