package com.hyperj.app;

import com.hyperj.common.utils.uuid.Uuid;
import com.hyperj.framework.config.CounterConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = {
        "com.hyperj.common",
        "com.hyperj.framework",
        "com.hyperj.system",
        "com.hyperj.app",
})
// 如果是多数据元，则需要加上下面这句话，避免即禁止 SpringBoot 自动注入数据源配置，采用手动配置
// ,exclude= {DataSourceAutoConfiguration.class}
// 多模块项目需要通过@MapperScan注解来扫描dao，因为单个模块下的包名不一样，所以@Mapper注解不生效
@MapperScan(value = "com.hyperj.**.dao")
public class HyperjAppApplication {

    @Autowired
    private CounterConfig counterConfig;

    @PostConstruct
    private void init(){
        Uuid.getInstance().init(counterConfig.getDataCenterId(), counterConfig.getWorkerId());
    }

    public static void main(String[] args) {
        SpringApplication.run(HyperjAppApplication.class, args);
    }

}
