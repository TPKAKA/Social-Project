package com.example.project;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CommentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comments);

        TextView post_author = findViewById(R.id.comment_author);
        TextView post_date = findViewById(R.id.comment_date);
        TextView post_content = findViewById(R.id.comment_content);
        ImageView post_like = findViewById(R.id.like);
        TextView post_likes = findViewById(R.id.likes);
        TextView post_comments = findViewById(R.id.comments);

        EditText comment_edittext = findViewById(R.id.comment_edittext);
        Button post = findViewById(R.id.post);

        ListView comments_list = findViewById(R.id.comments_list);
    }

    private class ListAdapter extends BaseAdapter {
        private final ArrayList<Comment> comments;
        public ListAdapter(ArrayList<Comment> comments) {
            this.comments = comments;
        }
        @Override
        public int getCount() {
            return comments.size();
        }
        @Override
        public Comment getItem(int i) {
            return comments.get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = getLayoutInflater().inflate(R.layout.comment, null);

            TextView comment_author = v.findViewById(R.id.comment_author);
            TextView comment_content = v.findViewById(R.id.comment_content);
            TextView comment_date = v.findViewById(R.id.comment_date);
            return v;

        }

    }

}