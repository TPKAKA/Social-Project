package com.example.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.models.Comment;
import com.example.models.FriendShip;
import com.example.models.Post;
import com.example.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PRM392";

    // Table names
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_POSTS = "Posts";
    private static final String TABLE_COMMENTS = "Comments";
    private static final String TABLE_USER_POSTS = "User_Posts";
    private static final String TABLE_FRIENDSHIPS = "Friendships";
    private static final String TABLE_LIKES = "Likes";

    // Common column names
    private static final String KEY_ID = "ID";

    // Users table columns
    private static final String KEY_FIRST_NAME = "FirstName";
    private static final String KEY_LAST_NAME = "LastName";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_PASSWORD = "Password";

    // Posts table columns
    private static final String KEY_CONTENT = "Content";
    private static final String KEY_USER = "User";
    private static final String KEY_DATETIME = "DateTime";

    // Comments table columns
    private static final String KEY_POST = "Post";

    // Friendships table columns
    private static final String KEY_FRIEND = "Friend";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FIRST_NAME + " TEXT,"
                + KEY_LAST_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_POSTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CONTENT + " TEXT,"
                + KEY_USER + " INTEGER,"
                + KEY_DATETIME + " TEXT,"
                + "FOREIGN KEY(" + KEY_USER + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "))";
        db.execSQL(CREATE_POSTS_TABLE);

        String CREATE_COMMENTS_TABLE = "CREATE TABLE " + TABLE_COMMENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CONTENT + " TEXT,"
                + KEY_POST + " INTEGER,"
                + KEY_USER + " INTEGER,"
                + KEY_DATETIME + " TEXT,"
                + "FOREIGN KEY(" + KEY_POST + ") REFERENCES " + TABLE_POSTS + "(" + KEY_ID + "),"
                + "FOREIGN KEY(" + KEY_USER + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "))";
        db.execSQL(CREATE_COMMENTS_TABLE);

        String CREATE_USER_POSTS_TABLE = "CREATE TABLE " + TABLE_USER_POSTS + "("
                + KEY_USER + " INTEGER,"
                + KEY_POST + " INTEGER,"
                + "PRIMARY KEY(" + KEY_USER + "," + KEY_POST + "),"
                + "FOREIGN KEY(" + KEY_USER + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "),"
                + "FOREIGN KEY(" + KEY_POST + ") REFERENCES " + TABLE_POSTS + "(" + KEY_ID + "))";
        db.execSQL(CREATE_USER_POSTS_TABLE);

        String CREATE_FRIENDSHIPS_TABLE = "CREATE TABLE " + TABLE_FRIENDSHIPS + "("
                + KEY_USER + " INTEGER,"
                + KEY_FRIEND + " INTEGER,"
                + "PRIMARY KEY(" + KEY_USER + "," + KEY_FRIEND + "),"
                + "FOREIGN KEY(" + KEY_USER + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "),"
                + "FOREIGN KEY(" + KEY_FRIEND + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "))";
        db.execSQL(CREATE_FRIENDSHIPS_TABLE);

        String CREATE_LIKES_TABLE = "CREATE TABLE " + TABLE_LIKES + "("
                + KEY_USER + " INTEGER,"
                + KEY_POST + " INTEGER,"
                + "PRIMARY KEY(" + KEY_USER + "," + KEY_POST + "),"
                + "FOREIGN KEY(" + KEY_USER + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "),"
                + "FOREIGN KEY(" + KEY_POST + ") REFERENCES " + TABLE_POSTS + "(" + KEY_ID + "))";
        db.execSQL(CREATE_LIKES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_POSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDSHIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIKES);
        onCreate(db);
    }

    // User CRUD
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, user.getFirstName());
        values.put(KEY_LAST_NAME, user.getLastName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, KEY_EMAIL + "=?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_PASSWORD))
            );
            cursor.close();
            return user;
        }
        return null;
    }

    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_PASSWORD))
            );
            cursor.close();
            return user;
        }
        return null;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, user.getFirstName());
        values.put(KEY_LAST_NAME, user.getLastName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        db.update(TABLE_USERS, values, KEY_ID + "=?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    // Post CRUD
    public void addPost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, post.getContent());
        values.put(KEY_USER, post.getUserId());
        values.put(KEY_DATETIME, post.getDateTime());
        long postId = db.insert(TABLE_POSTS, null, values);

        ContentValues userPostValues = new ContentValues();
        userPostValues.put(KEY_USER, post.getUserId());
        userPostValues.put(KEY_POST, postId);
        db.insert(TABLE_USER_POSTS, null, userPostValues);
        db.close();
    }

    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT p.*, u." + KEY_FIRST_NAME + ", u." + KEY_LAST_NAME + ", COUNT(l." + KEY_POST + ") as likeCount " +
                "FROM " + TABLE_POSTS + " p " +
                "JOIN " + TABLE_USERS + " u ON p." + KEY_USER + " = u." + KEY_ID + " " +
                "LEFT JOIN " + TABLE_LIKES + " l ON p." + KEY_ID + " = l." + KEY_POST + " " +
                "GROUP BY p." + KEY_ID + " ORDER BY p." + KEY_DATETIME + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Post post = new Post(
                        cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(KEY_USER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATETIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_LAST_NAME))
                );
                post.setLikeCount(cursor.getInt(cursor.getColumnIndexOrThrow("likeCount")));
                posts.add(post);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return posts;
    }

    // Comment CRUD
    public void addComment(Comment comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, comment.getContent());
        values.put(KEY_POST, comment.getPostId());
        values.put(KEY_USER, comment.getUserId());
        values.put(KEY_DATETIME, comment.getDateTime());
        db.insert(TABLE_COMMENTS, null, values);
        db.close();
    }

    public List<Comment> getCommentsByPost(int postId) {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT c.*, u." + KEY_FIRST_NAME + ", u." + KEY_LAST_NAME + " FROM " + TABLE_COMMENTS + " c " +
                "JOIN " + TABLE_USERS + " u ON c." + KEY_USER + " = u." + KEY_ID + " WHERE c." + KEY_POST + "=?" +
                " ORDER BY c." + KEY_DATETIME + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(postId)});
        if (cursor.moveToFirst()) {
            do {
                Comment comment = new Comment(
                        cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(KEY_POST)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(KEY_USER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATETIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_LAST_NAME))
                );
                comments.add(comment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return comments;
    }

    // Friendship CRUD
    public void addFriendship(FriendShip friendship) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER, friendship.getUserId());
        values.put(KEY_FRIEND, friendship.getFriendId());
        db.insertWithOnConflict(TABLE_FRIENDSHIPS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public void removeFriendship(FriendShip friendship) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FRIENDSHIPS, KEY_USER + "=? AND " + KEY_FRIEND + "=?",
                new String[]{String.valueOf(friendship.getUserId()), String.valueOf(friendship.getFriendId())});
        db.close();
    }

    public List<User> getFriends(int userId) {
        List<User> friends = new ArrayList<>();
        String query = "SELECT u.* FROM " + TABLE_USERS + " u JOIN " + TABLE_FRIENDSHIPS + " f ON u." + KEY_ID + " = f." + KEY_FRIEND +
                " WHERE f." + KEY_USER + "=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_LAST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),
                        null
                );
                friends.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return friends;
    }

    // Likes CRUD
    public void addLike(int userId, int postId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER, userId);
        values.put(KEY_POST, postId);
        db.insertWithOnConflict(TABLE_LIKES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public void removeLike(int userId, int postId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LIKES, KEY_USER + "=? AND " + KEY_POST + "=?",
                new String[]{String.valueOf(userId), String.valueOf(postId)});
        db.close();
    }

    public boolean hasLiked(int userId, int postId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LIKES, new String[]{KEY_USER},
                KEY_USER + "=? AND " + KEY_POST + "=?",
                new String[]{String.valueOf(userId), String.valueOf(postId)},
                null, null, null);
        boolean liked = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return liked;
    }

    public int getLikeCount(int postId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_LIKES + " WHERE " + KEY_POST + "=?",
                new String[]{String.valueOf(postId)});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }
}