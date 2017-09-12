package bodor.com.todolistdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/9/12 0012.
 */

public class DbHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "DATA";
    private static final String TABLE_NAME = "THINGS";
    private static final int DB_VERSION = 1;
    private static final String TASK = "TASK";


    public DbHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL);",TABLE_NAME,TASK);
        sqLiteDatabase.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = String.format("DELETE TABLE IF EXISTS %s",TABLE_NAME);
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
    public void insertNewTask(String task){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK,task);
        db.insertWithOnConflict(TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }
    public void deleteTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,TASK + "= ?",new String[]{task});
        db.close();
    }
    public ArrayList<String> getAllTasks(){

        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{TASK},null,null,null,null,null);

        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(TASK);
          String task =   cursor.getString(index);
            taskList.add(task);
        }
        cursor.close();
        db.close();
        return taskList;
    }


}
