package com.example.project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHandler";
    private final Context dbContext;
    private SQLiteDatabase db;

    private static final String dbName = "QLSV.SQLite";
    private static final String dbPath;
    private static final int dbVersion = 1;

    static {
        dbPath = "/data/data/com.example.qlsv/databases/"; // ✅ Đường dẫn thư mục
    }

    public DatabaseHandler(Context context) {
        super(context, dbName, null, dbVersion);
        this.dbContext = context;
        copyDatabaseIfNeeded();
    }

    private void copyDatabaseIfNeeded() {
        File dbFile = new File(dbPath + dbName);

        if (dbFile.exists()) {
            Log.d(TAG, "Database already exists. No need to copy.");
            return;
        }

        try {
            // ✅ Tạo thư mục nếu chưa tồn tại
            File dbDir = new File(dbPath);
            if (!dbDir.exists()) {
                dbDir.mkdirs();
            }

            // ✅ Đóng database nếu đang mở
            close();

            // ✅ Copy database từ assets
            InputStream in = dbContext.getAssets().open(dbName);
            OutputStream out = new FileOutputStream(dbFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.flush();
            out.close();

            Log.d(TAG, "Database copied successfully!");
            Toast.makeText(dbContext, "Database copied successfully!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e(TAG, "Error copying database", e);
            Toast.makeText(dbContext, "Error copying database: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public Cursor getCursor(String sql) {
        File dbFile = new File(dbPath + dbName);
        if (!dbFile.exists()) {
            Toast.makeText(dbContext, "Database not found!", Toast.LENGTH_LONG).show();
            return null;
        }

        try {
            db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
            return db.rawQuery(sql, null);
        } catch (Exception e) {
            Log.e(TAG, "Error executing query", e);
            return null;
        }
    }

    public void execSql(String sql) {
        File dbFile = new File(dbPath + dbName);
        if (!dbFile.exists()) {
            Toast.makeText(dbContext, "Database not found!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
            db.execSQL(sql);
        } catch (Exception e) {
            Log.e(TAG, "Error executing SQL", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS Students (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "studentId TEXT UNIQUE, " +
                "studentName TEXT, " +
                "email TEXT, " +
                "imageName TEXT, " +
                "major TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Có thể triển khai nâng cấp nếu cần
    }
}
