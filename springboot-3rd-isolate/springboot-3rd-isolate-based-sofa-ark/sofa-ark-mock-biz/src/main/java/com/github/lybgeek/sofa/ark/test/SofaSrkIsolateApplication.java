package com.github.lybgeek.sofa.ark.test;



import com.alipay.sofa.ark.support.startup.SofaArkBootstrap;
import com.github.lybgeek.sofa.ark.test.service.SofaSrkIsolateTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication(scanBasePackages = "com.github.lybgeek")
@ImportResource({ "classpath*:META-INF/spring/service.xml" })
public class SofaSrkIsolateApplication implements ApplicationRunner {

    @Autowired
    private SofaSrkIsolateTestService sofaSrkIsolateTestService;






    public static void main(String[] args) throws Exception {


        SofaArkBootstrap.launch(args);
        SpringApplication.run(SofaSrkIsolateApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        sofaSrkIsolateTestService.printVersion();
    }
}
