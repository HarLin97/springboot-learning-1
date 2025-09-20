package com.github.lybgeek.common.lang33.service;


import com.github.lybgeek.common.lang33.util.LangInfoUtil;
import org.springframework.stereotype.Service;

import java.util.Map;


//@Service
public class CommonLang33Service {
    public void printVersion() {
        LangInfoUtil.printVersionInfo("common-lang3-3");
    }

    public Map<String, Object> getVersionInfo(){
        return LangInfoUtil.getVersionInfo("sofa-ark-common-lang3-3-plugin");
    }



}