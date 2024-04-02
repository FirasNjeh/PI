package esprit.pi.demo.Configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final BanFilter banFilter;

    public WebMvcConfig(BanFilter banFilter) {
        this.banFilter = banFilter;
    }

    @Bean
    public FilterRegistrationBean<BanFilter> loggingFilter(){
        FilterRegistrationBean<BanFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(banFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
