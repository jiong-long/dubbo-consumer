package com.jianghu;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author wangjinlong
 */
@Slf4j
@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext application = SpringApplication.run(WebApplication.class, args);

		//显示项目访问地址
		Environment env = application.getEnvironment();
		String ip = "127.0.0.1";
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");
		if (StrUtil.isEmpty(path)) {
			path = "";
		}
		log.info("\n\t----------------------------------------------------------\n\t" +
				"Application is running! Access URLs:\n\t" +
				"项目访问地址: \t\thttp://" + ip + ":" + port + path + "\n\t" +
				"Swagger访问地址: \thttp://" + ip + ":" + port + path + "/swagger-ui.html\n\t" +
				"----------------------------------------------------------");
	}

}
