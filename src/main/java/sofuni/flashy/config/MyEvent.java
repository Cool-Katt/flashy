package sofuni.flashy.config;

import org.springframework.context.ApplicationEvent;

public class MyEvent extends ApplicationEvent
{
    private String msg;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MyEvent(Object source, String msg)
    {
        super(source);
        this.msg = msg;
    }
}
