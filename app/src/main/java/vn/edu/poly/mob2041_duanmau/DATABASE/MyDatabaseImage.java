package vn.edu.poly.mob2041_duanmau.DATABASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class MyDatabaseImage extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "image";
    private static final String TABLE_NAME = "store";
    private static final String COLUMN_IMAGE = "img";
    private static final String COLUMN_ID = "id";
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_IMAGE + " BLOB NOT NULL " + ")";

    public MyDatabaseImage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_IMAGE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertImageBitmap(byte[] img) {
        SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_IMAGE,img);
        long res = db.insert(TABLE_NAME,null,contentValues);
        if(res == -1){
            return  false;
        }
        return true;
    }

    public Bitmap getImg(){
        SQLiteDatabase db = this.getWritableDatabase();
        byte[] bitmap = new byte[0];
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE id = ?",null);
        if( cursor != null && cursor.moveToFirst() ){
            bitmap = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));
        }


        Bitmap img = BitmapFactory.decodeByteArray(bitmap,0,bitmap.length);

        return img;
    }


}
