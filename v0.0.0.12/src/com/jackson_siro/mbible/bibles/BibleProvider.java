package com.jackson_siro.mbible.bibles;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class BibleProvider extends ContentProvider {
    String TAG = "BibleProvider";

    public static String AUTHORITY = "com.jackson_siro.mbible.bibles.BibleProvider";
	 
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/mBible");

    public static final String BIBLES_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
                                                  "/vnd.example.android.mBible";
    public static final String DEFINITION_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                                                       "/vnd.example.android.mBible";

    private BibleDatabase mTextbook;

    // UriMatcher stuff
    private static final int SEARCH_BIBLES = 0;
    private static final int GET_BIBLE = 1;
    private static final int SEARCH_SUGGEST = 2;
    private static final int REFRESH_SHORTCUT = 3;
    private static final UriMatcher sURIMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher =  new UriMatcher(UriMatcher.NO_MATCH);
        
        matcher.addURI(AUTHORITY, "mBible", SEARCH_BIBLES);
        matcher.addURI(AUTHORITY, "mBible/#", GET_BIBLE);
        
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH_SUGGEST);

        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT, REFRESH_SHORTCUT);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT + "/*", REFRESH_SHORTCUT);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mTextbook = new BibleDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        // Use the UriMatcher to see what kind of query we have and format the db query accordingly
        switch (sURIMatcher.match(uri)) {
            case SEARCH_SUGGEST:
                if (selectionArgs == null) {
                  throw new IllegalArgumentException(
                      "selectionArgs must be provided for the Uri: " + uri);
                }
                return getSuggestions(selectionArgs[0]);
            case SEARCH_BIBLES:
                if (selectionArgs == null) {
                  throw new IllegalArgumentException(
                      "selectionArgs must be provided for the Uri: " + uri);
                }
                return search(selectionArgs[0]);
            case GET_BIBLE:
                return getText(uri);
            case REFRESH_SHORTCUT:
                return refreshShortcut(uri);
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    private Cursor getSuggestions(String query) {
      //query = query.toLowerCase();
      String[] columns = new String[] {
          BaseColumns._ID,
          BibleDatabase.BIBLE_TITLE,
          BibleDatabase.BIBLE_CONTENT,
       /* SearchManager.SUGGEST_COLUMN_SHORTCUT_ID,
                        (only if you want to refresh shortcuts) */
          SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID};

      return mTextbook.getTextMatches(query, columns);
    }

    private Cursor search(String query) {
      //query = query.toLowerCase();
      String[] columns = new String[] {
          BaseColumns._ID,
          BibleDatabase.BIBLE_TITLE,
          BibleDatabase.BIBLE_CONTENT};

      return mTextbook.getTextMatches(query, columns);
    }
    
    private Cursor getText(Uri uri) {
      String rowId = uri.getLastPathSegment();
      String[] columns = new String[] { BibleDatabase.BIBLE_TITLE, BibleDatabase.BIBLE_CONTENT};

      return mTextbook.getText(rowId, columns);
    }

    private Cursor refreshShortcut(Uri uri) {
      String rowId = uri.getLastPathSegment();
      String[] columns = new String[] {
          BaseColumns._ID,
          BibleDatabase.BIBLE_TITLE,
          BibleDatabase.BIBLE_CONTENT,
          SearchManager.SUGGEST_COLUMN_SHORTCUT_ID,
          SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID};

      return mTextbook.getText(rowId, columns);
    }

    /**
     * This method is required in order to query the supported types.
     * It's also useful in our own query() method to determine the type of Uri received.
     */
    @Override
    public String getType(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case SEARCH_BIBLES:
                return BIBLES_MIME_TYPE;
            case GET_BIBLE:
                return DEFINITION_MIME_TYPE;
            case SEARCH_SUGGEST:
                return SearchManager.SUGGEST_MIME_TYPE;
            case REFRESH_SHORTCUT:
                return SearchManager.SHORTCUT_MIME_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
    }

    // Other required implementations...

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

}
