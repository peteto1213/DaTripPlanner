package com.ncl.team3.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/08 17:25:24
 */
@Slf4j
public class MyStartConfig {
    private SSHConnection connectionSSH;

    public MyStartConfig() {
        super();
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized() {
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
    public void contextDestroyed() {
        log.info("Context destroyed ... !");
        connectionSSH.closeSSH(); // disconnect
    }
}
