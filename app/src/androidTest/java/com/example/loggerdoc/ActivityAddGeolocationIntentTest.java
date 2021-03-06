package com.example.loggerdoc;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.GrantPermissionRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.ActivityResultMatchers.hasResultCode;
import static android.support.test.espresso.contrib.ActivityResultMatchers.hasResultData;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.MatcherAssert.assertThat;

public class ActivityAddGeolocationIntentTest {

    // ensure proper permissions have been granted
    @Rule
    public GrantPermissionRule finePerm = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);
    @Rule
    public GrantPermissionRule coarsePerm = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_COARSE_LOCATION);

    private Patient p;
    private Problem pr;

    @Rule
    public IntentsTestRule<ActivityAddGeolocation> intentsTestRule =
            new IntentsTestRule<>(ActivityAddGeolocation.class, false, false);

    @Before
    // create mock intent with mock patient
    public void before() {
        p = new Patient("Patty2222", "testPatient@example.com", "555-555-1234", "Patient");
        pr = new Problem("Test Problem", LocalDateTime.now(),
                "Test Problem's Description", p.getElasticID());

        Intent intent = new Intent();
        intent.putExtra("Problem", pr.getElasticID());
        intentsTestRule.launchActivity(intent);
    }

    @Test
    public void TestResultFromActivity() {
        onView(withId(R.id.saveGeolocationButton))
                .perform(click());
        assertThat(intentsTestRule.getActivityResult(), hasResultCode(Activity.RESULT_OK));
        assertThat(intentsTestRule.getActivityResult(),
                hasResultData(IntentMatchers.hasExtraWithKey("geoLocation")));
    }

    @After
    public void after() {
        UserListController.getUserList().remove(p,
                intentsTestRule.getActivity().getBaseContext());
        ProblemRecordListController.getProblemList().remove(pr,
                intentsTestRule.getActivity().getBaseContext());
    }
}
