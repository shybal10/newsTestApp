package com.shybal.test.newsapp;

import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.Espresso;

import com.shybal.test.newsapp.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);
    @Test
    public void shouldBeAbleToLaunchMainScreen() {
        onView(withHint("Type Name Here" )).check(ViewAssertions.matches(isDisplayed()));
    }

}
