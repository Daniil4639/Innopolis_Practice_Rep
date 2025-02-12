package app.configs.web_config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
@ComponentScan("app")
public class WebConfig implements WebMvcConfigurer {}