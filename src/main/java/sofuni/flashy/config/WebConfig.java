package sofuni.flashy.config;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sofuni.flashy.stats.StatsInterceptor;

@Component
public class WebConfig implements WebMvcConfigurer
{

    private StatsInterceptor statsInterceptor;

    public WebConfig(StatsInterceptor statsInterceptor)
    {
        this.statsInterceptor = statsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(statsInterceptor);
    }
}
