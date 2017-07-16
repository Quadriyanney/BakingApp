package com.quadriyanney.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.quadriyanney.bakingapp.activities.Recipes;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by quadriy on 7/15/17.
 */

@RunWith(AndroidJUnit4.class)
public class RecipesIdleTest {

    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<Recipes> recipesActivityTestRule = new ActivityTestRule<Recipes>(Recipes.class);

    @Before
    public void registerIdlingResource() {
        idlingResource = recipesActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void listDisplayed() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    @After
    public void unRegisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}
