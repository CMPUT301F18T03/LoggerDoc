package com.example.loggerdoc;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

public class ActivityCareGiverBrowsePatientsIntentTest {

    @Rule
    public IntentsTestRule<ActivityCareGiverBrowsePatients> intentsTestRule =
            new IntentsTestRule<>(ActivityCareGiverBrowsePatients.class, false, false);

    @Before
    // create mock caregiver with mock patient
    public void setup() {
        CareGiver c = new CareGiver("Test Caregiver", "test@example.com", "555-555-1234", "Caregiver");
        Patient p = new Patient("Test Patient", "test@example.com", "555-555-1234", "Patient");
        c.addPatient(p);
        Intent i = new Intent();
        i.putExtra("Caregiver", c);
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestViewPatientsProblemsFromBrowse() {
        onData(anything()).inAdapterView(withId(R.id.PatientList)).atPosition(0)
                .perform(click());
        intended(hasComponent(ActivityBrowseProblems.class.getName()));
    }

    @Test
    public void TestAddPatientFromBrowse() {
        onView(withId(R.id.addPatientButton))
                .perform(click());
        intended(hasComponent(ActivityCareGiverAddPatient.class.getName()));
    }
}
