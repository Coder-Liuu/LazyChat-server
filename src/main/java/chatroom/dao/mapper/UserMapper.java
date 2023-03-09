package chatroom.dao.mapper;

import chatroom.dao.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User selectByUsername(String username);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User selectById(int id);

    @Insert("INSERT INTO users (username, password) VALUES (#{username}, #{password})")
    void insertUser(User user);
}
