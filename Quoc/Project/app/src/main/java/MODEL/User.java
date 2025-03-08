package MODEL;

import java.util.ArrayList;

public class User {
    private int ID;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private ArrayList<Integer> linkIDs;
    private ArrayList<Integer> friendIDs;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Integer> getLinkIDs() {
        return linkIDs;
    }

    public void setLinkIDs(ArrayList<Integer> linkIDs) {
    }

    public ArrayList<Integer> getFriendIDs() {
        return friendIDs;
    }

    public void setFriendIDs(ArrayList<Integer> friendIDs) {
        this.friendIDs = friendIDs;
    }

    public boolean liked(Post post) {
        return linkIDs.contains(post.getID());
    }

    public void dislike(Post post) {
        linkIDs.remove(post.getID());
    }

    public void setFriends(ArrayList<User> friends) {
        friendIDs = new ArrayList<>();
        for (User friend : friends) {
            friendIDs.add(friend.getID());
        }
    }


    public void addFriend(User f) {
        friendIDs.add(f.getID());
    }

    public void removeFriend(User f) {
        friendIDs.remove(Integer.valueOf(f.getID()));
    }
}