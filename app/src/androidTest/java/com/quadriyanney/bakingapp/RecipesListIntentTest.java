package com.quadriyanney.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.quadriyanney.bakingapp.util.Constants;
import com.quadriyanney.bakingapp.ui.recipesList.RecipesListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RecipesListIntentTest {

    private IdlingResource idlingResource;

    @Rule
    public IntentsTestRule<RecipesListActivity> recipesIntentsTestRule =
            new IntentsTestRule<>(RecipesListActivity.class);

    @Before
    public void  registerIdlingResource(){
        idlingResource = recipesIntentsTestRule.getActivity().getIdlingResource();

        Espresso.registerIdlingResources(idlingResource);
        intending(not(isInternal())).respondWith(
                new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void recyclerViewItemClick() {
        onView(withId(R.id.rvRecipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        intended(hasExtraWithKey(Constants.EXTRA_RECIPE));
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}
