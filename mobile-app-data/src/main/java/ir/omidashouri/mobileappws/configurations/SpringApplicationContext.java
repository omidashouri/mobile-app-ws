package ir.omidashouri.mobileappws.configurations;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringApplicationContext implements ApplicationContextAware {

//    use this class to have access to application Context Objects

    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT=applicationContext;
    }

//    with this method we can get already created bean from spring
    public static Object getBean(String beanName){
        return CONTEXT.getBean(beanName);
    }
}
