
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    protected String configFile;
    protected ApplicationContext ctx;

    public SpringTest(String configFileName) {
        String packagePath = this.getClass().getPackage().getName().replaceAll("\\.", "/");
        this.configFile = "/" + packagePath + "/" + configFileName;
        this.ctx = new ClassPathXmlApplicationContext(this.configFile);
    }
}
