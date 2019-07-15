
import org.springframework.context.ApplicationEvent;

public class EmailBlackListEvent extends ApplicationEvent {

    public EmailBlackListEvent(Object source, String text) {

        super(source);
    }
}
