package com.github.lybgeek.common.lang313.service;


import com.github.lybgeek.common.lang313.util.LangInfoUtil;

import java.util.Map;


//@Service
public class CommonLang313Service {
    public void printVersion() {
        LangInfoUtil.printVersionInfo("common-lang3-13");
    }

    public Map<String, Object> getVersionInfo(){
        return LangInfoUtil.getVersionInfo("sofa-ark-common-lang3-13-plugin");
    }



}