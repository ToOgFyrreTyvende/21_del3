package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLTools {
    static final public String USER_TABLE = "21_d3_users";
    static final public String ROLE_TABLE = "21_d3_roles";
    static final public String USER_ROLE_TABLE = "21_d3_users_roles";
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
