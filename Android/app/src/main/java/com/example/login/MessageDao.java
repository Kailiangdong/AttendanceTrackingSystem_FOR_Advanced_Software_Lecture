package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageDao {
    private SQLiteDatabase db;
    public MessageDao(Context context){
        MyOpenHelper MyOpenHelper = new MyOpenHelper(context);
        db = MyOpenHelper.getReadableDatabase();
    }

    public void insert(JSONArray attendance_log){
        JSONArray attendance= attendance_log;
        for (int i = 0; i<attendance.length(); i++){
            JSONObject message = null;
            try {
                message = attendance.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Long student_id = message.optLong("student_id");
            Integer group_num = message.optInt("group");
            Integer week_num = message.optInt("week");

            ContentValues ContentValues = new ContentValues();
            ContentValues.put("student_id", student_id);
            ContentValues.put("group_num", group_num);
            ContentValues.put("week_num", week_num);
            db.insert("Attendance", null, ContentValues);
        }
    }
}

class MyOpenHelper extends SQLiteOpenHelper {
    public MyOpenHelper(Context context) {
        super(context, "Message.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("TAG","onCreate is executed");
        String sql = "Create TABLE Attendance(Num integer primary key autoincrement, student_id bigint, group_num integer,week_num integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("TAG","onUpgrade is executed");
    }
}
