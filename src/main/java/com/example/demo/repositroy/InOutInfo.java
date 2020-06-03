package com.example.demo.repositroy;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author zhanghaoyang
 */
@Repository
@Data
public class InOutInfo {
    Long id;
    Long userId;
    String fromCommunityName;
    String toCommunityName;
    String phone;
    String comment;
    Date outTime;
    Date inTime;
    String inUserName;
    String outUserName;
    Long inUserId;
    Long outUserId;
    Long fromCommunityId;
    Long toCommunityId;
    String userName;
}