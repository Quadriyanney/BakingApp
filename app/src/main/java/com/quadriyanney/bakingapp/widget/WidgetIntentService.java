package com.quadriyanney.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.quadriyanney.bakingapp.R;

public class WidgetIntentService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewsFactory(this.getApplicationContext());
    }

    private class WidgetRemoteViewsFactory implements RemoteViewsFactory {

        private Context mContext;
        private Cursor cursor;

        WidgetRemoteViewsFactory(Context context) {
            mContext = context;
        }

        private void initCursor() {
            if (cursor != null) {
                cursor.close();
            }

            Uri uri = DatabaseContract.CONTENT_URI;
            cursor = mContext.getContentResolver()
                    .query(uri, null, null, null, null);
        }

        @Override
        public void onCreate() {
            initCursor();
        }

        @Override
        public void onDataSetChanged() {
            initCursor();
        }

        @Override
        public void onDestroy() {
            cursor.close();
        }

        @Override
        public int getCount() {
            return cursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

            if (cursor.getCount() != 0) {
                cursor.moveToPosition(position);

                remoteViews.setTextViewText(R.id.tvIngredientName, cursor.getString(3));

                String measure = cursor.getString(1) + " " + cursor.getString(2);
                remoteViews.setTextViewText(R.id.tvIngredientMeasure, measure);
            }
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
