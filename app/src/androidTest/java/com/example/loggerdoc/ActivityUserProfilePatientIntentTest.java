package com.example.loggerdoc;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;

public class ActivityUserProfilePatientIntentTest {

    private Patient p;

    private String patientName = "TimothyPleb";
    private String patientEmail = "testpatient@example.com";
    private String patientPhone = "555-123-4567";


    @Rule
    public IntentsTestRule<ActivityUserProfile> intentsTestRule =
            new IntentsTestRule<>(ActivityUserProfile.class, false, false);

    @Before
    public void before() {
        p = new Patient(patientName, patientEmail, patientPhone, "Patient");
        UserListController.getUserList().add_internal(p);
        UserListController.setCurrentUser(p);

        Intent i = new Intent();
        i.putExtra("Patient", p.getElasticID());
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestUserProfileAsPatient() {
        onView(withId(R.id.UserNameDisplay))
                .check(matches(withText(patientName)));
        onView(withId(R.id.edit_contact_info_email_view))
                .check(matches(withText(patientEmail)));
        onView(withId(R.id.edit_contact_info_number_view))
                .check(matches(withText(patientPhone)));
        onView(withId(R.id.backButton))
                .perform(click());

        assertTrue(intentsTestRule.getActivity().isFinishing());
    }

    @Test
    public void TestEditProfileFromProfile() {
        onView(withId(R.id.EditContactInfoButton))
                .perform(click());
        intended(hasComponent(ActivityUpdateContactInfo.class.getName()));
    }

    @After
    public void after() {
        UserListController.getUserList().remove_internal(p);
    }
}
