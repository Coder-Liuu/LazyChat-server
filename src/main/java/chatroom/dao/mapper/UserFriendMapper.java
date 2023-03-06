package chatroom.dao.mapper;

import chatroom.dao.entity.User;
import chatroom.dao.entity.UserFriend;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserFriendMapper {
    @Insert("INSERT INTO user_friends (user_id, friend_id, status) VALUES (#{user_id}, #{friend_id}, #{status})")
    void insertStatus(@Param("user_id") int user_id, @Param("friend_id") int friend_id, @Param("status") String status);

    @Update("UPDATE user_friends SET status = 'friend' where user_id = #{user_id} and friend_id = #{friend_id}")
    void updatePending(@Param("user_id") int user_id, @Param("friend_id") int friend_id);

    @Select("<script>" +
        "SELECT * FROM user_friends WHERE 1 = 1" +
        "<if test='user_id != -1'> AND user_id = #{user_id}</if>" +
        "<if test='friend_id != -1'> AND friend_id = #{friend_id}</if>" +
        "AND status = #{status}" +
        "</script>")
    List<UserFriend> getFriends(@Param("user_id") int user_id,
                                @Param("friend_id") int friend_id,
                                @Param("status") String status);
}
