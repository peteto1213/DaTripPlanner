package com.ncl.team3.config;

/**
 * As the initial database for the project was located inside the school server,
 * external access to port 3306 of the database was not available.
 * So it was necessary to use an SSH channel to connect to the school's linux server and use this as a bridging
 * channel to access the internal Mysql database.
 * The main function of this class is to establish an SSH connection when the Tomcat server is started.
 *
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/03/27 16:17:08
 */
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


@Slf4j
@Configuration
public class MyContextListener
        //implements ServletContextListener
         {

    private SSHConnection connectionSSH;

    public MyContextListener() {
        super();
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Context initialized ... !");
        try {
            connectionSSH = new SSHConnection();
        } catch (Throwable e) {
            e.printStackTrace(); // error connecting SSH server
        }
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Context destroyed ... !");
        connectionSSH.closeSSH(); // disconnect
    }


}