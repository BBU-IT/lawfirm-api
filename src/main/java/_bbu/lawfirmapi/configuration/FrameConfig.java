package  _bbu.lawfirmapi.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import jakarta.servlet.Filter;

@Configuration
public class FrameConfig {

    @Bean
    public Filter frameOptionsHeaderFilter() {
        return (req, res, chain) -> {
            chain.doFilter(req, res);
            var response = (jakarta.servlet.http.HttpServletResponse) res;

            response.setHeader("X-Frame-Options", "ALLOWALL");
        };
    }
}
