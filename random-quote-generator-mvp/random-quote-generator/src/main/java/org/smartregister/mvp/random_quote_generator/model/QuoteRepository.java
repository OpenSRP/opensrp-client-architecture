
package org.smartregister.mvp.random_quote_generator.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

/**
 * Created by VonBass on 5/1/2018.
 */
public class QuoteRepository extends SQLiteOpenHelper {

    private int repositorySize = 0;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "quotes_db";
    private static final String TABLE_NAME = "quotes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUOTE = "quote";

    private List<String> quotes;


    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_QUOTE + " TEXT"
                    + ")";

    private static final String COUNT_TABLE_ENTRIES = "SELECT COUNT(*) FROM " + TABLE_NAME;



    public QuoteRepository(Context context, List<String> quotes) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.quotes = quotes;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        addQuotes(quotes, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addQuotes(List<String> quotes, SQLiteDatabase db) {

        ContentValues values = new ContentValues();

        for (String quote : quotes) {
            values.put(COLUMN_QUOTE, quote);
            db.insert(TABLE_NAME, null, values);
            Log.d("Adding quotes", "okay");
        }

        updateRepositorySize(db);
    }

    public String getQuote() {

        SQLiteDatabase db = getReadableDatabase();
        if (getRepositorySize() == 0) {
            addQuotes(quotes, db);
        }

        Log.d("repository size", "" + getRepositorySize());
        int id = (int) Math.floor(Math.random() * getRepositorySize() + 1);
        Log.d("column id", "" + id);
        Cursor result = db.rawQuery("SELECT " + COLUMN_QUOTE + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null);
        result.moveToFirst();

        String quote = result.getString(0);
        result.close();
        return quote;
    }

    public int getRepositorySize() {

        updateRepositorySize(getReadableDatabase());
        return repositorySize;
    }

    public void updateRepositorySize(SQLiteDatabase db) {

        Cursor result = db.rawQuery(COUNT_TABLE_ENTRIES, null);
        result.moveToFirst();

        repositorySize = result.getInt(0);
    }

    public void clearRepository(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
