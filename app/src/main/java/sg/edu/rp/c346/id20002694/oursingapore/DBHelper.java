package sg.edu.rp.c346.id20002694.oursingapore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "songs.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGERS = "singers";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_SONG + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_SINGERS + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_STARS + " INTEGER ) ";

        db.execSQL(createSongTableSql);
        Log.i("info", "created tables");

        //Dummy records, to be inserted when the database is created

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, "Coney Island");
        values.put(COLUMN_SINGERS, "Planning area: Punggol");
        values.put(COLUMN_YEAR, 1);
        values.put(COLUMN_STARS, 1);
        db.insert(TABLE_SONG, null, values);

        ContentValues values2 = new ContentValues();
        values2.put(COLUMN_TITLE, "Sentosa Island");
        values2.put(COLUMN_SINGERS, "Probably one of the best, if not the best island");
        values2.put(COLUMN_YEAR, 4);
        values2.put(COLUMN_STARS,  5);
        db.insert(TABLE_SONG, null, values2);

        ContentValues values3 = new ContentValues();
        values3.put(COLUMN_TITLE, "Pulau Ubin");
        values3.put(COLUMN_SINGERS, "Did you know that Pulau Ubin vehicles have a unique island plate?");
        values3.put(COLUMN_YEAR, 10);
        values3.put(COLUMN_STARS,  4);
        db.insert(TABLE_SONG, null, values3);

        ContentValues values4 = new ContentValues();
        values4.put(COLUMN_TITLE, "Pulau Tekong");
        values4.put(COLUMN_SINGERS, "There is absolutely nothing on this island, go away");
        values4.put(COLUMN_YEAR, 24);
        values4.put(COLUMN_STARS,  3);
        db.insert(TABLE_SONG, null, values4);

        Log.i("info", "dummy records inserted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        db.execSQL("ALTER TABLE " + TABLE_SONG + " ADD COLUMN  module_name TEXT ");
        //onCreate(db);
    }

    public long insertSong(String title, String singers, int year, int stars  ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SINGERS, singers);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_STARS,  stars);

        long result = db.insert(TABLE_SONG, null, values);
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<sg.edu.rp.c346.id20002694.oursingapore.Song> getAllSongs() {
        ArrayList<sg.edu.rp.c346.id20002694.oursingapore.Song> songs = new ArrayList<sg.edu.rp.c346.id20002694.oursingapore.Song>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_TITLE + ","
                + COLUMN_SINGERS + ","
                + COLUMN_YEAR + ","
                + COLUMN_STARS + " FROM " + TABLE_SONG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id= cursor.getInt(0);
                int stars = cursor.getInt(4);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);

                sg.edu.rp.c346.id20002694.oursingapore.Song song = new sg.edu.rp.c346.id20002694.oursingapore.Song(id , title, singers,  year, stars);

                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public int updateSong(sg.edu.rp.c346.id20002694.oursingapore.Song data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_SINGERS, data.getSingers());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_STARS,  data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_SONG, values, condition, args);
        db.close();
        return result;
    }

    public int deleteSong(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONG, condition, args);
        db.close();
        return result;
    }

    public ArrayList<sg.edu.rp.c346.id20002694.oursingapore.Song> getAllSongs(String keyword) {
        ArrayList<sg.edu.rp.c346.id20002694.oursingapore.Song> songs = new ArrayList<sg.edu.rp.c346.id20002694.oursingapore.Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID,COLUMN_TITLE,COLUMN_SINGERS,COLUMN_YEAR, COLUMN_STARS};
        String condition = COLUMN_STARS + " = ?";
        String[] args = {   keyword };
        Cursor cursor = db.query(TABLE_SONG, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int stars = cursor.getInt(4);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);

                sg.edu.rp.c346.id20002694.oursingapore.Song song = new sg.edu.rp.c346.id20002694.oursingapore.Song(id, title, singers,  year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<sg.edu.rp.c346.id20002694.oursingapore.Song> getAllSongsByStars() {
        ArrayList<sg.edu.rp.c346.id20002694.oursingapore.Song> songs = new ArrayList<sg.edu.rp.c346.id20002694.oursingapore.Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID,COLUMN_TITLE,COLUMN_SINGERS,COLUMN_YEAR, COLUMN_STARS};
        String condition = COLUMN_STARS + " = 5";

        Cursor cursor = db.query(TABLE_SONG, columns, condition,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int stars = cursor.getInt(4);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);

                sg.edu.rp.c346.id20002694.oursingapore.Song song = new sg.edu.rp.c346.id20002694.oursingapore.Song(id, title, singers,  year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }


}
