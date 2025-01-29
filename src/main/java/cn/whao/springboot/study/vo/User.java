package cn.whao.springboot.study.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wanghao
 * @description 用户
 * @create 2025-01-29 15:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 忽略一组属性
//@JsonIgnoreProperties({"address", "name"})
public class User {

    private String name;

    private int age;

    // 忽略属性
    @JsonIgnore
    private String address;

    // 设置别名
    @JsonProperty("rt")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;   // 默认jackson会序列化为时间戳
}
