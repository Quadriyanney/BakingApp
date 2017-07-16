package com.quadriyanney.bakingapp.widget;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class CustomContract {
	public static final String DB_NAME = "com.quadriyanney.bakingapp.widget.ingredients";
	public static final int DB_VERSION = 1;
	static final String TABLE = "ingredients";
    static final String AUTHORITY = "com.quadriyanney.bakingapp.widget";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    static final int INGREDIENT_LIST = 1;
    static final int INGREDIENT_ITEM = 2;
    static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/quadriyanney.ingredientDB/" + TABLE;
    static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/quadriyanney/ingredientDB" + TABLE;

	public class Columns {
		public static final String QUANTITY = "quantity";
		public static final String MEASURE = "measure";
		public static final String INGREDIENT = "ingredient";
		public static final String _ID = BaseColumns._ID;
	}
}
