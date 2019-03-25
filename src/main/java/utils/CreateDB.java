package utils;

import java.sql.Connection;
import java.sql.SQLException;

public class CreateDB {

    public static void main(String[] args){
        CreateDB creator = new CreateDB();
        creator.createTableQuery();

    }

    String[] createTableQuery(){
        return createTableQuery("21_d1_users");
    }

    String[] createTableQuery(String db_name){
        return new String[]{
                "drop table if exists " + db_name + ";",
                "CREATE TABLE " + db_name + " (userID INT NOT NULL PRIMARY KEY, userName VARCHAR(20) UNIQUE not null, ini VARCHAR(4), " +
                        "cpr VARCHAR(11) NOT NULL, password VARCHAR(128) NOT NULL, roles varchar(64) not null," +
                        "CHECK (userID>=11 AND userID <=99)," +
                        "CHECK (LENGTH(ini) >= 2 AND LENGTH(ini) <= 4)," +
                        "CHECK (LENGTH(cpr) = 11)," +
                        "CHECK (LENGTH(userName) >= 2 AND LENGTH(userName) <= 20)," +
                        "CHECK (roles = 'Admin' OR roles = 'Pharmacist' OR roles = 'Foreman' OR roles = 'Operator'));"
        };
    }

    public void generateTable(String db_name){
        String[] createStatements = createTableQuery();
        if (!db_name.equals("")){
            createStatements = createTableQuery(db_name);
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
