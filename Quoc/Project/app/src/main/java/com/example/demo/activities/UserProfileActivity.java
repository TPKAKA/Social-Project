package com.example.demo.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;
import com.example.demo.db.DatabaseHandler;

import com.example.demo.models.FriendShip;
import com.example.demo.models.User;

public class UserProfileActivity extends AppCompatActivity {
    private TextView userNameTextView;
    private Button followButton, unfollowButton;
    private DatabaseHandler db;
    private SharedPreferences prefs;
    private int friendId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userNameTextView = findViewById(R.id.user_name);
        followButton = findViewById(R.id.follow);
        unfollowButton = findViewById(R.id.unfollow);
        db = new DatabaseHandler(this);
        prefs = getSharedPreferences("SocialAppPrefs", MODE_PRIVATE);

        friendId = getIntent().getIntExtra("friendId", -1);
        User friend = db.getUserById(friendId);
        if (friend != null) {
            userNameTextView.setText(friend.getFirstName() + " " + friend.getLastName());
        }

        int userId = prefs.getInt("userId", -1);
        boolean isFollowing = db.getFriends(userId).stream().anyMatch(f -> f.getId() == friendId);
        followButton.setVisibility(isFollowing ? View.GONE : View.VISIBLE);
        unfollowButton.setVisibility(isFollowing ? View.VISIBLE : View.GONE);

        followButton.setOnClickListener(v -> follow());
        unfollowButton.setOnClickListener(v -> unfollow());
    }

    private void follow() {
        int userId = prefs.getInt("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        FriendShip friendship = new FriendShip(userId, friendId);
        db.addFriendship(friendship);
        Toast.makeText(this, "Followed successfully", Toast.LENGTH_SHORT).show();
        followButton.setVisibility(View.GONE);
        unfollowButton.setVisibility(View.VISIBLE);
    }

    private void unfollow() {
        int userId = prefs.getInt("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        FriendShip friendship = new FriendShip(userId, friendId);
        db.removeFriendship(friendship);
        Toast.makeText(this, "Unfollowed successfully", Toast.LENGTH_SHORT).show();
        followButton.setVisibility(View.VISIBLE);
        unfollowButton.setVisibility(View.GONE);
    }
}