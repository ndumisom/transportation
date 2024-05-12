package za.com.cocamzansi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.DispatcherServlet;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import za.com.cocamzansi.configuration.ContentCacheDispatcherServlet;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication(scanBasePackages = {"za.com"})
@EnableJpaAuditing
@EnableSwagger2
public class UserApplication extends SpringBootServletInitializer {

		@Override
		protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
			return application.sources(UserApplication.class);
		}

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	// swagger configuration.
	@Bean
	public Docket configureSwagger() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(new ApiInfoBuilder().description("This Micorservice " +
				"serves" + " Cocamzansi").title("Cocamzansi").version("1.0").build()).select().paths(regex("/v1.*")).build();
	}

	@Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
	public DispatcherServlet dispatcherServlet() {
		return new ContentCacheDispatcherServlet();
	}

}
