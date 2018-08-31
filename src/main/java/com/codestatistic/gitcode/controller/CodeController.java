package com.codestatistic.gitcode.controller;

import com.codestatistic.gitcode.Domain.Code;
import com.codestatistic.gitcode.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: Nielson
 * @Date: 2018/8/31 10:51
 * @Description:
 */
@Controller
@RequestMapping("")
public class CodeController {
    @Autowired
    private CodeService codeService;

    //选题系统首页
    @GetMapping(value = "/code")
    public String getTopicAll(Model model){
        List<Code> codes=codeService.queryAll();
        model.addAttribute("codes",codes);
        return "index";
    }
}
