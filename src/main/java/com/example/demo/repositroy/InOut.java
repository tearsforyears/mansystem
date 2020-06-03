package com.example.demo.repositroy;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author zhanghaoyang
 * 进出表先出后进 可以本社区出本社区进 假定是一个简单外出
 * outTime指的是出社区的时间 inTime是进入社区时间
 */
@Repository
@Data
public class InOut {
    Long id;
    Long userId;
    Long fromCommunityId;
    Long toCommunityId;
    String comment;
    Date outTime;
    Date inTime;
    Long inUserId;
    Long outUserId;
}
