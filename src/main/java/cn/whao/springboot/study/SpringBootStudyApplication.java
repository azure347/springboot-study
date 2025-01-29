package cn.whao.springboot.study;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author wanghao
 * @description 应用启动类
 * @create 2025-01-28 16:37
 */
@SpringBootApplication
public class SpringBootStudyApplication {

    public static void main(String[] args) {

        // 1. 通过静态 run 方法
//        SpringApplication.run(SpringBootStudyApplication.class, args);

        // 2. 通过 api 调整应用行为
        /*SpringApplication application =
                new SpringApplication(SpringBootStudyApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);*/

        // 3. SpringApplicationBuilder Fluent Api  链式调用
        new SpringApplicationBuilder(SpringBootStudyApplication.class)
                .bannerMode(Banner.Mode.OFF)
//                .web(WebApplicationType.NONE)
                .run(args);

    }
}
