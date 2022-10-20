package vn.edu.poly.mob2041_duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import vn.edu.poly.mob2041_duanmau.DATABASE.MyDatabase;
import vn.edu.poly.mob2041_duanmau.DTO.BooksDTO;
import vn.edu.poly.mob2041_duanmau.DTO.MembersDTO;

public class BooksDAO {
    SQLiteDatabase sqLiteDatabase;
    MyDatabase myDatabase;

    public BooksDAO(Context context) {
        myDatabase =new MyDatabase(context);
        sqLiteDatabase = myDatabase.getWritableDatabase();
    }

    public void close(){
        myDatabase.close();
    }




    public long insertBooks(BooksDTO booksDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(BooksDTO.COL_NAME_BOOK, booksDTO.getNameBook());
        contentValues.put(BooksDTO.COL_PRICE, booksDTO.getPriceBook());
        contentValues.put(BooksDTO.COL_DISCOUNT, booksDTO.getDiscountBook());
        contentValues.put(BooksDTO.COL_ID_KIND_OF_BOOK, booksDTO.getIdKindOfBook());
        long res = sqLiteDatabase.insert(BooksDTO.TB_NAME, null, contentValues);
        return  res;
    }



    public int deleteBook(BooksDTO booksDTO){
        int res = sqLiteDatabase.delete(BooksDTO.TB_NAME,BooksDTO.COL_ID + " = ?", new String[]{booksDTO.getIdBook() +""});
        return  res;
    }


    public int updateBook(BooksDTO booksDTO){
        ContentValues contentValues= new ContentValues();
        contentValues.put(BooksDTO.COL_NAME_BOOK, booksDTO.getNameBook());
        contentValues.put(BooksDTO.COL_PRICE, booksDTO.getPriceBook());
        contentValues.put(BooksDTO.COL_DISCOUNT, booksDTO.getDiscountBook());
        contentValues.put(BooksDTO.COL_ID_KIND_OF_BOOK, booksDTO.getIdKindOfBook());

        int res = sqLiteDatabase.update( BooksDTO.TB_NAME, contentValues,BooksDTO.COL_ID + " = ?", new String[] { booksDTO.getIdBook() +"" } );
        return  res;
    }


    public ArrayList<BooksDTO> selectAllBook(){
        ArrayList<BooksDTO> lists = new ArrayList<>();
        String sqlSelect = "SELECT * FROM " + BooksDTO.TB_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(sqlSelect,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                BooksDTO booksDTO = new BooksDTO();
                booksDTO.setIdBook(cursor.getInt(0));
                booksDTO.setNameBook(cursor.getString(1));
                booksDTO.setPriceBook(cursor.getInt(2));
                booksDTO.setDiscountBook(cursor.getInt(3));
                booksDTO.setIdKindOfBook(cursor.getInt(4));
                lists.add(booksDTO);
                cursor.moveToNext();
            }
        }

        return lists;
    }


    public  BooksDTO selectNameKindOfBook(int id){
        BooksDTO booksDTO = new BooksDTO();
        String[] dk = new String[]{id + ""};
        String strSql = " SELECT  tbBooks.idKindOfBook, tbKindOfBook.titleBook" +
                " FROM tbBooks INNER JOIN tbKindOfBook " +
                "ON tbBooks.idKindOfBook = tbKindOfBook.idKindOfBook" +
                " WHERE idBook = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(strSql,dk);
        if(cursor.moveToFirst()){
            booksDTO.setIdKindOfBook(cursor.getInt(0));
            booksDTO.setNameKindOfBook(cursor.getString(1));

        }
        return  booksDTO;
    }

    public BooksDTO selectIdBook(String id){
        String[] dk = new String[]{id + ""};
        String selectSql = "SELECT * FROM "+BooksDTO.TB_NAME+" WHERE idBook = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(selectSql,dk);
        ArrayList<BooksDTO> lists = new ArrayList<>();
        if(cursor.moveToNext()){
            BooksDTO booksDTO = new BooksDTO();
            booksDTO.setIdBook(cursor.getInt(0));
            booksDTO.setNameBook(cursor.getString(1));
            booksDTO.setPriceBook(cursor.getInt(2));
            booksDTO.setDiscountBook(cursor.getInt(3));
            booksDTO.setIdKindOfBook(cursor.getInt(4));
            lists.add(booksDTO);

        }
        return lists.get(0);
    }

    public int selectCountBooks(){
        int count = 0;
        String selectSql = "SELECT COUNT (*)  FROM "+BooksDTO.TB_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(selectSql,null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        return count;
    }

    public int selectPriceBooks(){
        int price = 0;
        String selectSql = "SELECT priceBook FROM "+BooksDTO.TB_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(selectSql,null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            price = cursor.getInt(0);
        }

        return price;

    }


}
