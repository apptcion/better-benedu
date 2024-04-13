package com.benedu.javaConfig;

import java.io.BufferedReader;

import java.io.FileReader;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.benedu.controller.MainController;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
    @Bean
    @Primary
    public DataSource dataSource() {
       HikariDataSource dataSource = new HikariDataSource();
       dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
       dataSource.setJdbcUrl("jdbc:oracle:thin:@(description= (retry_count=20)(retry_delay=3)(address=(protocol=tcps)(port=1521)(host=adb.ap-seoul-1.oraclecloud.com))(connect_data=(service_name=g3eca1928e569e3_e5scp61n3vndf130_medium.adb.oraclecloud.com))(security=(ssl_server_dn_match=yes)))");
     //  dataSource.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:octl");
       dataSource.setConnectionTimeout(30000);
       dataSource.setMaximumPoolSize(10);
       dataSource.setIdleTimeout(600000);
       dataSource.setPoolName("SpringHikariCP");
       dataSource.setMaxLifetime(1800000);
        try {
        	//파일 위치 바꿀 필요 있음
        	FileReader fr = new FileReader("C:/Users/mojan/remake_benedu/workspace/betterbenedu/src/main/resources/DBUser/dlfma.txt");
        	BufferedReader br = new BufferedReader( fr );
        	dataSource.setUsername("ADMIN");
        	
        	FileReader fr2 = new FileReader("C:/Users/mojan/remake_benedu/workspace/betterbenedu/src/main/resources/DBUser/qlalfqjsgh.txt");
        	BufferedReader br2 = new BufferedReader( fr2 );
        	dataSource.setPassword("wsXUmX?mU*W7w!_");
        	
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return dataSource;
    }

	
	
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        return sessionFactory.getObject();
    }
    
    @Bean
    public DataSourceTransactionManager transactionManager() {
    	return new DataSourceTransactionManager(dataSource());
    }
    
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.benedu.mappers");
        return mapperScannerConfigurer;
    }
    

}
