package accuratesoft.shakawat.rotadatacollection.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import accuratesoft.shakawat.rotadatacollection.utils.Util;

public class MyDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "rota";
    private static final int DATABASE_VERSION = 1;
    private static String administration = "administration";
    private static String screening = "screening";
    private static String enrolled = "enrolled";
    Context context;
    private static final String create_administration="CREATE TABLE " + administration
            + "( id INTEGER PRIMARY KEY ,"
            +"parent_id INTEGER, "
            +"name TEXT, "
            +"category INTEGER,"
            + " created_at DATETIME DEFAULT CURRENT_TIMESTAMP )";
    private static final String create_screening="CREATE TABLE " + screening
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            +"in_date TEXT, "
            +"hos_name TEXT, "
            +"hos_id TEXT, "
            +"dob TEXT, "
            +"admission TEXT, "
            +"sample TEXT, "
            +"screen_id TEXT, "
            +"dc_name TEXT, "
            +"health_reason_oth TEXT, "
            +"r_reason_oth TEXT, "
            +"name TEXT, "
            +"mobile TEXT, "
            +"address TEXT, "
            +"enrollment_id TEXT, "
            +"vac_info_oth TEXT, "
            +"vaccine_name INTEGER, "
            +"vaccine_doses INTEGER, "
            +"fdose TEXT, "
            +"sdose TEXT, "
            +"tdose TEXT, "
            +"less5 INTEGER, "
            +"is_gastro INTEGER, "
            +"parent_av INTEGER, "
            +"gastro7 INTEGER, "
            +"bf_symptom INTEGER, "
            +"healthy INTEGER, "
            +"evaluation INTEGER, "
            +"r_reason INTEGER, "
            +"sex INTEGER, "
            +"div INTEGER, "
            +"dis INTEGER, "
            +"upz INTEGER, "
            +"un INTEGER, "
            +"is_accept INTEGER, "
            +"is_vaccinated INTEGER, "
            +"vac_info INTEGER, "
            + " created_at DATETIME DEFAULT CURRENT_TIMESTAMP )";
    private static final String create_enrolled="CREATE TABLE " + enrolled
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            +"screen_id INTEGER, "
            +"enrollment_id TEXT, "
            +"hos_name TEXT, "
            +"hos_id TEXT, "
            +"in_date TEXT, "
            +"dia_onset TEXT, "
            +"abs_doc_name TEXT, "
            +"rel_oth TEXT, "
            +"dia_episode TEXT, "
            +"vomit_episode TEXT, "
            +"fever_temp TEXT, "
            +"rehy_type_oth TEXT, "
            +"probiotic_name TEXT, "
            +"probiotic_dose TEXT, "
            +"rel INTEGER, "
            +"dia INTEGER, "
            +"blood INTEGER, "
            +"vomit INTEGER, "
            +"fever INTEGER, "
            +"dehy INTEGER, "
            +"dehy_deg INTEGER, "
            +"rehy INTEGER, "
            +"rehy_type INTEGER, "
            +"probiotic INTEGER, "
            +"cond INTEGER, "
            +"heart INTEGER, "
            +"pulse INTEGER, "
            +"tear INTEGER, "
            +"tongue INTEGER, "
            +"skin INTEGER, "
            +"capillary INTEGER, "
            +"extrem INTEGER, "
            + " created_at DATETIME DEFAULT CURRENT_TIMESTAMP )";
    public MyDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_administration);
        sqLiteDatabase.execSQL(create_screening);
        sqLiteDatabase.execSQL(create_enrolled);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+administration);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+screening);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+enrolled);
        onCreate(sqLiteDatabase);
    }
    public void insert(ContentValues cv_administration){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(administration,null,cv_administration);
    }
    public void insertScreening(ContentValues cv_screening){
        SQLiteDatabase db = this.getWritableDatabase();
        if (Integer.parseInt(cv_screening.get("id").toString())==0) {
            cv_screening.remove("id");
            db.insert(screening, null, cv_screening);
            Util.makeToast(context,"Insert screening success");
        }else if (Integer.parseInt(cv_screening.get("id").toString())>0) {
            db.update(screening, cv_screening, "id=" + cv_screening.get("id"), null);
            Util.makeToast(context,"Update screening success");
        }
    }
    public void insertEnrolled(ContentValues cv_enrolled){
        SQLiteDatabase db = this.getWritableDatabase();
        if (Integer.parseInt(cv_enrolled.get("id").toString())==0) {
            cv_enrolled.remove("id");
            db.insert(enrolled, null, cv_enrolled);
            Util.makeToast(context,"Insert enrolled success");
        }else if (Integer.parseInt(cv_enrolled.get("id").toString())>0) {
            db.update(enrolled, cv_enrolled, "id=" + cv_enrolled.get("id"), null);
            Util.makeToast(context,"Update enrolled success");
        }
    }
    public Cursor getDiv(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + administration +" where category=1";
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public Cursor getDis(int div){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + administration +" where category=2 and parent_id= "+div;
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public Cursor getUpz(int dis){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + administration +" where category=3 and parent_id= "+dis;
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public Cursor getUn(int upz){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + administration +" where category=4 and parent_id= "+upz;
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public int getId(String name, int category){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT id FROM " + administration +" where name='"+name+"' and category= '"+category+"'";
//        Log.d("getId query",selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        int id=0;
        if (c.moveToFirst()){
            id = c.getInt(0);
        }
        return id;
    }
    public Cursor getScreening(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + screening ;
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public Cursor getEnrolled(int screen_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + enrolled +" Where screen_id="+ screen_id;
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public Cursor getEnrolled(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + enrolled;
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public void del_all(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ screening);
        db.execSQL("delete from "+ enrolled);
    }
}
