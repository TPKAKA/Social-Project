package com.example.adapters;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.R;
import com.example.db.DatabaseHandler;
import com.example.models.Post;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {
    private Context context;
    private List<Post> posts;
    private DatabaseHandler db;
    private SharedPreferences prefs;

    public PostAdapter(Context context, List<Post> posts) {
        super(context, 0, posts);
        this.context = context;
        this.posts = posts;
        this.db = new DatabaseHandler(context);
        this.prefs = context.getSharedPreferences("SocialAppPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
        }

        Post post = posts.get(position);
        int userId = prefs.getInt("userId", -1);

        TextView author = convertView.findViewById(R.id.author);
        TextView content = convertView.findViewById(R.id.content);
        TextView date = convertView.findViewById(R.id.date);
        TextView likeCount = convertView.findViewById(R.id.like_count);
        TextView comments = convertView.findViewById(R.id.comments);
        ImageView likeIcon = convertView.findViewById(R.id.like_icon);

        author.setText(post.getFirstName() + " " + post.getLastName());
        content.setText(post.getContent());
        date.setText(post.getDateTime());
        likeCount.setText(String.valueOf(post.getLikeCount()));
        comments.setText(db.getCommentsByPost(post.getId()).size() + " comments");

        boolean hasLiked = db.hasLiked(userId, post.getId());
        likeIcon.setImageResource(hasLiked ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);

        likeIcon.setOnClickListener(v -> {
            if (userId == -1) {
                return;
            }
            if (db.hasLiked(userId, post.getId())) {
                db.removeLike(userId, post.getId());
                post.setLikeCount(post.getLikeCount() - 1);
                likeIcon.setImageResource(android.R.drawable.btn_star_big_off);
            } else {
                db.addLike(userId, post.getId());
                post.setLikeCount(post.getLikeCount() + 1);
                likeIcon.setImageResource(android.R.drawable.btn_star_big_on);
            }
            likeCount.setText(String.valueOf(post.getLikeCount()));
        });

        return convertView;
    }
}