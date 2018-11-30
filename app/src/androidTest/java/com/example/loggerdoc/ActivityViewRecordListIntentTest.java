package com.example.loggerdoc;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

public class ActivityViewRecordListIntentTest {

    private Patient p;
    private Problem pr;
    private Record r;

    @Rule
    public IntentsTestRule<ActivityViewRecordList> intentsTestRule =
            new IntentsTestRule<>(ActivityViewRecordList.class, false, false);

    @Before
    // create mock patient with mock problem
    public void setup() {
        p = new Patient("Patty2222", "testPatient@example.com", "555-123-4567", "Patient");
        UserListController.getUserList().add_internal(p);
        UserListController.setCurrentUser(p);

        pr = new Problem("Possible concussion", LocalDateTime.now(),
                "From car accident", p.getElasticID());
        ProblemRecordListController.getProblemList().add_internal(pr);

        r = new Record("Car crash", pr.getElasticID());
        ProblemRecordListController.getRecordList().add_internal(r);

        Intent i = new Intent();
        i.putExtra("Problem", pr.getElasticID());
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void TestAddRecordFromRecordList() {
        onView(withId(R.id.addRecordButton))
                .perform(click());
        intended(hasComponent(ActivityAddRecord.class.getName()));
    }

    // subject to change once search is implemented
    @Test
    public void TestSearchFromRecordList() {
        onView(withId(R.id.recordSearchButton))
                .perform(click());
        intended(hasComponent(ActivitySearch.class.getName()));
    }

    @Test
    public void TestViewRecordFromRecordList() {
        onData(anything()).inAdapterView(withId(R.id.recordsListView)).atPosition(0)
                .perform(click());
        intended(hasComponent(ActivityViewRecord.class.getName()));
    }

    @After
    public void after() {
        UserListController.getUserList().remove_internal(p);
        ProblemRecordListController.getProblemList().remove_internal(pr);
        ProblemRecordListController.getRecordList().remove_internal(r);
    }
}
