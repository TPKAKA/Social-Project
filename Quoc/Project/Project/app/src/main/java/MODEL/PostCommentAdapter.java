package MODEL;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class PostCommentAdapter extends BaseAdapter {
    private final ArrayList<Post> posts;
    private final ArrayList<Comment> comments;
    private Activity activity;
    public PostCommentAdapter(ArrayList<Post> posts, ArrayList<Comment> comments, Activity activity) {
        this.posts posts;
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

        TextView post_author= v.findViewById(R.id.post_author);
        TextView post_date = v.findViewById(R.id.post_date);
        TextView post_content = v.findViewById(R.id.post_content);
        ImageView post_like v.findViewById(R.id.post_like);
        TextView post_likes v.findViewById(R.id.post_likes);
        TextView post_comments = v.findViewById(R.id.post_comments);

        TextView comment_author = v.findViewById(R.id.comment_author);
        TextView comment_content = v.findViewById(R.id.comment_content);
        TextView comment_date = v.findViewById(R.id.comment_date);

        return v;

    }
}
