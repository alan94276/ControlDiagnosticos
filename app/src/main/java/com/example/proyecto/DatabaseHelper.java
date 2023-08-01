package com.example.proyecto;

import static android.app.DownloadManager.COLUMN_ID;
import static com.example.proyecto.ShareActivity.COLUMN_EMAIL;
import static com.example.proyecto.ShareActivity.COLUMN_ISSUE;
import static com.example.proyecto.ShareActivity.COLUMN_MODEL;
import static com.example.proyecto.ShareActivity.COLUMN_NAME;
import static com.example.proyecto.ShareActivity.COLUMN_PHONE;
import static com.example.proyecto.ShareActivity.COLUMN_PHOTO_PATH;
import static com.example.proyecto.ShareActivity.DATABASE_NAME;
import static com.example.proyecto.ShareActivity.DATABASE_VERSION;
import static com.example.proyecto.ShareActivity.TABLE_NAME;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    DatabaseHelper(ShareActivity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_MODEL + " TEXT, " +
                COLUMN_ISSUE + " TEXT, " +
                COLUMN_PHOTO_PATH + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
