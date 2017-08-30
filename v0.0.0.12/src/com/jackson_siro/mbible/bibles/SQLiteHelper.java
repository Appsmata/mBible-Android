package com.jackson_siro.mbible.bibles;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	// database version
	private static final int DATABASE_VERSION = 1;
	// database name
	private static final String DATABASE_NAME = "MasterBible";
	private static final String TABLE_FAVOURS = "Favourites";
	private static final String FAVOUR_ID = "id";
	private static final String FAVOUR_TITLE = "title";
	private static final String FAVOUR_CONTENT = "content";
	
	private static final String TABLE_BIBLE = "BibleInfo";
	private static final String BIBLE_ID = "id";
	private static final String BIBLE_TESTAMENT = "testament";
	private static final String BIBLE_BOOK = "book";
	private static final String BIBLE_CHAPTERS = "chapters";
	private static final String BIBLE_ENGLISH = "english";
	private static final String BIBLE_ABBREVS = "abbrevs";
	private static final String BIBLE_SWAHILI = "swahili";

	private static final String[] COLUMNS_FAVOURITES = { FAVOUR_ID, FAVOUR_TITLE, FAVOUR_CONTENT };
	private static final String[] COLUMNS_BIBLE = { BIBLE_ID, BIBLE_TESTAMENT, BIBLE_BOOK, BIBLE_CHAPTERS,
			BIBLE_ENGLISH, BIBLE_ABBREVS, BIBLE_SWAHILI};

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE " + TABLE_FAVOURS + "(" + FAVOUR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				FAVOUR_TITLE + " TEXT, " + FAVOUR_CONTENT + " TEXT)");
		db.execSQL("CREATE TABLE " + TABLE_BIBLE + "(" + BIBLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				BIBLE_TESTAMENT + " TEXT, " + BIBLE_BOOK + " TEXT, " + BIBLE_CHAPTERS + " TEXT, " + 
				BIBLE_ENGLISH + " TEXT, " + BIBLE_ABBREVS + " TEXT, " + BIBLE_SWAHILI + " TEXT)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIBLE);
		this.onCreate(db);
	}

	public void createFavour(Favour favour) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FAVOUR_TITLE, favour.getTitle());
		values.put(FAVOUR_CONTENT, favour.getContent());
		db.insert(TABLE_FAVOURS, null, values);
		db.close();
	}

	public Favour readFavour(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_FAVOURS, // a. table
				COLUMNS_FAVOURITES, FAVOUR_ID +" = ?", new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Favour favour = new Favour();
		favour.setId(Integer.parseInt(cursor.getString(0)));
		favour.setTitle(cursor.getString(1));
		favour.setContent(cursor.getString(2));

		return favour;
	}

	public List<Favour> getAllFavours() {
		List<Favour> favourites = new LinkedList<Favour>();
		String query = "SELECT  * FROM " + TABLE_FAVOURS;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// parse all results
		Favour favour = null;
		if (cursor.moveToFirst()) {
			do {
				favour = new Favour();
				favour.setId(Integer.parseInt(cursor.getString(0)));
				favour.setTitle(cursor.getString(1));
				favour.setContent(cursor.getString(2));
				favourites.add(favour);
			} while (cursor.moveToNext());
		}
		return favourites;
	}

	public int updateFavour(Favour favour) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("title", favour.getTitle()); // get title
		values.put("content", favour.getContent()); // get content
		int i = db.update(TABLE_FAVOURS, values, FAVOUR_ID + " = ?", new String[] { String.valueOf(favour.getId()) });

		db.close();
		return i;
	}

	public void deleteFavour(Favour favour) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_FAVOURS, FAVOUR_ID + " = ?", new String[] { String.valueOf(favour.getId()) });
		db.close();
	}
	

	public void addtoHolyBible(HolyBible HolyBible) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(BIBLE_TESTAMENT, HolyBible.getTestament());
		values.put(BIBLE_BOOK, HolyBible.getBook());
		values.put(BIBLE_CHAPTERS, HolyBible.getChapters());
		values.put(BIBLE_ENGLISH, HolyBible.getEnglish());
		values.put(BIBLE_ABBREVS, HolyBible.getAbbrevs());
		values.put(BIBLE_SWAHILI, HolyBible.getSwahili());
		db.insert(TABLE_BIBLE, null, values);
		db.close();
	}

	public HolyBible readHolyBible(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_BIBLE, // a. table
				COLUMNS_BIBLE, BIBLE_ID +" = ?", new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		HolyBible HolyBible = new HolyBible();
		HolyBible.setId(Integer.parseInt(cursor.getString(0)));
		HolyBible.setTestament(cursor.getString(1));
		HolyBible.setBook(cursor.getString(2));
		HolyBible.setChapters(cursor.getString(3));
		HolyBible.setEnglish(cursor.getString(4));
		HolyBible.setAbbrevs(cursor.getString(5));
		HolyBible.setSwahili(cursor.getString(6));

		return HolyBible;
	}

	public List<HolyBible> getAllHolyBible() {
		List<HolyBible> TheHolyBible = new LinkedList<HolyBible>();
		String query = "SELECT  * FROM " + TABLE_BIBLE;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		HolyBible HolyBible = null;
		if (cursor.moveToFirst()) {
			do {
				HolyBible = new HolyBible();
				HolyBible.setId(Integer.parseInt(cursor.getString(0)));
				HolyBible.setTestament(cursor.getString(1));
				HolyBible.setBook(cursor.getString(2));
				HolyBible.setChapters(cursor.getString(3));
				HolyBible.setEnglish(cursor.getString(4));
				HolyBible.setAbbrevs(cursor.getString(5));
				HolyBible.setSwahili(cursor.getString(6));
				TheHolyBible.add(HolyBible);
			} while (cursor.moveToNext());
		}
		return TheHolyBible;
	}

	public List<HolyBible> getBibleTestament(String TESTAMENT) {
		List<HolyBible> TheHolyBible = new LinkedList<HolyBible>();
		String query = "SELECT  * FROM " + TABLE_BIBLE + " WHERE " + BIBLE_TESTAMENT + " ='" + TESTAMENT + "' ORDER BY " + BIBLE_ID + "";
		
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// parse all results
		HolyBible HolyBible = null;
		if (cursor.moveToFirst()) {
			do {
				HolyBible = new HolyBible();
				HolyBible.setId(Integer.parseInt(cursor.getString(0)));
				HolyBible.setTestament(cursor.getString(1));
				HolyBible.setBook(cursor.getString(2));
				HolyBible.setChapters(cursor.getString(3));
				HolyBible.setEnglish(cursor.getString(4));
				HolyBible.setAbbrevs(cursor.getString(5));
				HolyBible.setSwahili(cursor.getString(6));
				TheHolyBible.add(HolyBible);
			} while (cursor.moveToNext());
		}
		return TheHolyBible;
	}

}
