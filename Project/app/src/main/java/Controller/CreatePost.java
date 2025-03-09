package Controller;


import Model.Post;

import com.example.project.DatabaseHandler;
import android.content.Context;


public class CreatePost {
    private final Post post;
    private final DatabaseHandler databaseHandler;

    public CreatePost(Context context, Post post) {
        this.post = post;
        this.databaseHandler = new DatabaseHandler(context);
    }

    public boolean isPosted() {
        boolean isPosted = false;

        String insert = "INSERT INTO posts (Content, User, DateTime) VALUES ('"
                + post.getContent() + "', '"
                + post.getUser().getID()+ "', '"
                + post.getDateTimeToString() + "');";

        try {
            databaseHandler.execSql(insert);
            isPosted = true;
        } catch (Exception e) {
            e.printStackTrace();
            isPosted = false;
        }

        return isPosted;
    }
}
