package com.example.loggerdoc;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.After;
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

    private CareGiver c;
    private Patient p;

    @Rule
    public IntentsTestRule<ActivityCareGiverBrowsePatients> intentsTestRule =
            new IntentsTestRule<>(ActivityCareGiverBrowsePatients.class, false, false);

    @Before
    // create mock caregiver with mock patient
    public void setup() {
        c = new CareGiver("CareBear", "testcaregiver@example.com", "555-555-1234", "Caregiver");
        p = new Patient("Patty2222", "testpatient@example.com", "555-123-4567", "Patient");
        c.addPatient(p);

        UserListController.getUserList().add_internal(c);
        UserListController.getUserList().add_internal(p);
        UserListController.setCurrentUser(c);

        Intent i = new Intent();
        i.putExtra("Caregiver", c.getElasticID());
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

    @After
    public void after() {
        UserListController.getUserList().remove(c,
                intentsTestRule.getActivity().getBaseContext());
        UserListController.getUserList().remove(p,
                intentsTestRule.getActivity().getBaseContext());
    }
}
