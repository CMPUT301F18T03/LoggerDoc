package com.example.loggerdoc;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import com.robotium.solo.Solo;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.test.annotation.UiThreadTest;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddProblemEspressoTest extends ActivityTestRule<ActivityAddProblem>{

    private Solo solo;

    public AddProblemEspressoTest(){
        super(ActivityAddProblem.class);
    }

    @Before
    public void setUp(){
        super.launchActivity(new Intent());
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @After
    public void endTest(){
        solo.finishOpenedActivities();
    }

    @Test
    public void assertRightActivity(){
        solo.assertCurrentActivity("Wrong Activity", ActivityAddProblem.class);
    }

    @UiThreadTest
    public void testCreate(){
        solo.enterText((EditText)solo.getView(R.id.problem_Title_Text), "NewTitle");
        solo.enterText((EditText)solo.getView(R.id.problem_desc_text), "New Description");

        solo
    }

}
