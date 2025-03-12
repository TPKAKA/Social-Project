package com.example.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.R;
import com.example.models.Comment;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment> {
    private Context context;
    private List<Comment> comments;

    public CommentAdapter(Context context, List<Comment> comments) {
        super(context, 0, comments);
        this.context = context;
        this.comments = comments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false);
        }

        Comment comment = comments.get(position);

        TextView author = convertView.findViewById(R.id.comment_author);
        TextView content = convertView.findViewById(R.id.comment_content);
        TextView date = convertView.findViewById(R.id.comment_date);

        author.setText(comment.getFirstName() + " " + comment.getLastName());
        content.setText(comment.getContent());
        date.setText(comment.getDateTime());

        return convertView;
    }
}