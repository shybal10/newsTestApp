package com.shybal.test.newsapp;

import android.support.test.espresso.assertion.ViewAssertions;

import com.shybal.test.newsapp.view.DetailsActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;

public class DetailsActivityTest {

    @Rule
    public final ActivityRule<DetailsActivity> details = new ActivityRule<>(DetailsActivity.class);
    @Test
    public void shoudBeAbleToLoadImages() {
        onView(withContentDescription("content image" )).check(ViewAssertions.matches(isDisplayed()));
    }


}
