package cn.whao.springboot.study.controller;

import cn.whao.springboot.study.config.SpringBootConfig;
import cn.whao.springboot.study.vo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

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

    private final ObjectMapper mapper;

    @Value("${test.springboot.version}")
    private String version;

    @Value("${test.springboot.name}")
    private String name;

    @Autowired
    public TestController(SpringBootConfig springBootConfig, ObjectMapper mapper) {
        this.springBootConfig = springBootConfig;
        this.mapper = mapper;
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

    @GetMapping("/jackson")
    public User jackson() throws Exception {
        User user = User.builder()
                .name("xiaoming")
                .age(18)
                .address("beijing")
                .registerTime(new Date())
                .build();

        String jsonUser = mapper.writeValueAsString(user);
        log.info("jsonUser: {}", jsonUser);
        return mapper.readValue(jsonUser, User.class);
    }

}
