package com.github.lybgeek.common.lang313.activator;

import com.alipay.sofa.ark.exception.ArkRuntimeException;
import com.alipay.sofa.ark.spi.model.PluginContext;
import com.alipay.sofa.ark.spi.service.PluginActivator;
import com.github.lybgeek.common.lang313.service.CommonLang313Service;

public class SamplePluginActivator implements PluginActivator {

    @Override
    public void start(PluginContext context) throws ArkRuntimeException {
        System.out.println("start in sofa-ark-common-lang3-13-plugin activator");
        context.publishService(CommonLang313Service.class, new CommonLang313Service());
    }

    @Override
    public void stop(PluginContext context) throws ArkRuntimeException {
        System.out.println("stop in sofa-ark-common-lang3-13-plugin activator");
    }

}