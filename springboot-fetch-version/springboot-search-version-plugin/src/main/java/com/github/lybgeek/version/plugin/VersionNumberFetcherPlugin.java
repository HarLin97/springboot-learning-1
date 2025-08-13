package com.github.lybgeek.version.plugin;

import com.github.lybgeek.version.util.VersionNoUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


@Mojo(name = "version-fetch", defaultPhase = LifecyclePhase.INSTALL)
public class VersionNumberFetcherPlugin extends AbstractMojo {

    /**
     * 版本号，多个用逗号隔开
     */
    @Parameter
    private String versionNo;

    /**
     * 目标类-归属应用ID
     */
    @Parameter
    private String appId;

    /**
     * 版本号上报地址
     */
    @Parameter
    private String reportBaseUrl;


    /**
     * 版本号上报地址路径
     */
    @Parameter(defaultValue = "/version/report")
    private String reportPath;


    /**
     * 是否启用插件
     */
    @Parameter(defaultValue = "true")
    private String enabled;

    @Override
    public void execute() {
        if(Boolean.parseBoolean(enabled)){
            VersionNoUtils.reportVersion2RemoteSvc(reportBaseUrl + reportPath,appId,versionNo);
        }


    }






}
