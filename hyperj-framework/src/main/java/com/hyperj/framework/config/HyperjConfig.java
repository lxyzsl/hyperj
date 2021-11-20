package com.hyperj.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 读取项目相关配置
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "hyperj")
public class HyperjConfig {

    /** 项目名称 */
    private String name;

    /** 版本 */
    private String version;

    /** 项目描述 */
    private String description;
}
