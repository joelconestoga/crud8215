package ca.joel.crud8215;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static ca.joel.crud8215.MainActivity.*;

public class DBHandler extends SQLiteOpenHelper {

    private static final String TABLE_STUDENT = "STUDENT";
    public static final String DB_FILE = TABLE_STUDENT + "S.DB";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_FIRSTNAME = "FIRST_NAME";
    private static final String COLUMN_LASTNAME = "LAST_NAME";
    private static final String COLUMN_MARK = "MARK";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_STUDENT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_FIRSTNAME + " TEXT," +
                COLUMN_LASTNAME + " TEXT," +
                COLUMN_MARK + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("'DROP TABLE IF EXISTS " + TABLE_STUDENT + ";");
        onCreate(db);
    }

    public int insert(Student student) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_FIRSTNAME, student.getFirstName());
        content.put(COLUMN_LASTNAME, student.getLastName());
        content.put(COLUMN_MARK, student.getMark());
        return (int)getWritableDatabase().insertOrThrow(TABLE_STUDENT, "", content);
    }

    public void delete(Student student) {
        getWritableDatabase().delete(TABLE_STUDENT,
                COLUMN_ID + "='" + student.getId() + "'",
                null);
    }

    public void update(Student student) {
        getWritableDatabase().execSQL("UPDATE " + TABLE_STUDENT + " SET " +
                                        COLUMN_FIRSTNAME + "='" + student.getFirstName() + "'," +
                                        COLUMN_LASTNAME + "='" + student.getLastName() + "'," +
                                        COLUMN_MARK + "=" + student.getMark() + " " +
                                        "WHERE " + COLUMN_ID + "=" + student.getId());
    }

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
