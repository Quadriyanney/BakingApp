package com.quadriyanney.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import com.quadriyanney.bakingapp.R;

import static com.quadriyanney.bakingapp.util.Constants.PREFERENCE_RECIPE;

public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views =
                new RemoteViews(context.getPackageName(), R.layout.recipe_ingredient_widget);

        SharedPreferences sharedPreferences =
                context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String recipe = sharedPreferences.getString(PREFERENCE_RECIPE, "");

        Intent intent = new Intent(context, WidgetIntentService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        views.setTextViewText(R.id.tvRecipeName, recipe + " Ingredients");
        views.setRemoteAdapter(appWidgetId, R.id.lvIngredients, intent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}