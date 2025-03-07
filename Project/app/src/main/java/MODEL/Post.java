package MODEL;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post {
    private int ID;
    private String content;
    private User use;
    private LocalDateTime time;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", java.util.Locale.ENGLISH);
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", java.util.Locale.ENGLISH);

    public Post(){}
    public Post(String content, User use){
        this.content = content;
        this.use = use;
        this.time = LocalDateTime.now();
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public User getUser(User user) {
        return user;
    }
    public void setUser(User user) {
        this.use = user;
    }
    public LocalDateTime getDateTime() {
        return time;
    }
    public void setDateTime(LocalDateTime time) {
        this.time = time;
    }
    public String getDateTimeToString() {
        return dateFormatter.format(time);
    }
    public void setDateTimeFromString(String time) {
        this.time = LocalDateTime.parse(time, dateFormatter);
    }

}
