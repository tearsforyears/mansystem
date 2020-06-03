package com.example.demo.repositroy;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class Permission {
    Long id;
    String permissionCode;
    String permissionName;
}
