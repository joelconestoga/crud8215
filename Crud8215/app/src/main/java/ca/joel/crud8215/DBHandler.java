package ca.joel.crud8215;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

//SQL Lite Database Handler
public class DBHandler extends SQLiteOpenHelper {

    //Constants for File, Table and Columns
    private static final String TABLE_STUDENT = "STUDENT";
    public static final String DB_FILE = "STUDENTS.DB";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    private static final String COLUMN_LAST_NAME = "LAST_NAME";
    private static final String COLUMN_MARK = "MARK";

    //Constructor
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Creation script
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_STUDENT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_FIRST_NAME + " TEXT," +
                COLUMN_LAST_NAME + " TEXT," +
                COLUMN_MARK + " INTEGER);");
    }

    //Upgrade script
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("'DROP TABLE IF EXISTS " + TABLE_STUDENT + ";");
        onCreate(db);
    }

    //Method to save a new Student into database. Returns the Id auto-generated
    public int insert(Student student) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_FIRST_NAME, student.getFirstName());
        content.put(COLUMN_LAST_NAME, student.getLastName());
        content.put(COLUMN_MARK, student.getMark());
        return (int)getWritableDatabase().insertOrThrow(TABLE_STUDENT, "", content);
    }

    //Method to delete a Student
    public void delete(Student student) {
        getWritableDatabase().delete(TABLE_STUDENT,
                COLUMN_ID + "='" + student.getId() + "'",
                null);
    }

    //Method to update a Student
    public void update(Student student) {
        getWritableDatabase().execSQL("UPDATE " + TABLE_STUDENT + " SET " +
                COLUMN_FIRST_NAME + "='" + student.getFirstName() + "'," +
                COLUMN_LAST_NAME + "='" + student.getLastName() + "'," +
                                        COLUMN_MARK + "=" + student.getMark() + " " +
                                        "WHERE " + COLUMN_ID + "=" + student.getId());
    }

    //Method the retrieve all students from database
    public List<Student> getAll() {
        List<Student> all = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_STUDENT, null);
        while (cursor.moveToNext()) {
            all.add(new Student(cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getInt(3)));
        }
        cursor.close();
        return all;
    }
}
