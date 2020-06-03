package com.example.demo.mapper;

import com.example.demo.repositroy.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 * @author zhanghaoyang
 */
@Component
public interface InOutMapper {
    /**
     * 查看用户是否外出
     *
     * @param userId 用户id
     * @return
     */
    @Select("SELECT count(1) FROM sys_in_out WHERE to_community_id is null AND user_id=#{userId}")
    Boolean findIfUserOut(Long userId);

    /**
     * 更新进入状态到数据库
     *
     * @param in
     */
    @Update("UPDATE sys_in_out SET " +
            "to_community_id = #{toCommunityId}," +
            "comment = #{comment}," +
            "in_time=#{inTime}," +
            "in_user_id=#{inUserId} " +
            "WHERE user_id=#{userId} AND in_time is null")
    void in(In in);

    /**
     * 更新出状态到数据库
     *
     * @param out
     */
    @Insert("INSERT INTO sys_in_out(from_community_id,comment,user_id,out_user_id,out_time) " +
            "values(#{fromCommunityId},#{comment},#{userId},#{outUserId},#{outTime})")
    void out(Out out);

    /**
     * 查找所有的出入数据
     *
     * @return
     */
    @Select("SELECT * FROM sys_in_out")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fromCommunityId", column = "from_community_id"),
            @Result(property = "toCommunityId", column = "to_community_id"),
            @Result(property = "outTime", column = "out_time"),
            @Result(property = "inTime", column = "in_time"),
            @Result(property = "inUserId", column = "in_user_id"),
            @Result(property = "outUserId", column = "out_user_id"),
    })
    List<InOut> findInOuts();

    /**
     * 查找用户出入的所有数据
     *
     * @param id 用户id
     * @return
     */
    @Select("SELECT\n" +
            "(SELECT c1.name FROM sys_community c1 WHERE c1.id=io.to_community_id) to_community_name,\n" +
            "(SELECT c1.name FROM sys_community c1 WHERE c1.id=io.from_community_id) from_community_name,\n" +
            "(SELECT u.user_name FROM sys_user u WHERE u.id=io.in_user_id) in_user_name,\n" +
            "(SELECT u.user_name FROM sys_user u WHERE u.id=io.out_user_id) out_user_name,\n" +
            "to_community_id,from_community_id,in_user_id,out_user_id,user_id,id,\n" +
            "(SELECT user_name FROM sys_user where id=#{id}) AS user_name,\n" +
            "(SELECT account FROM sys_user where id=#{id}) AS phone,\n" +
            "comment,out_time,in_time\n" +
            "FROM sys_in_out io\n" +
            "WHERE user_id = #{id}\n" +
            "ORDER BY in_time DESC"
    )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fromCommunityName", column = "from_community_name"),
            @Result(property = "toCommunityName", column = "to_community_name"),
            @Result(property = "outTime", column = "out_time"),
            @Result(property = "inTime", column = "in_time"),
            @Result(property = "inUserName", column = "in_user_name"),
            @Result(property = "outUserName", column = "out_user_name"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "inUserId", column = "in_user_id"),
            @Result(property = "outUserId", column = "out_user_id"),
            @Result(property = "fromCommunityId", column = "from_community_id"),
            @Result(property = "toCommunityId", column = "to_community_id"),
            @Result(property = "userName", column = "user_name"),
    })
    List<InOutInfo> findInOutInfoByUserId(Long id);

    /**
     * 查找用户出入的所有数据(分页)
     *
     * @param id   用户id
     * @param from 从哪开始
     * @param size 往下取多少数据
     * @return
     */
    @Select("SELECT\n" +
            "(SELECT c1.name FROM sys_community c1 WHERE c1.id=io.to_community_id) to_community_name,\n" +
            "(SELECT c1.name FROM sys_community c1 WHERE c1.id=io.from_community_id) from_community_name,\n" +
            "(SELECT u.user_name FROM sys_user u WHERE u.id=io.in_user_id) in_user_name,\n" +
            "(SELECT u.user_name FROM sys_user u WHERE u.id=io.out_user_id) out_user_name,\n" +
            "to_community_id,from_community_id,in_user_id,out_user_id,user_id,id,\n" +
            "(SELECT user_name FROM sys_user where id=#{id}) AS user_name,\n" +
            "(SELECT account FROM sys_user where id=#{id}) AS phone,\n" +
            "comment,out_time,in_time\n" +
            "FROM sys_in_out io\n" +
            "WHERE user_id = #{id}\n" +
            "ORDER BY in_time DESC\n" +
            "LIMIT #{from},#{size}"
    )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fromCommunityName", column = "from_community_name"),
            @Result(property = "toCommunityName", column = "to_community_name"),
            @Result(property = "outTime", column = "out_time"),
            @Result(property = "inTime", column = "in_time"),
            @Result(property = "inUserName", column = "in_user_name"),
            @Result(property = "outUserName", column = "out_user_name"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "inUserId", column = "in_user_id"),
            @Result(property = "outUserId", column = "out_user_id"),
            @Result(property = "fromCommunityId", column = "from_community_id"),
            @Result(property = "toCommunityId", column = "to_community_id"),
            @Result(property = "userName", column = "user_name"),
    })
    List<InOutInfo> findInOutInfoByUserIdPages(String id, Integer from, Integer size);

    /**
     * 用户出入次数(一次出入算一次)
     *
     * @param id 用户id
     * @return
     */
    @Select("SELECT count(1) FROM sys_in_out WHERE user_id=#{id}")
    Integer countInOutInfoByUserId(String id);

    /**
     * 查找警告信息,过了临界值的记录会被找出来
     *
     * @param limTimes 临界值
     * @return
     */
    @Select("SELECT \n" +
            "(SELECT user_name FROM sys_user WHERE id=user_id) username,\n" +
            "(SELECT account FROM sys_user WHERE id=user_id) phone,\n" +
            "count(*) times\n" +
            "FROM sys_in_out\n" +
            "GROUP BY user_id\n" +
            "HAVING times>#{limTimes}")
    List<AlertInfo> findAlertInfo(String limTimes);

    /**
     * 获得用户的警告信息的条数
     *
     * @param limTimes 临界值
     * @return
     */
    @Select("SELECT count(*) FROM\n" +
            "(SELECT count(*) times FROM sys_in_out \n" +
            "GROUP BY user_id \n" +
            "HAVING times>#{limTimes})tmp")
    Integer countAlertInfo(String limTimes);

    /**
     * 获取用户个人的警告信息
     *
     * @param id 用户id
     * @return
     */
    @Select("SELECT \n" +
            "(SELECT user_name FROM sys_user WHERE id=user_id) username,\n" +
            "(SELECT account FROM sys_user WHERE id=user_id) phone,\n" +
            "count(*) times\n" +
            "FROM sys_in_out\n" +
            "WHERE user_id=#{id}")
    List<AlertInfo> findAlertInfoByUserId(String id);

    /**
     * 获取用户个人的警告信息条数
     *
     * @param id 用户id
     * @return
     */
    @Select("SELECT \n" +
            "count(*) times\n" +
            "FROM sys_in_out\n" +
            "WHERE user_id=#{id}")
    Integer countAlertInfoByUserId(String id);


    @Select("SELECT count(1) FROM sys_in_out WHERE user_id=#{id}")
    Integer countInOutInfoByCommunityId(String id);

    /**
     * 获取过去24小时内 用户的入情况
     *
     * @return
     */
    @Select("select date_format(in_time, '%Y-%m-%d %h:00') date, count(1) count\n" +
            "from sys_in_out\n" +
            "group by date\n" +
            "ORDER BY date desc\n" +
            "limit 0,24")
    List<TPSInfo> findInTPSInfo();

    /**
     * 获取过去24小时内 用户的出情况
     *
     * @return
     */
    @Select("select date_format(out_time, '%Y-%m-%d %h:00') date, count(1) count\n" +
            "from sys_in_out\n" +
            "group by date\n" +
            "ORDER BY date desc\n" +
            "limit 0,24")
    List<TPSInfo> findOutTPSInfo();
}
