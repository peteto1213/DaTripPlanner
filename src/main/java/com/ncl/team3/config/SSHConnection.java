package com.ncl.team3.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/03/27 15:31:02
 */
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import jdk.nashorn.internal.objects.annotations.Property;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;
import java.util.Scanner;

/**
 *
 *  @author Lei Chen
 *  @version 1.0
 *  @StudentNumber: 200936497
 *  @date 2022/04/13 13:00:54
 */
public class SSHConnection {
    private final static int LOCAl_PORT = 4321;
    private final static int REMOTE_PORT = 3306;
    private final static int SSH_REMOTE_PORT = 22;
    private static String SSH_USER = System.getProperty("StudentId");
    private  static String SSH_PASSWORD = System.getProperty("StudentPassword");
    private final static String SSH_REMOTE_SERVER = "linux.cs.ncl.ac.uk";
    private final static String MYSQL_REMOTE_SERVER = "cs-db.ncl.ac.uk";
    private final static int REDIS_REMOTE_PORT = 6500;
    private final static String REDIS_REMOTE_URL = "127.0.0.1";
    private final static int REDIS_LOCAL_PORT = 6501;

    private Session mysqlSession; //represents each ssh session
    private Session redisSession;
    public void closeSSH ()
    {
        mysqlSession.disconnect();
        redisSession.disconnect();
    }

    public SSHConnection () throws Throwable
    {
        //System.out.println("Please enter your personal student account number.");
        //Scanner scanner =new Scanner(System.in);
        //SSH_USER = scanner.next();
        //System.out.println("Please enter your personal student account password.");
        //SSH_PASSWORD = scanner.next();

        System.out.println(SSH_USER);
        System.out.println(SSH_PASSWORD);
        JSch jsch = null;

        jsch = new JSch();
        //jsch.addIdentity(S_PATH_FILE_PRIVATE_KEY);

        mysqlSession = jsch.getSession(SSH_USER, SSH_REMOTE_SERVER, SSH_REMOTE_PORT);
        mysqlSession.setPassword(SSH_PASSWORD);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        mysqlSession.setConfig(config);
        mysqlSession.connect(); //ssh connection established!
        JSch jsch2 = new JSch();
        redisSession = jsch2.getSession(SSH_USER, SSH_REMOTE_SERVER, SSH_REMOTE_PORT);
        redisSession.setPassword(SSH_PASSWORD);
        redisSession.setConfig(config);
        //by security policy, you must connect through a forwarded port
        mysqlSession.setPortForwardingL(LOCAl_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT);
        redisSession.setPortForwardingL(REDIS_LOCAL_PORT, REDIS_REMOTE_URL, REDIS_REMOTE_PORT);
    }
}