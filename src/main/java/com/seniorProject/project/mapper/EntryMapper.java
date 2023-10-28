package com.seniorProject.project.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.seniorProject.project.model.Entry;

import java.util.List;

@Mapper
public interface EntryMapper {

    @Select("select * from entries where id = #{id} and entrydate = #{date}")
    Entry selectEntry(String id, String date);

    @Select("select * from entries where id = #{id}")
    List<Entry> selectEntries(String id);

    @Delete("delete from entries where id = #{id} and entrydate = #{date}")
    void deleteEntry(String id, String date);

    @Insert("insert into `entries` (id, entryDate, mood, activities, journal) values (#{id},#{entryDate},#{mood},#{activities},#{journal})")
    void insertEntry(Entry entry);

    @Update("update entries set (#{mood}, #{activities}, #{journal}) where id = #{id} and entrydate = #{date}")
    void updateEntry(String id, String date, int mood, String activities, String journal);
}
