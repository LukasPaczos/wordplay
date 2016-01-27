package paczos.com.mastermind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Main.db";
    public static final String TABLE_NAME = "stats_table";
    public static final String COL_1 = "DIFFICULTY";
    public static final String COL_2 = "WON";
    public static final String COL_3 = "LOST";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME + " (" + COL_1 + " TEXT PRIMARY KEY, " +
                COL_2 + " INTEGER, " + COL_3 + " INTEGER)");
        checkIfNew(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void checkIfNew(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.getCount() == 0) {
            fillNew(db);
        }
    }

    private void fillNew(SQLiteDatabase db) {
        insertData("EASY", 0, 0, db);
        insertData("MEDIUM", 0, 0, db);
        insertData("HARD", 0, 0, db);
        insertData("EXTREME", 0, 0, db);
    }

    private void insertData(String difficulty, int won, int lost, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, difficulty);
        contentValues.put(COL_2, won);
        contentValues.put(COL_3, lost);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    public void updateData(String difficulty, String result) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + result + " FROM " + TABLE_NAME +
                " WHERE difficulty = ?", new String[] { difficulty });

        cursor.moveToFirst();
        int temp = cursor.getInt(0);
        temp++;
        ContentValues contentValues = new ContentValues();
        contentValues.put(result, temp);
        db.update(TABLE_NAME, contentValues, COL_1 + " = ?", new String[] {difficulty});
    }

    public void reset() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, 0);
        contentValues.put(COL_3, 0);
        db.update(TABLE_NAME, contentValues, COL_1 + " = ?", new String[]{"EASY"});
        db.update(TABLE_NAME, contentValues, COL_1 + " = ?", new String[]{"MEDIUM"});
        db.update(TABLE_NAME, contentValues, COL_1 + " = ?", new String[]{"HARD"});
        db.update(TABLE_NAME, contentValues, COL_1 + " = ?", new String[]{"EXTREME"});
    }
}
