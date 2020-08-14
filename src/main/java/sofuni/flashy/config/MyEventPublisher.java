package sofuni.flashy.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import sofuni.flashy.services.ScoreboardService;

@Component
public class MyEventPublisher
{
    private final ApplicationEventPublisher applicationEventPublisher;

    public MyEventPublisher(ApplicationEventPublisher applicationEventPublisher)
    {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(String message)
    {
        MyEvent myEvent = new MyEvent(this, message);
        this.applicationEventPublisher.publishEvent(myEvent);
    }
}
