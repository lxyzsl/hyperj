package com.hyperj.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 读取项目相关配置
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "counter")
public class CounterConfig {

    /** 数据中心ID */
    private Long dataCenterId;

    /** 工作机器ID */
    private Long workerId;

}
