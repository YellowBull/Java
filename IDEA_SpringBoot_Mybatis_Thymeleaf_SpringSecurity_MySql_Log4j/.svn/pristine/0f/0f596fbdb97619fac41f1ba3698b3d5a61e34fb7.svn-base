package com.jmev.cn.dao.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author 邵欣
 * @date 2018/1/16.
 */
@Configuration
@MapperScan(basePackages = { "com.jmev.cn.dao" }, sqlSessionFactoryRef = "sqlSessionFactory")
public class MyBatisConfig
{

    @Autowired
    @Qualifier("druidDataSource")
    private DataSource mybaitsDs;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception
    {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        // 使用druidDataSource数据源, 连接业务库
        factoryBean.setDataSource(mybaitsDs);
        Resource[] mapperLocations = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml");
        factoryBean.setMapperLocations(mapperLocations);
        // 加载全局的配置文件
        factoryBean.setConfigLocation(new DefaultResourceLoader().getResource("config/core/SqlMapConfig.xml"));
        //添加插件
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception
    {
        // 使用上面配置的Factory
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory());
        return template;
    }
}
