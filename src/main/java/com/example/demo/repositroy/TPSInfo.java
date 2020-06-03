package com.example.demo.repositroy;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author zhanghaoyang
 */
@Repository
@Data
public class TPSInfo {
    String date;
    Integer count;
}
