package Model;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PostCommentAdapter extends BaseAdapter {
    private final ArrayList<Post> posts;

    private final ArrayList<Comment> comments;
    private Activity activity;

    public PostCommentAdapter(ArrayList<Post> posts, ArrayList<Comment> comments,

                              Activity activity) {
        this.posts = posts;
        this.comments = comments;
        this.activity = activity;

    }
    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override

    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = activity.getLayoutInflater().inflate(R.layout.post_comment, null);

        TextView post_author = v.findViewById(R.id.author);

        return v;

    }
}
