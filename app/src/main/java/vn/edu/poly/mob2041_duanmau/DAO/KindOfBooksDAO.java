package vn.edu.poly.mob2041_duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import vn.edu.poly.mob2041_duanmau.DATABASE.MyDatabase;
import vn.edu.poly.mob2041_duanmau.DTO.KindOfBooksDTO;

public class KindOfBooksDAO {
    SQLiteDatabase sqLiteDatabase;
    MyDatabase myDatabase;

    public KindOfBooksDAO(Context context) {
        myDatabase =new MyDatabase(context);
        sqLiteDatabase = myDatabase.getWritableDatabase();
    }

    public void close(){
        myDatabase.close();
    }

    public long insertKindOfBook(KindOfBooksDTO kindOfBooksDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KindOfBooksDTO.COL_TITLE_BOOK, kindOfBooksDTO.getTitleBook());
        long res = sqLiteDatabase.insert(KindOfBooksDTO.TB_NAME, null, contentValues);
        return  res;
    }



    public int deleteKindOfBook(KindOfBooksDTO kindOfBooksDTO){
        int res = sqLiteDatabase.delete(KindOfBooksDTO.TB_NAME,KindOfBooksDTO.COL_ID + " = ?", new String[]{kindOfBooksDTO.getIdKindOfBook() +""});
        return  res;
    }

    //Hàm update
    public int updateKindOfBook(KindOfBooksDTO kindOfBooksDTO){

        ContentValues values= new ContentValues();
        values.put( KindOfBooksDTO.COL_TITLE_BOOK,kindOfBooksDTO.getTitleBook() );
        int res = sqLiteDatabase.update( KindOfBooksDTO.TB_NAME, values,KindOfBooksDTO.COL_ID + " = ?", new String[] { kindOfBooksDTO.getIdKindOfBook() +"" } );
        return  res;
    }


    public ArrayList<KindOfBooksDTO> selectAllKindOfBook(){

        ArrayList<KindOfBooksDTO> lists = new ArrayList<>();
        String sqlSelect = "SELECT * FROM " + KindOfBooksDTO.TB_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(sqlSelect,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                KindOfBooksDTO kindOfBooksDTO = new KindOfBooksDTO();
                kindOfBooksDTO.setIdKindOfBook(cursor.getInt(0));
                kindOfBooksDTO.setTitleBook(cursor.getString(1));
                lists.add(kindOfBooksDTO);

                cursor.moveToNext();
            }
        }

        return lists;
    }

    public int selectCountKindOfBook(){
        int count = 0;
        String selectSql = "SELECT COUNT (*)  FROM "+KindOfBooksDTO.TB_NAME;
        Cursor cursorThu = sqLiteDatabase.rawQuery(selectSql,null);
        if (cursorThu.getCount() != 0){
            cursorThu.moveToFirst();
            count = cursorThu.getInt(0);
        }
        return count;
    }


}
