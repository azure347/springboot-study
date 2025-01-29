package cn.whao.springboot.study.controller;

import cn.whao.springboot.study.config.SpringBootConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanghao
 * @description 测试controller
 * @create 2025-01-29 8:13
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    private final SpringBootConfig springBootConfig;

    @Value("${test.springboot.version}")
    private String version;

    @Value("${test.springboot.name}")
    private String name;

    @Autowired
    public TestController(SpringBootConfig springBootConfig) {
        this.springBootConfig = springBootConfig;
    }

    /**
     * 第一种方式的数据注入
     */
    @GetMapping("/conf_inject_1")
    public void firstConfInject() {
        log.info("first Conf Inject: {} {}" ,version, name);
    }

    /**
     * 第二种方式的数据注入
     */
    @GetMapping("/conf_inject_2")
    public void secondConfInject() {
        log.info("second Conf Inject: {} {}" ,springBootConfig.getVersion(), springBootConfig.getName());
    }

}
