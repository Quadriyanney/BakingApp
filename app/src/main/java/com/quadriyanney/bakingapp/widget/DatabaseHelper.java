package com.quadriyanney.bakingapp.widget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


class DatabaseHelper extends SQLiteOpenHelper {

	DatabaseHelper(Context context) {
		super(context, CustomContract.DB_NAME, null, CustomContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqlDB) {
		String sqlQuery = "CREATE TABLE "+ CustomContract.TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				CustomContract.Columns.QUANTITY + " TEXT, " +
				CustomContract.Columns.MEASURE + " TEXT, "+
				CustomContract.Columns.INGREDIENT + ")";

		sqlDB.execSQL(sqlQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
		sqlDB.execSQL("DROP TABLE IF EXISTS "+ CustomContract.TABLE);
		onCreate(sqlDB);
	}
}
