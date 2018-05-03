package com.example.admin.infraredcertification;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/06/01 0001.
 */

public class PadyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_MESSAGE="create table Picture("
            +"id integer primary key autoincrement,"
            +"time text,"
            +"isPicture int)";
    private Context mContext;

    public PadyDatabaseHelper(Context context, String title, SQLiteDatabase.CursorFactory factory, int version)
    {
        super (context ,title,factory,version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_MESSAGE);
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {}
}

