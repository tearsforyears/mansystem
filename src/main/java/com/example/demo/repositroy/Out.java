package com.example.demo.repositroy;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author zhanghaoyang
 */
@Data
@Repository
public class Out {
    Long userId;
    Long fromCommunityId;
    String comment;
    Date outTime;
    Long outUserId;
}
