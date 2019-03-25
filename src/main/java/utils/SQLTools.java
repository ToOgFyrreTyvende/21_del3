package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLTools {
    static public Connection createConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            return DriverManager.getConnection(
                    "jdbc:mysql://" + System.getenv("DB_URL") + "/" + System.getenv("DB_USER"),
                    System.getenv("DB_USER"), System.getenv("DB_PASS"));
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
