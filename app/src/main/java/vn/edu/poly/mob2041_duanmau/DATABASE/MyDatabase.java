package vn.edu.poly.mob2041_duanmau.DATABASE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {

    public static String DB_NAME = "BookManagements";
    public static int DB_VERSION = 1;

    public MyDatabase(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbKindOfBook = "CREATE TABLE  tbKindOfBook (idKindOfBook INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,titleBook TEXT NOT NULL );";
        db.execSQL(dbKindOfBook);
        String dbMember = "CREATE TABLE  tbMember (idMember INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,nameMember TEXT NOT NULL ,birthDate TEXT NOT NULL);";
        db.execSQL(dbMember);
        String dbBook = "CREATE TABLE  tbBooks (idBook INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,nameBook TEXT NOT NULL ,priceBook INTEGER NOT NULL,discountBook INTEGER NOT NULL, idKindOfBook INTEGER NOT NULL);";
        db.execSQL(dbBook);
        String dbLibrarian = "CREATE TABLE  tbLibrarian (idLibrarian INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , nameLibrarian TEXT NOT NULL ,phoneLibrarian TEXT NOT NULL,userLibrarian TEXT NOT NULL,passLibrarian TEXT NOT NULL);";
        db.execSQL(dbLibrarian);
        String dbLoanSlip = "CREATE TABLE  tbLoanSlip (idLoanSlip INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,idMember INTEGER NOT NULL ,idBook INTEGER NOT NULL,idLibrarian INTEGER NOT NULL, borrowedDate TEXT NOT NULL, rentCost INTEGER NOT NULL, borrowedState TEXT NOT NULL);";
        db.execSQL(dbLoanSlip);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
