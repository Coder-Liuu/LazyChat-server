package chatroom.dao.entity;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Timestamp createdAt;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
