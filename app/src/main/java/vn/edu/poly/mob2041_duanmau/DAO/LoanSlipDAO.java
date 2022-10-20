package vn.edu.poly.mob2041_duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import vn.edu.poly.mob2041_duanmau.DATABASE.MyDatabase;
import vn.edu.poly.mob2041_duanmau.DTO.BooksDTO;
import vn.edu.poly.mob2041_duanmau.DTO.LoanSlipDTO;
import vn.edu.poly.mob2041_duanmau.DTO.TopBooks;

public class LoanSlipDAO {
    SQLiteDatabase sqLiteDatabase;
    MyDatabase myDatabase;
    Context context;

    public LoanSlipDAO(Context context) {
        this.context = context;
        myDatabase = new MyDatabase(context);
        sqLiteDatabase = myDatabase.getWritableDatabase();
    }

    public void close(){
        myDatabase.close();
    }

    public long insertLoanSlip(LoanSlipDTO loanSlipDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(LoanSlipDTO.COL_ID_MEMBER, loanSlipDTO.getIdMember());
        contentValues.put(LoanSlipDTO.COL_ID_BOOK, loanSlipDTO.getIdBook());
        contentValues.put(LoanSlipDTO.COL_ID_LIBRARIAN, loanSlipDTO.getIdLibrarian());
        contentValues.put(LoanSlipDTO.COL_BORROWED_DATE, loanSlipDTO.getBorrowedDate());
        contentValues.put(LoanSlipDTO.COL_RENT_COST, loanSlipDTO.getRentCost());
        contentValues.put(LoanSlipDTO.COL_BORROWED_STATE, loanSlipDTO.getBorrowedState());
        long res = sqLiteDatabase.insert(LoanSlipDTO.TB_NAME, null, contentValues);
        return  res;
    }



    public int deleteLoanSlip(LoanSlipDTO loanSlipDTO){
        int res = sqLiteDatabase.delete(LoanSlipDTO.TB_NAME,LoanSlipDTO.COL_ID + " = ?", new String[]{loanSlipDTO.getIdLoanSlip() +""});
        return  res;
    }


    public int updateLoanSlip(LoanSlipDTO loanSlipDTO){
        ContentValues contentValues= new ContentValues();
        contentValues.put(LoanSlipDTO.COL_ID_MEMBER, loanSlipDTO.getIdMember());
        contentValues.put(LoanSlipDTO.COL_ID_BOOK, loanSlipDTO.getIdBook());
        contentValues.put(LoanSlipDTO.COL_ID_LIBRARIAN, loanSlipDTO.getIdLibrarian());
        contentValues.put(LoanSlipDTO.COL_BORROWED_DATE, loanSlipDTO.getBorrowedDate());
        contentValues.put(LoanSlipDTO.COL_RENT_COST, loanSlipDTO.getRentCost());
        contentValues.put(LoanSlipDTO.COL_BORROWED_STATE, loanSlipDTO.getBorrowedState());

        int res = sqLiteDatabase.update( LoanSlipDTO.TB_NAME, contentValues,LoanSlipDTO.COL_ID + " = ?", new String[] { loanSlipDTO.getIdLoanSlip() +"" } );
        return  res;
    }


    public ArrayList<LoanSlipDTO> selectAllLoanSlip(){
        ArrayList<LoanSlipDTO> lists = new ArrayList<>();
        String sqlSelect = "SELECT * FROM " + LoanSlipDTO.TB_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(sqlSelect,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                LoanSlipDTO loanSlipDTO= new LoanSlipDTO();
                loanSlipDTO.setIdLoanSlip(cursor.getInt(0));
                loanSlipDTO.setIdMember(cursor.getInt(1));
                loanSlipDTO.setIdBook(cursor.getInt(2));
                loanSlipDTO.setIdLibrarian(cursor.getInt(3));
                loanSlipDTO.setBorrowedDate(cursor.getString(4));
                loanSlipDTO.setRentCost(cursor.getInt(5));
                loanSlipDTO.setBorrowedState(cursor.getString(6));

                lists.add(loanSlipDTO);
                cursor.moveToNext();
            }
        }

        return lists;
    }


    public LoanSlipDTO selectNameMember(int id){
        LoanSlipDTO loanSlipDTO= new LoanSlipDTO();
        String[] dk = new String[]{id + ""};
        String strSql = " SELECT  tbLoanSlip.idMember, tbMember.nameMember" + " FROM tbLoanSlip INNER JOIN tbMember " +
                "ON tbLoanSlip.idMember = tbMember.idMember" +
                " WHERE idLoanSlip = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(strSql,dk);
        if(cursor.moveToFirst()){
            loanSlipDTO.setIdMember(cursor.getInt(0));
            loanSlipDTO.setNameMember(cursor.getString(1));
        }

        return  loanSlipDTO;
    }


    public LoanSlipDTO selectNameBook(int id){
        LoanSlipDTO loanSlipDTO= new LoanSlipDTO();
        String[] dk = new String[]{id + ""};
        String strSql = " SELECT  tbLoanSlip.idBook, tbBooks.nameBook , tbBooks.priceBook" + " FROM tbLoanSlip INNER JOIN tbBooks " +
                "ON tbLoanSlip.idBook = tbBooks.idBook" +
                " WHERE idLoanSlip = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(strSql,dk);
        if(cursor.moveToFirst()){
            loanSlipDTO.setIdBook(cursor.getInt(0));
            loanSlipDTO.setNameBook(cursor.getString(1));
            loanSlipDTO.setRentCost(cursor.getInt(2));
        }
        return  loanSlipDTO;
    }

    public ArrayList<TopBooks> selectListTopBooks(){
        ArrayList<TopBooks> lists = new ArrayList<>();
        String sqlSelect = "SELECT idBook, COUNT(idBook) AS countBook FROM tbLoanSlip GROUP BY idBook ORDER BY countBook DESC LIMIT 10";
        Cursor cursor = sqLiteDatabase.rawQuery(sqlSelect,null);
        BooksDAO booksDAO = new BooksDAO(context);
        while (cursor.moveToNext()){
            TopBooks topBooks = new TopBooks();
            BooksDTO booksDTO = booksDAO.selectIdBook(cursor.getString(0));
            topBooks.setTitleBook(booksDTO.getNameBook());
            topBooks.setCountBook(cursor.getInt(1));
            lists.add(topBooks);

        }

        return lists;

    }




    public long selectTotalMoney2022(){
        long sum = 0;
        String strSql = "SELECT SUM(rentCost) as"+"TongTien"+" FROM tbLoanSlip WHERE borrowedDate >= '2022-01-1'";
        Cursor cursor = sqLiteDatabase.rawQuery(strSql,null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            sum = cursor.getLong(0);
        }

        return  sum;
    }


    public long selectTotalMoneyTheFirstQuarter(){
        long sum1 = 0;
        String strSql = "SELECT SUM (rentCost)  FROM tbLoanSlip WHERE borrowedDate BETWEEN '2022-01-1' AND '2022-03-31' ";

        Cursor cursor = sqLiteDatabase.rawQuery(strSql,null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            sum1 = cursor.getInt(0);
        }
        return  sum1;
    }


    public long selectTotalMoneyTheSecondQuarter(){
        long sum2 = 0;
        String strSql = "SELECT SUM (rentCost) FROM tbLoanSlip WHERE borrowedDate BETWEEN  '2022-04-1' AND '2022-06-30'";

        Cursor cursor = sqLiteDatabase.rawQuery(strSql,null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            sum2 = cursor.getInt(0);
        }

        return  sum2;
    }
    public long selectTotalMoneyTheThirdQuarter(){
        long sum3 = 0;
        String strSql = "SELECT SUM (rentCost) FROM tbLoanSlip WHERE borrowedDate BETWEEN '2022-07-1' AND '2022-09-30'";

        Cursor cursor = sqLiteDatabase.rawQuery(strSql,null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            sum3 = cursor.getInt(0);
        }

        return  sum3;
    }
    public long selectTotalMoneyTheFourthQuarter(){
        long sum4 = 0;
        String strSql = "SELECT SUM (rentCost) FROM tbLoanSlip WHERE borrowedDate BETWEEN '2022-10-01' AND '2022-12-31'";

        Cursor cursor = sqLiteDatabase.rawQuery(strSql,null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            sum4 = cursor.getInt(0);
        }

        return  sum4;
    }



    public LoanSlipDTO selectNameLibrarian(int id){
        LoanSlipDTO loanSlipDTO= new LoanSlipDTO();
        String[] dk = new String[]{id + ""};
        String strSql = " SELECT  tbLoanSlip.idLibrarian, tbLibrarian.nameLibrarian" + " FROM tbLoanSlip INNER JOIN tbLibrarian " +
                "ON tbLoanSlip.idLibrarian = tbLibrarian.idLibrarian" +
                " WHERE idLoanSlip = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(strSql,dk);
        if(cursor.moveToFirst()){
            loanSlipDTO.setIdLibrarian(cursor.getInt(0));
            loanSlipDTO.setNameLibrarian(cursor.getString(1));
        }
        return  loanSlipDTO;
    }


    public int selectCountLoanSlip(){
        int count = 0;
        String selectSql = "SELECT COUNT (*)  FROM "+LoanSlipDTO.TB_NAME;
        Cursor cursorThu = sqLiteDatabase.rawQuery(selectSql,null);
        if (cursorThu.getCount() != 0){
            cursorThu.moveToFirst();
            count = cursorThu.getInt(0);
        }
        return count;
    }


}
