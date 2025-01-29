package cn.whao.springboot.study.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wanghao
 * @description 自定义异步线程池的配置
 * @create 2025-01-29 9:01
 */
@Slf4j
@Configuration
public class AsyncPoolConfig implements AsyncConfigurer {

    @Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 线程池核心线程数量，默认值是1
        executor.setCorePoolSize(5);
        // 线程池维护线程的最大数量
        executor.setMaxPoolSize(10);
        // 缓冲队列
        executor.setQueueCapacity(20);
        // 超出核心线程数之外的线程的最大存活时间，默认60s
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("Async-");

        // 是否等待所有线程执行完毕后再关闭线程池，默认是false
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 等待时长，默认是0
        executor.setAwaitTerminationSeconds(60);

        // 拒绝策略
        executor.setRejectedExecutionHandler(
                // 用于被拒绝任务的一个处理程序，它直接在executor方法的调用线程中运行被拒绝的任务，如果执行程序关闭则丢弃这个任务
//                new ThreadPoolExecutor.CallerRunsPolicy()
                // 如果线程池运行的任务满了，并且队列也是满的，它会直接抛出异常
                new ThreadPoolExecutor.AbortPolicy()
                // 当线程池的数量等于最大线程数，而且队列是满的情况下，它会抛弃线程池中最后要执行的一个任务，并执行新传递进来的任务
//                new ThreadPoolExecutor.DiscardOldestPolicy()
                // 当线程池的数量等于最大线程数，不做任何处理
//                new ThreadPoolExecutor.DiscardPolicy()
        );
        executor.initialize();

        return executor;
    }

    /**
     * 定义异步任务异常处理类 -- 只会处理没有返回值的异步任务
     * 对于没有返回值的异步调用，异步处理抛出异常后就会由AsyncUncaughtExceptionHandler去捕获指定的异常，原有任务还会继续执行直到结束
     *
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
            log.info("AsyncError: {}, Method: {}, Param: {}", throwable.getMessage(), method.getName(), JSON.toJSONString(objects));
            throwable.printStackTrace();

            // TODO 发送邮件或短信
        }
    }
}
