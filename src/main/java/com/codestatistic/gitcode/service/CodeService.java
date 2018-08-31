package com.codestatistic.gitcode.service;

import com.codestatistic.gitcode.Domain.Code;

import java.util.List;

/**
 * @Auther: Nielson
 * @Date: 2018/8/31 11:19
 * @Description:
 */
public interface  CodeService {
    List<Code> queryAll();
}
