package com.bitauto.ep.fx.webapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.bitauto.ep.fx")
//@ComponentScan("com.bitauto.ep.fx.webapi")
//@EnableAdminServer//启动服务端
//从指定包中数据仓库组件
public class Application /* extends SpringBootServletInitializer*/ {

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}*/
	public static void main(String[] args) {
	 	SpringApplication.run(Application.class, args);
	}

}

