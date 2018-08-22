package com.jmev.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@EnableAutoConfiguration
@SpringBootApplication(exclude =
{ DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class })
public class CarinfoMonitorApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CarinfoMonitorApplication.class, args);
    }
}
