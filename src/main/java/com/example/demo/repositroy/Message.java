package com.example.demo.repositroy;

import lombok.Data;
import org.springframework.stereotype.Repository;

/**
 * @author zhanghaoyang
 */
@Repository
@Data
public class Message {
    Long id;
    String title;
    String content;
    Integer importance;
}
