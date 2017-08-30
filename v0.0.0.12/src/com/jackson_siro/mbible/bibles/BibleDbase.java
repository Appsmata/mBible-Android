package com.jackson_siro.mbible.bibles;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.util.Log;
import java.util.HashMap;

import com.jackson_siro.mbible.*;

public class BibleDbase {
    private static final String TAG = "BibleDatabase";

    public static final String BIBLE_TITLE = SearchManager.SUGGEST_COLUMN_TEXT_1;
    public static final String BIBLE_CONTENT = SearchManager.SUGGEST_COLUMN_TEXT_2;
    
    private static final String DATABASE_NAME = "mBible";
    private static final String DATABASE_TABLE = "mybible";
    private static final int DATABASE_VERSION = 2;

    private final BibleOpenHelper mDatabaseOpenHelper;
    private static final HashMap<String,String> mColumnMap = buildColumnMap();

    
    public BibleDbase(Context context) {
    	
    	mDatabaseOpenHelper = new BibleOpenHelper(context);
    }

    private static HashMap<String,String> buildColumnMap() {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put(BIBLE_TITLE, BIBLE_TITLE);
        map.put(BIBLE_CONTENT, BIBLE_CONTENT);
        map.put(BaseColumns._ID, "rowid AS " + BaseColumns._ID);    	
    	map.put( SearchManager.SUGGEST_COLUMN_ICON_1, "rowid  as " + R.drawable.ic_mbible);
        map.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, "rowid AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
        map.put(SearchManager.SUGGEST_COLUMN_SHORTCUT_ID, "rowid AS " + SearchManager.SUGGEST_COLUMN_SHORTCUT_ID);
        
    	return map;
    }

    public Cursor getText(String rowId, String[] columns) {
        String selection = "rowid = ?";
        String[] selectionArgs = new String[] {rowId};

        return query(selection, selectionArgs, columns);

    }

    public Cursor getTextMatches(String query, String[] columns) {
        String selection = BIBLE_TITLE + " MATCH ?";
        String[] selectionArgs = new String[] {query+"*"};

        return query(selection, selectionArgs, columns);

    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DATABASE_TABLE);
        builder.setProjectionMap(mColumnMap);

        Cursor cursor = builder.query(mDatabaseOpenHelper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    private static class BibleOpenHelper extends SQLiteOpenHelper {

        private final Context mHelperContext;
        private SQLiteDatabase mDatabase;

        BibleOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

}
