package com.seniorProject.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.seniorProject.project.model.Entry;

import java.util.List;

@Mapper
public interface EntryMapper {

    @Select("select * from `entries` where id = #{id}")
    List<Entry> selectEntry(String id);
}
