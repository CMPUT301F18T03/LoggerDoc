package com.example.loggerdoc;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.loggerdoc.IntentTestUtils.waitForUserListUpdate;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ActivityCareGiverAddPatientIntentTest {

    private CareGiver c;
    private Patient p;

    @Rule
    public IntentsTestRule<ActivityCareGiverAddPatient> intentsTestRule =
            new IntentsTestRule<>(ActivityCareGiverAddPatient.class, false, false);

    @Before
    public void before() {
        c = new CareGiver("DoctorJim", "testcaregiver@example.com", "555-555-1234", "Caregiver");
        p = new Patient("NotADoctor", "testpatient@example.com", "555-123-4567", "Patient");

        UserListController.getUserList().add_internal(c);
        UserListController.getUserList().add_internal(p);

        Intent i = new Intent();
        i.putExtra("Caregiver", c.getElasticID());
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestAddPatientToCaregiver() {
        String testPatientName = "NotADoctor";

        onView(withId(R.id.AddPatientEditText))
                .perform(typeText(testPatientName), closeSoftKeyboard());
        onView(withId(R.id.AddPatientButton))
                .perform(click());

        assertTrue(c.getPatientList().contains(p.getElasticID()));
        assertTrue(intentsTestRule.getActivity().isFinishing());
    }

    @Test
    public void TestAddNonExistentPatient() {
        String testPatientName = "NotARealUser26277";

        onView(withId(R.id.AddPatientEditText))
                .perform(typeText(testPatientName), closeSoftKeyboard());
        onView(withId(R.id.AddPatientButton))
                .perform(click());

        assertFalse(c.getPatientList().contains(p.getElasticID()));
        assertFalse(intentsTestRule.getActivity().isFinishing());
    }

    @After
    public void after() {
        UserListController.getUserList().remove_internal(c);
        UserListController.getUserList().remove_internal(p);
    }
}
