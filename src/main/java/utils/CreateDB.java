package utils;

import java.sql.Connection;
import java.sql.SQLException;

public class CreateDB {

    public static void main(String[] args){
        CreateDB creator = new CreateDB();
        creator.generateTable("");

    }

    String[] createTableQuery(){
        return createTableQuery("");
    }

    String[] createTableQuery(String append){
        return new String[]{
                "drop table if exists " + SQLTools.USER_TABLE + append + ";",
                "drop table if exists " + SQLTools.ROLE_TABLE + append + ";",
                "drop table if exists " + SQLTools.USER_ROLE_TABLE + append + ";",
                "CREATE TABLE " + SQLTools.USER_TABLE + append + " (userId int NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "userName VARCHAR(128) UNIQUE not null," +
                        "ini VARCHAR(4)," +
                        "password VARCHAR(50)," +
                        "cpr VARCHAR(11) UNIQUE not null);",

                "CREATE TABLE " + SQLTools.ROLE_TABLE + append + " (roleId int NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "role VARCHAR(32) UNIQUE not null," +
                        "level int);",

                "CREATE TABLE " + SQLTools.USER_ROLE_TABLE + append + " (user_roleID int NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "userId int references users(userId) ON DELETE CASCADE," +
                        "roleId int references roles(roleId) ON DELETE CASCADE);",
        };
    }


    public void generateTable(String append_text){
        String[] createStatements = createTableQuery();
        if (!append_text.equals("")){
            createStatements = createTableQuery(append_text);
        }

        try{
            Connection c = SQLTools.createConnection();
            for (String statement : createStatements){
                c.createStatement().executeUpdate(statement);
            }
            c.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
