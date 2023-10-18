package com.seniorProject.project.mapper;
import com.seniorProject.project.enums.MessageType;
import com.seniorProject.project.model.User;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into `user` (email,password) values (#{email},#{password})")
    void insert(User user);


    @Update("update `user` set password = #{password} where email = #{email}")
    void updatePassword(User user);

    @Delete("delete from `user` where email = #{email}")
    void delete(String email);

    @Select("select password from `user` where email = #{email}")
    String selectPassword(String email);


    @Select("select * from `user` where email = #{email}")
    User selectUser(String email);
}
