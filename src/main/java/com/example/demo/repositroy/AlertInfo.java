package com.example.demo.repositroy;

import lombok.Data;
import org.springframework.stereotype.Repository;

/**
 * @author zhanghaoyang
 */
@Repository
@Data
public class AlertInfo {
    String username;
    String phone;
    Integer times;
}
