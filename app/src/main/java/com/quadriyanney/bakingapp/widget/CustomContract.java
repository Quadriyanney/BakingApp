package com.quadriyanney.bakingapp.widget;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

class CustomContract {
	static final String DB_NAME = "com.quadriyanney.bakingapp.widget.ingredients";
	static final int DB_VERSION = 1;
	static final String TABLE = "ingredients";
    static final String AUTHORITY = "com.quadriyanney.bakingapp.widget";
    static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    static final int INGREDIENT_LIST = 1;
    static final int INGREDIENT_ITEM = 2;
    static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/quadriyanney.ingredientDB/"+TABLE;
    static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/quadriyanney/ingredientDB" + TABLE;

	class Columns {
		static final String QUANTITY = "quantity";
		static final String MEASURE = "measure";
		static final String INGREDIENT = "ingredient";
		static final String _ID = BaseColumns._ID;
	}
}
