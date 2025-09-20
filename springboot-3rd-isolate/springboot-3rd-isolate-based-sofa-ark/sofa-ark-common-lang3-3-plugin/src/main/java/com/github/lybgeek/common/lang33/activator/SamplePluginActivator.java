package com.github.lybgeek.common.lang33.activator;

import com.alipay.sofa.ark.exception.ArkRuntimeException;
import com.alipay.sofa.ark.spi.model.PluginContext;
import com.alipay.sofa.ark.spi.service.PluginActivator;
import com.github.lybgeek.common.lang33.service.CommonLang33Service;

public class SamplePluginActivator implements PluginActivator {

    @Override
    public void start(PluginContext context) throws ArkRuntimeException {
        System.out.println("start in sofa-ark-common-lang3-3-plugin activator");
        context.publishService(CommonLang33Service.class, new CommonLang33Service());
    }

    @Override
    public void stop(PluginContext context) throws ArkRuntimeException {
        System.out.println("stop in sofa-ark-common-lang3-3-plugin activator");
    }

}