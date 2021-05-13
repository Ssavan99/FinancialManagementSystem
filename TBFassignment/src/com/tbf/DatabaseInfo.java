package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseInfo {
    public static Logger log = Logger.getLogger("DatabaseInfo");

    public static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public static final String url = "jdbc:mysql://cse.unl.edu/savansurep?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static final String username = "savansurep";
    public static final String password = "ABCD1234";
    
    public static Connection getConnection() {
        try {
            Class.forName(DRIVER_CLASS).newInstance();
        } catch (InstantiationException e) {
            log.info("Exception Occured");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            log.info("Exception Occured");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            log.info("Exception Occured");
            e.printStackTrace();
        }


        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException sqle) {
            log.info("Exception Occured");
            throw new RuntimeException(sqle);
        }
    return connection;
    }
}
