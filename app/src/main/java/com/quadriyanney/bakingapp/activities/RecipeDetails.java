package com.quadriyanney.bakingapp.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.IngredientsInfo;
import com.quadriyanney.bakingapp.fragments.IngredientsFragment;
import com.quadriyanney.bakingapp.fragments.StepDetailsFragment;
import com.quadriyanney.bakingapp.fragments.StepsFragment;
import com.quadriyanney.bakingapp.widget.CustomContract;
import com.quadriyanney.bakingapp.widget.WidgetProvider;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetails extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    String recipeName, recipeIngredients, recipeSteps, mMeasurement, mIngredientName;
    Toolbar toolbar;
    int counter = 0, mQuantity;
    SharedPreferences sharedPreferences;
    List<IngredientsInfo> ingredientList = new ArrayList<>();
    private boolean isTwoPane;
    IngredientsFragment ingredientsFragment = new IngredientsFragment();
    StepsFragment stepsFragment = new StepsFragment();
    FragmentTransaction transaction;
    StepDetailsFragment detailsFragment = new StepDetailsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);

        if (getIntent().getExtras().getString("recipe") != null){
            recipeName = getIntent().getExtras().getString("recipe");
        }
        if (getIntent().getExtras().getString("ingredients") != null){
            recipeIngredients = getIntent().getExtras().getString("ingredients");
        }
        if (getIntent().getExtras().getString("steps") != null){
            recipeSteps = getIntent().getExtras().getString("steps");
        }

        toolbar = (Toolbar) findViewById(R.id.detailToolbar);
        toolbar.setTitle(recipeName);
        setSupportActionBar(toolbar);

        setAttributes();

        if (findViewById(R.id.detail_container) != null) {
            isTwoPane = true;
        }

        if (isTwoPane) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            ingredientsFragment.getIngredientsList(recipeIngredients);
            transaction = getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, ingredientsFragment, "ingredients");
            transaction.commit();

            transaction = getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, detailsFragment, "details");
            transaction.commit();
        }
        else {
            viewPager = (ViewPager) findViewById(R.id.viewPager);
            tabLayout = (TabLayout) findViewById(R.id.tabLayout);

            CustomAdapter adapter = new CustomAdapter(getSupportFragmentManager());

            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        }

        sharedPreferences = this.getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    public void setAttributes(){
        try {
            JSONArray jsonIngredients = new JSONArray(recipeIngredients);

            while (counter < jsonIngredients.length()){
                mQuantity = jsonIngredients.getJSONObject(counter).getInt("quantity");
                mMeasurement = jsonIngredients.getJSONObject(counter).getString("measure");
                mIngredientName = jsonIngredients.getJSONObject(counter).getString("ingredient");

                ingredientList.add(new IngredientsInfo(mQuantity, mMeasurement, mIngredientName));
                counter++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbal_menu, menu);
        if (isTwoPane){
            menu.getItem(R.id.stepsButton).setVisible(true);
            menu.getItem(R.id.ingredientsButton).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.widgetButton){
            AddToWidget();
        }
        else if (item.getItemId() == R.id.stepsButton){
            stepsFragment.getStepsList(recipeSteps);
            transaction = getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, stepsFragment, "steps");
            transaction.commit();
        }
        else if (item.getItemId() == R.id.ingredientsButton){
            ingredientsFragment.getIngredientsList(recipeIngredients);
            transaction = getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ingredientsFragment, "ingredients");
            transaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }


    public void AddToWidget(){

        sharedPreferences.edit().putString("name", recipeName).apply();

        Uri uri = CustomContract.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null,
                null, null, null);

        if (cursor != null){
            while (cursor.moveToNext()){
                Uri u = CustomContract.CONTENT_URI;
                getContentResolver().delete(u, CustomContract.Columns._ID + "=?",
                        new String[]{cursor.getString(0)});
            }
        }


        ContentValues cv = new ContentValues();

        for (IngredientsInfo info : ingredientList){
            cv.clear();
            cv.put(CustomContract.Columns.QUANTITY, info.getQuantity());
            cv.put(CustomContract.Columns.MEASURE, info.getMeasurement());
            cv.put(CustomContract.Columns.INGREDIENT, info.getIngredient_name());

            Uri uri1 = CustomContract.CONTENT_URI;
            getApplicationContext().getContentResolver().insert(uri1, cv);
        }

        int [] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(
                new ComponentName(getApplication(), WidgetProvider.class));

        WidgetProvider provider = new WidgetProvider();
        provider.onUpdate(this, AppWidgetManager.getInstance(this), ids);

        Context context = this.getApplicationContext();
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
        int [] appWidgetIds = manager.getAppWidgetIds(thisWidget);
        manager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ing_widget_list);
    }

    private class CustomAdapter extends FragmentStatePagerAdapter{

        CustomAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    ingredientsFragment.getIngredientsList(recipeIngredients);
                    return ingredientsFragment;
                case 1:
                    stepsFragment.getStepsList(recipeSteps);
                    return stepsFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String name = null;
            if (position == 0){
                name = "Ingredients";
            }
            if (position == 1){
                name = "Steps";
            }
            return name;
        }
    }
}
