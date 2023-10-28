package com.seniorProject.project.mapper;

import com.seniorProject.project.model.Verification;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Mapper
public interface VerificationMapper {

    @Insert("insert into `verification` (id,verCode,expiredDate) values (#{id},#{verCode},#{expiredDate})")
    void insert(String id, String verCode, Date expiredDate);

    @Delete("delete from `verification` where id = #{id} and expiredDate < #{expiredDate}")
    void delete(String id, Date expiredDate);

    @Select("select * from `verification` where id = #{id}")
    Verification selectCode(String id);
}
