package MODEL;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.socialmedia.R;

import java.util.ArrayList;

public class CommentsAdapter extends BaseAdapter {
    private final ArrayList<Comment> comments;
    private Activity activity;
    public CommentsAdapter(ArrayList<Comment> comments, Activity activity) {
        this.comments = comments;
        this.activity = activity:

    }
    @Override
    public int getCount() { return comments.size(); }
    @Override
    public Comment getItem(int i) { return comments.get(i); }
    @Override
    public long getItemId(int i) { return 1; }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = activity.getLayoutInflater().inflate(R.layout.comment,  null);

        TextView comment_author = v.findViewById(R.id.comment_author);
        TextView comment_content = v.findViewById(R.id.comment_content);
        TextView comment_date = v.findViewById(R.id.comment_date);
        return v;
    }

}