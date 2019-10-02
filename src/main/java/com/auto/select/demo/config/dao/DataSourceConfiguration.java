package com.auto.select.demo.config.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/8/7 15:08
 */
@Configuration
//配置mybatis mapper的扫描路径
@MapperScan("com.auto.select.demo.dao")
public class DataSourceConfiguration {
    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean("dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException {
        ComboPooledDataSource dateSource = new ComboPooledDataSource();
        dateSource.setDriverClass(jdbcDriver);
        dateSource.setJdbcUrl(jdbcUrl);
        dateSource.setUser(jdbcUsername);
        dateSource.setPassword(jdbcPassword);
        //关闭连接后不自动commit
        dateSource.setAutoCommitOnClose(false);
        return dateSource;
    }
}