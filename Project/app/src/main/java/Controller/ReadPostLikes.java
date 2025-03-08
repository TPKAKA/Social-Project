package Controller;
import java.sql.ResultSet;
import java.sql.SQLException;

import MODEL.Database;

import MODEL.Post;

public class ReadPostLikes {


    private int likes;

    public ReadPostLikes(Post p, Database database) {

        likes = 0;

        String select = "SELECT * FROM 'likes' WHERE `Post = " + p.getID() + ";";

        try {

            ResultSet rs = database.getStatement().executeQuery(select);

            while (rs.next()) {

                likes++;

            }
        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public int getLikesCount() {

        return likes;

    }

}

