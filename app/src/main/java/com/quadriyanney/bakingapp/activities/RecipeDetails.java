package com.quadriyanney.bakingapp.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.IngredientsInfo;
import com.quadriyanney.bakingapp.data.StepsInfo;
import com.quadriyanney.bakingapp.fragments.IngredientsFragment;
import com.quadriyanney.bakingapp.fragments.StepDetailsFragment;
import com.quadriyanney.bakingapp.fragments.StepsFragment;
import com.quadriyanney.bakingapp.widget.CustomContract;
import com.quadriyanney.bakingapp.widget.WidgetProvider;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class RecipeDetails extends AppCompatActivity implements StepsFragment.ClickListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    String recipeName, recipeIngredients, recipeSteps, mMeasurement, mIngredientName, name;
    String mShortDescription, mDescription, mVideoUrl, mThumbnailUrl;
    Toolbar toolbar;
    JSONArray jsonSteps, jsonIngredients;
    int counter = 0, mQuantity;
    SharedPreferences sharedPreferences;
    ArrayList<IngredientsInfo> ingredientsList = new ArrayList<>();
    ArrayList<StepsInfo> stepsList = new ArrayList<>();
    IngredientsFragment ingredientsFragment = new IngredientsFragment();
    StepsFragment stepsFragment = new StepsFragment();
    private boolean isTwoPane;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);

        if (findViewById(R.id.detail_container) != null) isTwoPane = true;

        if (getIntent().getExtras().getString("recipe") != null){
            recipeName = getIntent().getExtras().getString("recipe");
        }
        if (getIntent().getExtras().getString("ingredients") != null){
            recipeIngredients = getIntent().getExtras().getString("ingredients");
        }
        if (getIntent().getExtras().getString("steps") != null){
            recipeSteps = getIntent().getExtras().getString("steps");
        }

        textView = (TextView) findViewById(R.id.instruction);

        toolbar = (Toolbar) findViewById(R.id.detailToolbar);
        toolbar.setTitle(recipeName);
        setSupportActionBar(toolbar);

        setIngredientsAttributes();
        setStepsAttributes();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        CustomAdapter adapter = new CustomAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        
        sharedPreferences = this.getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    public void setIngredientsAttributes () {
        try {
            jsonIngredients = new JSONArray(recipeIngredients);

            while (counter < jsonIngredients.length()){
                mQuantity = jsonIngredients.getJSONObject(counter).getInt("quantity");
                mMeasurement = jsonIngredients.getJSONObject(counter).getString("measure");
                mIngredientName = jsonIngredients.getJSONObject(counter).getString("ingredient");

                ingredientsList.add(new IngredientsInfo(mQuantity, mMeasurement, mIngredientName));
                counter++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        counter = 0;
    }
    
    public void setStepsAttributes () {
        try {
            jsonSteps = new JSONArray(recipeSteps);

            while (counter < jsonSteps.length()){
                mShortDescription = jsonSteps.getJSONObject(counter).getString("shortDescription");
                mDescription = jsonSteps.getJSONObject(counter).getString("description");
                mVideoUrl = jsonSteps.getJSONObject(counter).getString("videoURL");
                mThumbnailUrl = jsonSteps.getJSONObject(counter).getString("thumbnailURL");

                stepsList.add(new StepsInfo(mShortDescription, mDescription, mVideoUrl, mThumbnailUrl));
                counter++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        counter = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbal_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.widgetButton) AddToWidget();
        return super.onOptionsItemSelected(item);
    }

    public void AddToWidget () {
        sharedPreferences.edit().putString("name", recipeName).apply();

        Uri uri = CustomContract.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor != null){
            while (cursor.moveToNext()){
                getContentResolver().delete(uri, CustomContract.Columns._ID + "=?",
                        new String[]{cursor.getString(0)});
            }
        }

        ContentValues cv = new ContentValues();

        for (IngredientsInfo info : ingredientsList){
            cv.clear();
            cv.put(CustomContract.Columns.QUANTITY, info.getQuantity());
            cv.put(CustomContract.Columns.MEASURE, info.getMeasurement());
            cv.put(CustomContract.Columns.INGREDIENT, info.getIngredient_name());

            getApplicationContext().getContentResolver().insert(uri, cv);
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

    @Override
    public void onStepClicked(int position) {
        if (isTwoPane) {
            if (textView.getVisibility() == View.VISIBLE) textView.setVisibility(View.GONE);
            StepDetailsFragment detailsFragment = new StepDetailsFragment();
            detailsFragment.getDetails(stepsList.get(position).getDescription(),
                    stepsList.get(position).getVideoUrl(),
                    stepsList.get(position).getThumbnailUrl());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, detailsFragment, "details")
                    .commit();
        } else {
            Intent intent = new Intent(RecipeDetails.this, StepDetails.class);
            intent.putExtra("description", stepsList.get(position).getDescription());
            intent.putExtra("vUrl", stepsList.get(position).getVideoUrl());
            intent.putExtra("tUrl", stepsList.get(position).getThumbnailUrl());
            startActivity(intent);
        }
    }

    private class CustomAdapter extends FragmentStatePagerAdapter {

        CustomAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    ingredientsFragment.getIngredientsList(ingredientsList);
                    return ingredientsFragment;
                case 1:
                    stepsFragment.getStepsList(stepsList);
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
            if (position == 0) name = "Ingredients";
            if (position == 1) name = "Steps";
            
            return name;
        }
    }
}