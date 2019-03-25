package com.quadriyanney.bakingapp.widget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class DatabaseHelper extends SQLiteOpenHelper {

    DatabaseHelper(Context context) {
        super(context, DatabaseContract.DB_NAME, null, DatabaseContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuery = "CREATE TABLE " + DatabaseContract.TABLE +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.Columns.QUANTITY + " TEXT, " +
                DatabaseContract.Columns.MEASURE + " TEXT, " +
                DatabaseContract.Columns.INGREDIENT + ")";

        sqlDB.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
        sqlDB.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE);
        onCreate(sqlDB);
    }
}
