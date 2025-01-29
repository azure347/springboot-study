package cn.whao.springboot.study.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author wanghao
 * @description
 * @create 2025-01-29 15:41
 */
@Order(1)
@Slf4j
@Component
public class BootCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("This is BootCommandLineRunner...");
    }
}
