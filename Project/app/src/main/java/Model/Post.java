package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post {
    private int ID;
    private String content;
    private User user;
    private LocalDateTime time;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", java.util.Locale.ENGLISH);
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", java.util.Locale.ENGLISH);

    public Post() {}

    public Post(String content, User user) {
        this.content = content;
        this.user = user;
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


    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return time;
    }

    public void setDateTime(LocalDateTime time) {
        this.time = time;
    }

    // ✅ Sửa lỗi getDateTimeToString
    public String getDateTimeToString() {
        return formatter.format(time);
    }

    public void setDateTimeFromString(String time) {
        this.time = LocalDateTime.parse(time, formatter);
    }
}
