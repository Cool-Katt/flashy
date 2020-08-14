package sofuni.flashy.stats;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sofuni.flashy.services.impl.StatsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
@Component
public class StatsInterceptor implements HandlerInterceptor
{
    private final StatsService statsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception
    {
        this.statsService.incRequestCount();
        return true;
    }
}
