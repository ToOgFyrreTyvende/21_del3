package dal;

import dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utils.SQLTools.createConnection;

public class UserDAOMySQL implements IUserDAO {
    private final String DB_NAME;

    public UserDAOMySQL(){
        DB_NAME = "21_d1_users";
    }

    public UserDAOMySQL(String db_name){
        DB_NAME = db_name;
    }

    @Override
    public UserDTO getUser(int userId) throws DALException{
        Connection c = createConnection();
        UserDTO user = new UserDTO();
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + DB_NAME + " where userid =" + userId);
            if (rs.first()){
                user.setUserId(userId);
                user.setUserName(rs.getString(2));
                user.setIni(rs.getString(3));
                user.setCpr(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.addRole(rs.getString(6));
            }
            c.close();
        } catch (NullPointerException N){
            throw new NullPointerException("Looks like it went bad, nullPointException");
        } catch (SQLException e){
            throw new DALException(e.getMessage());
        }
        if (user.getUserId() == 0){
            throw new DALException("Kunne ikke finde brugeren");
        } else {
            return user;
        }
    }

    @Override
    public List<UserDTO> getUserList() throws DALException{
        Connection c = createConnection();
        List<UserDTO> userList = new ArrayList<>();
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select userId from " + DB_NAME);
            while (rs.next()){
                userList.add(getUser(rs.getInt(1)));
            }
            c.close();
        } catch (NullPointerException N){
            throw new NullPointerException("Looks like it went bad, nullPointException");
        } catch (SQLException e){
            throw new DALException(e.getMessage());
        }

        return userList;
    }

    @Override
    public void createUser(UserDTO user) throws DALException{
        Connection c = createConnection();
        int rs = 0;
        try{
            Statement stmt = c.createStatement();
            rs = stmt.executeUpdate("insert into " + DB_NAME + " values( '" + user.getUserId() + "','" + user.getUserName() + "','" + user.getIni() + "','" + user.getCpr() + "','" + user.getPassword() + "','" + user.getRoles().get(0) + "')");
            c.close();
        } catch (NullPointerException N){
            throw new NullPointerException("Looks like it went bad, nullPointException");
        } catch (SQLException de){
            throw new DALException(de.getMessage());
        } finally {
            if (rs == 0){
                System.out.println("doesn't seem like you created a user");
            }
        }
    }

    @Override
    public void updateUser(UserDTO user) throws DALException{
        Connection c = createConnection();
        int rs = 0;
        try{
            Statement stmt = c.createStatement();
            rs = stmt.executeUpdate("update " + DB_NAME + " set userName = '" + user.getUserName() + "', ini = '" + user.getIni() + "', cpr = '" + user.getCpr() + "', password = '" + user.getPassword() + "', roles = '" + user.getRoles().get(0) + "' where userId = " + user.getUserId());
            c.close();
        } catch (NullPointerException N){
            throw new NullPointerException("Looks like it went bad, nullPointException");
        } catch (SQLException e){
            throw new DALException(e.getMessage());
        } finally {
            if (rs == 0){
                System.out.println("doesn't seem like there is a user to update");
            }
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException{
        Connection c = createConnection();
        int rs = 0;
        try{
            Statement stmt = c.createStatement();
            rs = stmt.executeUpdate("delete from " + DB_NAME + " where userId = " + userId);
            c.close();
        } catch (NullPointerException N){
            throw new NullPointerException("Looks like it went bad, nullPointException");
        } catch (SQLException e){
            throw new DALException(e.getMessage());
        } finally {
            if (rs == 0){
                System.out.println("doesn't seem like there is a user to update");
            }
        }
    }
}