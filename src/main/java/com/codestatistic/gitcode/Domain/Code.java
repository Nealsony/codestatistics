package com.codestatistic.gitcode.Domain;

import lombok.Data;

/**
 * @Auther: Nielson
 * @Date: 2018/8/31 11:21
 * @Description:
 */
@Data
public class Code {
    long id;
    String username;
    int add;
    int remove;
    int increment;
    String datetime;
}
