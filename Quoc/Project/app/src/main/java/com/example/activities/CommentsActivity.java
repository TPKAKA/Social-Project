package com.example.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.demo.R;
import com.example.demo.adapters.CommentAdapter;
import com.example.demo.db.DatabaseHandler;
import com.example.demo.models.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentsActivity extends AppCompatActivity {
    private EditText commentEditText;
    private Button postButton;
    private ListView commentsListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DatabaseHandler db;
    private SharedPreferences prefs;
    private List<Comment> comments = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        commentEditText = findViewById(R.id.comment_edittext);
        postButton = findViewById(R.id.post);
        commentsListView = findViewById(R.id.comments_list);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        db = new DatabaseHandler(this);
        prefs = getSharedPreferences("SocialAppPrefs", MODE_PRIVATE);
        postId = getIntent().getIntExtra("postId", -1);

        commentAdapter = new CommentAdapter(this, comments);
        commentsListView.setAdapter(commentAdapter);

        postButton.setOnClickListener(v -> createComment());
        loadComments();

        swipeRefreshLayout.setOnRefreshListener(this::loadComments);
    }

    private void createComment() {
        String content = commentEditText.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = prefs.getInt("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        Comment comment = new Comment(0, content, postId, userId, dateTime, null, null);
        db.addComment(comment);
        loadComments();
        commentEditText.setText("");
    }

    private void loadComments() {
        swipeRefreshLayout.setRefreshing(true);
        comments.clear();
        comments.addAll(db.getCommentsByPost(postId));
        commentAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}