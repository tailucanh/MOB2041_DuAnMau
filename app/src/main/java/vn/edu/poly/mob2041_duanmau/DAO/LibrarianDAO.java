package vn.edu.poly.mob2041_duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import vn.edu.poly.mob2041_duanmau.DATABASE.MyDatabase;
import vn.edu.poly.mob2041_duanmau.DTO.LibrariansDTO;
import vn.edu.poly.mob2041_duanmau.DTO.MembersDTO;

public class LibrarianDAO {
    SQLiteDatabase sqLiteDatabase;
    MyDatabase myDatabase;

    public LibrarianDAO(Context context) {
        myDatabase =new MyDatabase(context);
        sqLiteDatabase = myDatabase.getWritableDatabase();
    }

    public void close(){
        myDatabase.close();
    }

    public long insertLibrarian(LibrariansDTO librariansDTO){

        ContentValues contentValues = new ContentValues();
        contentValues.put(LibrariansDTO.COL_NAME_LIBRARIAN, librariansDTO.getNameLibrarian());
        contentValues.put(LibrariansDTO.COL_PHONE_NUMBER, librariansDTO.getPhoneNumbers());
        contentValues.put(LibrariansDTO.COL_USER_NAME, librariansDTO.getUserName());
        contentValues.put(LibrariansDTO.COL_PASS_WORD, librariansDTO.getPassword());
        long res = sqLiteDatabase.insert(LibrariansDTO.TB_NAME, null, contentValues);
        return  res;
    }




    public int deleteLibrarian(LibrariansDTO librariansDTO){
        int res = sqLiteDatabase.delete(LibrariansDTO.TB_NAME,LibrariansDTO.COL_ID + " = ?", new String[]{librariansDTO.getId() +""});
        return  res;
    }



    public int updateLibrarian(LibrariansDTO librariansDTO){
        ContentValues contentValues= new ContentValues();
        contentValues.put(LibrariansDTO.COL_NAME_LIBRARIAN, librariansDTO.getNameLibrarian());
        contentValues.put(LibrariansDTO.COL_PHONE_NUMBER, librariansDTO.getPhoneNumbers());
        contentValues.put(LibrariansDTO.COL_USER_NAME, librariansDTO.getUserName());
        contentValues.put(LibrariansDTO.COL_PASS_WORD, librariansDTO.getPassword());
       int res = sqLiteDatabase.update( LibrariansDTO.TB_NAME, contentValues,LibrariansDTO.COL_ID + " = ?", new String[] { librariansDTO.getId() +"" } );

        return  res;
    }


    public ArrayList<LibrariansDTO> selectAllLibrarian(){
        ArrayList<LibrariansDTO> lists = new ArrayList<>();
        String sqlSelect = "SELECT * FROM " + LibrariansDTO.TB_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(sqlSelect,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                LibrariansDTO librariansDTO = new LibrariansDTO();
                librariansDTO.setId(cursor.getInt(0));
                librariansDTO.setNameLibrarian(cursor.getString(1));
                librariansDTO.setPhoneNumbers(cursor.getString(2));
                librariansDTO.setUserName(cursor.getString(3));
                librariansDTO.setPassword(cursor.getString(4));
                lists.add(librariansDTO);
                cursor.moveToNext();
            }
        }

        return lists;
    }



    public int selectCountLibrarian(){
        int count = 0;
        String selectSql = "SELECT COUNT (*)  FROM "+LibrariansDTO.TB_NAME;
        Cursor cursorThu = sqLiteDatabase.rawQuery(selectSql,null);
        if (cursorThu.getCount() != 0){
            cursorThu.moveToFirst();
            count = cursorThu.getInt(0);
        }
        return count;
    }


}
