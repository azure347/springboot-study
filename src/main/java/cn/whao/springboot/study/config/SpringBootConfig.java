package cn.whao.springboot.study.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wanghao
 * @description SpringBoot的配置
 * @create 2025-01-29 8:23
 */
@Data
@Component
@ConfigurationProperties(prefix = "test.springboot")
public class SpringBootConfig {

    private String version;

    private String name;
}
