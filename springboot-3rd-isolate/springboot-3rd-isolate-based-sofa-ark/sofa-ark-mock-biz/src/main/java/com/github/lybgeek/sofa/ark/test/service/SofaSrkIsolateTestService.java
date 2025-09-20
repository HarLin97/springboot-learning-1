package com.github.lybgeek.sofa.ark.test.service;


import com.github.lybgeek.common.lang313.service.CommonLang313Service;
import com.github.lybgeek.common.lang33.service.CommonLang33Service;
import com.github.lybgeek.sofa.ark.test.util.LangInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SofaSrkIsolateTestService {

    @Autowired
    private CommonLang313Service commonLang313Service;

    @Autowired
    private CommonLang33Service commonLang33Service;






    public void printVersion() {
        LangInfoUtil.printVersionInfo("sofa-ark-mock-biz");
        System.out.println("--------------------------------------------------------");
        commonLang313Service.printVersion();
        System.out.println("--------------------------------------------------------");
        commonLang33Service.printVersion();

    }

    public List<Map<String, Object>> getVersionInfos(){
        List<Map<String, Object>>  versionInfos = new ArrayList<>();
        versionInfos.add(LangInfoUtil.getVersionInfo("sofa-ark-mock-biz"));
        versionInfos.add(commonLang313Service.getVersionInfo());
        versionInfos.add(commonLang33Service.getVersionInfo());
        return versionInfos;

    }
}
