package com.example.nick.hsvcolor.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by Nick on 4/22/2015.
 */
public class ColorContentProvider extends ContentProvider{
    private ColorDBHelper db;

    private static final String AUTHORITY = "com.example.nick.hsvcolor";
    private static final String BASE_PATH = "color";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/colors";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/color";
    public static final String CONTENT_URI_PREFIX = "content://" + AUTHORITY + "/" + BASE_PATH + "/";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int COLORS = 1;

    static {
        //Defining endpoints
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, COLORS);
    }

    @Override
    public boolean onCreate() {
        db = new ColorDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(ColorTable.COLOR_TABLE);

        int uriType = sURIMatcher.match(uri);

        switch (uriType){
            case COLORS:
                //no where statement because we want all tasks by default
                break;
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }

        SQLiteDatabase wdb = db.getWritableDatabase();
        Cursor cursor = queryBuilder.query(wdb, projection, selection, selectionArgs, null, null, sortOrder);

        //Tell the cursor to be notified by this URI when a change in the data occurs (such as an insert)
        //The cursor will then update itself with the new data
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase wdb = db.getWritableDatabase();

        long id = 0;

        int uriType = sURIMatcher.match(uri);
        switch (uriType){
            case COLORS:
                id = wdb.insert(ColorTable.COLOR_TABLE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(CONTENT_URI_PREFIX + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
