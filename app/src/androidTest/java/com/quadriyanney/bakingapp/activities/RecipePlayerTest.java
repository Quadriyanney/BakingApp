package com.quadriyanney.bakingapp.activities;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.quadriyanney.bakingapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipePlayerTest {

    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<Recipes> mActivityTestRule = new ActivityTestRule<>(Recipes.class);

    @Before
    public void registerIdlingResource() {
        idlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void recipePlayerTest() {
        ViewInteraction recyclerView = onView(allOf(withId(R.id.recyclerView),
                withParent(allOf(withId(R.id.root_layout),
                        withParent(withId(android.R.id.content)))), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        
        ViewInteraction recyclerView2 = onView(allOf(withId(R.id.stepsRecyclerView),
                withParent(allOf(withId(R.id.stepLayout), withParent(withId(R.id.viewPager)))),
                isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        try {
            Thread.sleep(5000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
        
        ViewInteraction appCompatImageButton = onView(allOf(withId(R.id.exo_pause),
                withContentDescription("Pause"), isDisplayed()));
        appCompatImageButton.perform(click());

        try {
            Thread.sleep(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
        
        ViewInteraction appCompatImageButton2 = onView(allOf(withId(R.id.exo_play),
                withContentDescription("Play"), isDisplayed()));
        appCompatImageButton2.perform(click());
        

        try {
            Thread.sleep(5000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
        
        ViewInteraction appCompatImageButton3 = onView(allOf(withId(R.id.exo_ffwd),
                withContentDescription("Fast forward"), isDisplayed()));
        appCompatImageButton3.perform(click());

            try {
                Thread.sleep(5000);
             } catch (InterruptedException e) {
                e.printStackTrace();
             }
        
        ViewInteraction appCompatImageButton4 = onView(allOf(withId(R.id.exo_rew),
                withContentDescription("Rewind"), isDisplayed()));
        appCompatImageButton4.perform(click());

        try {
            Thread.sleep(5000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
        
        ViewInteraction appCompatImageButton5 = onView(allOf(withId(R.id.exo_prev),
                withContentDescription("Previous track"), isDisplayed()));
        appCompatImageButton5.perform(click());
    }

    @After
    public void unRegisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}
