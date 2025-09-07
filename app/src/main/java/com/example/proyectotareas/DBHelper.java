package com.example.proyectotareas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.proyectotareas.caracters.AppLoger;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "login_demo.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT UNIQUE NOT NULL," +
                "password_hash TEXT NOT NULL," +
                        "salt TEXT NOT NULL," +
                        "created_at INTEGER NOT NULL" +
                        ")"
        );

        db.execSQL("CREATE UNIQUE INDEX idx_users_username ON users(username)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public long insertUser(String username, char[] password) throws Exception {
        byte[] salt = PasswordUtils.generateSalt();
        byte[] hash = PasswordUtils.hash(password, salt);

        ContentValues cv = new ContentValues();
        cv.put("username", username.trim());
        cv.put("password_hash", PasswordUtils.toBase64(hash));
        cv.put("salt", PasswordUtils.toBase64(salt));
        cv.put("created_at", System.currentTimeMillis());

        SQLiteDatabase db = getWritableDatabase();
        return db.insert("users", null, cv);

    }

    public boolean userExists(String username) {
        SQLiteDatabase db = getReadableDatabase();
        AppLoger.d("SQLiteHelper", "Verificando usuario en BD: " + username);
        try (Cursor c = db.rawQuery(
                "SELECT 1 FROM users WHERE username = ? LIMIT 1",
                new String[]{username.trim()})) {
            AppLoger.d("SQLiteHelper", "Usuario encontrado en BD: " + username);
            return c.moveToFirst();

        }
    }

    public boolean checklogin(String username, char[] password) throws Exception {
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor c = db.rawQuery(
                "SELECT password_hash, salt FROM users WHERE username = ? LIMIT 1",
                new String[]{username.trim()})) {
            if (!c.moveToFirst()) return false;
            String hashB64 = c.getString(0);
            String saltB64 = c.getString(1);
            byte[] expectedHash = PasswordUtils.fromBase64(hashB64);
            byte[] salt = PasswordUtils.fromBase64(saltB64);

            return PasswordUtils.verify(password, salt, expectedHash);
        }
    }
}
