package vn.edu.poly.mob2041_duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.edu.poly.mob2041_duanmau.DATABASE.MyDatabase;
import vn.edu.poly.mob2041_duanmau.DTO.KindOfBooksDTO;
import vn.edu.poly.mob2041_duanmau.DTO.MembersDTO;

public class MemberDAO {
    SQLiteDatabase sqLiteDatabase;
    MyDatabase myDatabase;

    public MemberDAO(Context context) {
        myDatabase =new MyDatabase(context);
        sqLiteDatabase = myDatabase.getWritableDatabase();
    }

    public void close(){
        myDatabase.close();
    }

    public long insertMember(MembersDTO membersDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MembersDTO.COL_NAME_MEMBER, membersDTO.getNameMember());
        contentValues.put(MembersDTO.COL_BIRTH_DATE, membersDTO.getBirthDate());
        long res = sqLiteDatabase.insert(MembersDTO.TB_NAME, null, contentValues);
        return  res;
    }



    public int deleteMember(MembersDTO membersDTO){
        int res = sqLiteDatabase.delete(MembersDTO.TB_NAME,MembersDTO.COL_ID + " = ?", new String[]{membersDTO.getIdMember() +""});
        return  res;
    }


    public int updateMember(MembersDTO membersDTO){
        ContentValues values= new ContentValues();
        values.put( MembersDTO.COL_NAME_MEMBER,membersDTO.getNameMember() );
        values.put( MembersDTO.COL_BIRTH_DATE,membersDTO.getBirthDate() );
        int res = sqLiteDatabase.update( MembersDTO.TB_NAME, values,MembersDTO.COL_ID + " = ?", new String[] { membersDTO.getIdMember() +"" } );
        return  res;
    }


    public ArrayList<MembersDTO> selectAllMember(){
        ArrayList<MembersDTO> lists = new ArrayList<>();
        String sqlSelect = "SELECT * FROM " + MembersDTO.TB_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(sqlSelect,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                MembersDTO membersDTO = new MembersDTO();
                membersDTO.setIdMember(cursor.getInt(0));
                membersDTO.setNameMember(cursor.getString(1));
                membersDTO.setBirthDate(cursor.getString(2));
                lists.add(membersDTO);
                cursor.moveToNext();
            }
        }

        return lists;
    }

    public int selectCountMember(){
        int count = 0;
        String selectSql = "SELECT COUNT (*)  FROM "+MembersDTO.TB_NAME;
        Cursor cursorThu = sqLiteDatabase.rawQuery(selectSql,null);
        if (cursorThu.getCount() != 0){
            cursorThu.moveToFirst();
            count = cursorThu.getInt(0);
        }
        return count;
    }


}
