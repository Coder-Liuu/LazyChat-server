package chatroom.dao.entity;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserFriend {
    private Integer id;
    private Integer user_id;
    private Integer friend_id;
    private Timestamp added_at;
    private String status;
}
