package com.seniorProject.project.mapper;

import com.seniorProject.project.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.seniorProject.project.model.Entry;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EntryMapper {

    @Select("select * from `entries` where id = #{id}")
    List<Entry> selectEntry(String id);

    @Insert("insert into `entries` (id, entryDate, mood, activities, journal) values (#{id},#{entryDate},#{mood},#{activities},#{journal})")
    void insert(Entry entry);

    @Update("update `entries` set mood = #{mood}, activities = #{activities}, journal = #{journal} where id = #{id} and entryDate = #{entryDate}")
    void updateEntry(Entry entry);
}
