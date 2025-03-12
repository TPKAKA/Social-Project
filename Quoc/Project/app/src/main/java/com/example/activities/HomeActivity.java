package com.example.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.demo.R;
import com.example.demo.adapters.PostAdapter;
import com.example.demo.db.DatabaseHandler;
import com.example.demo.models.Post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private EditText postEditText;
    private Button postButton;
    private ListView postsListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView modifyProfile, logout;
    private DatabaseHandler db;
    private SharedPreferences prefs;
    private List<Post> posts = new ArrayList<>();
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        postEditText = findViewById(R.id.post_edittext);
        postButton = findViewById(R.id.post);
        postsListView = findViewById(R.id.posts_list);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        modifyProfile = findViewById(R.id.modify);
        logout = findViewById(R.id.logout);
        db = new DatabaseHandler(this);
        prefs = getSharedPreferences("SocialAppPrefs", MODE_PRIVATE);

        postAdapter = new PostAdapter(this, posts);
        postsListView.setAdapter(postAdapter);

        postButton.setOnClickListener(v -> createPost());
        loadPosts();

        swipeRefreshLayout.setOnRefreshListener(this::loadPosts);

        postsListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(HomeActivity.this, CommentsActivity.class);
            intent.putExtra("postId", posts.get(position).getId());
            startActivity(intent);
        });

        modifyProfile.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ModifyProfileActivity.class)));
        logout.setOnClickListener(v -> logout());
    }

    private void createPost() {
        String content = postEditText.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "Post content cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = prefs.getInt("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        Post post = new Post(0, content, userId, dateTime, null, null);
        db.addPost(post);
        loadPosts();
        postEditText.setText("");
    }

    private void loadPosts() {
        swipeRefreshLayout.setRefreshing(true);
        posts.clear();
        posts.addAll(db.getAllPosts());
        postAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void logout() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPosts();
    }
}