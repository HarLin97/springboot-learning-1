package com.github.lybgeek.sofa.ark.test.controller;


import com.github.lybgeek.sofa.ark.test.service.SofaSrkIsolateTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sofa-ark")
public class SofaSrkIsolateTestController {


    @Autowired
    private SofaSrkIsolateTestService sofaSrkIsolateTestService;


    @RequestMapping("/test")
    public List<Map<String, Object>> getVersionInfos(){
        sofaSrkIsolateTestService.printVersion();
        return sofaSrkIsolateTestService.getVersionInfos();
    }
}
