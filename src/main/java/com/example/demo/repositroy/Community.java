package com.example.demo.repositroy;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author zhanghaoyang
 */
@Repository
@Data
public class Community implements Serializable {
    Long id;
    String name;
    String description;
    String address;
}
