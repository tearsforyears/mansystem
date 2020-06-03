package com.example.demo.mapper;

import com.example.demo.repositroy.Message;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhanghaoyang
 */
@Component
public interface MessageMapper {
    @Select("SELECT * FROM sys_message where community_id=#{id}")
    List<Message> findMessagesByCommunityId(Long id);


    @Insert("INSERT INTO sys_message(community_id,content,title,importance) values(#{id},#{msg.content},#{msg.title},#{msg.importance})")
    void insertMessage(Long id, Message msg);

    @Delete("DELETE FROM sys_message where id=#{id}")
    void deleteMessage(Long id);
}
