package com.organization4242.delmgorb.application;

import com.organization4242.delmgorb.view.MainWindowView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main application class.
 *
 * @author Murzinov Ilya
 */
public final class Application {
    private Application() {

    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        MainWindowView mainWindowView = (MainWindowView) context.getBean("mainWindowView");

        mainWindowView.display();
    }
}
