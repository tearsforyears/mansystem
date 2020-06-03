package com.example.demo.repositroy;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author zhanghaoyang
 */
@Data
@Repository
public class In {
    Long userId;
    Long toCommunityId;
    String comment;
    Date inTime;
    Long inUserId;
}
