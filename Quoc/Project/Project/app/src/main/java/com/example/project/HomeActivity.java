package com.example.project;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView modify = findViewById(R.id.modify);
        EditText post_edittext = findViewById(R.id.post_edittext);
        Button post = findViewById(R.id.post);
        ListView posts_list = findViewById(R.id.posts/list);

    }

    private class ListAdapter extends BaseAdapter {
        private final ArrayList<Post> post;

        public ListAdapter(ArrayList<Post> posts){
            this.posts = posts;
        }

        @Override
        public int getCount(){
            return posts.size();
        }

        @Override
        public Post getItem(int i){
            return posts.get(i);
        }

        @Override
        public long getItemId(int i){
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup){
            View v = getLayoutInflater().inflate(R.layout.post, null);

            TextView author = v.findViewById(R.id.author);
            TextView date = v.findViewById(R.id.date);
            TextView content = v.findViewById(R.id.content);
            ImageView like = v.findViewById(R.id.like);
            TextView likes = v.findViewById(R.id.likes);
            TextView comments = v.findViewById(R.id.comments);
            return v;
        }
    }
}