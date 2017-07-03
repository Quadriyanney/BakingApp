package com.quadriyanney.bakingapp.widget;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

public class CustomProvider extends ContentProvider{

    private SQLiteDatabase db;
    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(CustomContract.AUTHORITY, CustomContract.TABLE, CustomContract.INGREDIENT_LIST);
        uriMatcher.addURI(CustomContract.AUTHORITY, CustomContract.TABLE+"/#", CustomContract.INGREDIENT_ITEM);
    }

    @Override
    public boolean onCreate() {
        boolean ret = true;
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        db = databaseHelper.getWritableDatabase();

        if (db == null) {
            ret = false;
        }

        assert db != null;
        if (db.isReadOnly()) {
            db.close();
            db = null;
            ret = false;
        }
        return ret;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(CustomContract.TABLE);

        switch (uriMatcher.match(uri)) {
            case CustomContract.INGREDIENT_LIST:
                break;

            case CustomContract.INGREDIENT_ITEM:
                qb.appendWhere(CustomContract.Columns._ID + " = " + uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }

        return qb.query(db, projection, selection, selectionArgs, null, null, null);
    }

    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)) {
            case CustomContract.INGREDIENT_LIST:
                return CustomContract.CONTENT_TYPE;

            case CustomContract.INGREDIENT_ITEM:
                return CustomContract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }

    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        if (uriMatcher.match(uri) != CustomContract.INGREDIENT_LIST) {
            throw new IllegalArgumentException("Invalid URI: " + uri);
        }

        long id = db.insert(CustomContract.TABLE, null, contentValues);

        if (id>0) {
            return ContentUris.withAppendedId(uri, id);
        }
        throw new SQLException("Error inserting into table: " + CustomContract.TABLE);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        int deleted = 0;

        switch (uriMatcher.match(uri)) {
            case CustomContract.INGREDIENT_LIST:
                db.delete(CustomContract.TABLE, selection, selectionArgs);
                break;

            case CustomContract.INGREDIENT_ITEM:
                String where = CustomContract.Columns._ID + " = " + uri.getLastPathSegment();
                if (!selection.isEmpty()) {
                    where += " AND "+selection;
                }

                deleted = db.delete(CustomContract.TABLE, where, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {

        int updated = 0;

        switch (uriMatcher.match(uri)) {
            case CustomContract.INGREDIENT_LIST:
                db.update(CustomContract.TABLE, contentValues, s, strings);
                break;

            case CustomContract.INGREDIENT_ITEM:
                String where = CustomContract.Columns._ID + " = " + uri.getLastPathSegment();
                if (!s.isEmpty()) {
                    where += " AND " + s;
                }
                updated = db.update(CustomContract.TABLE, contentValues, where, strings);
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
        return updated;
    }
}
