package dal;

import dto.*;
import utils.SQLTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
CREATE TABLE users (userId int NOT NULL AUTO_INCREMENT PRIMARY KEY,
userName VARCHAR(128) UNIQUE not null,
ini VARCHAR(4));

CREATE TABLE roles (roleId int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    role VARCHAR(32) UNIQUE not null,
                    level int);

CREATE TABLE users_roles (user_roleID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                            userId int references users(userId),
                            roleId int references roles(roleId));*/
public class UserDAOMySQL implements IUserDAO {
    private String append_text = "";

    public UserDAOMySQL(String append_text) {
        this.append_text = append_text;
    }
    public UserDAOMySQL() { }

    @Override
    public void createUser(IUserDTO user) throws DALException {
        try (Connection c = SQLTools.createConnection()){

            PreparedStatement prep = c.prepareStatement("insert into " + SQLTools.USER_TABLE + " (userId, userName, ini, cpr, password) VALUES (?, ?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);
            prep.setInt(1, user.getUserId());
            prep.setString(2, user.getUserName());
            prep.setString(3, user.getIni());
            prep.setString(4, user.getCpr());
            prep.setString(5, user.getPassword());
            prep.executeUpdate();
            try (ResultSet generatedId = prep.getGeneratedKeys()){
                if (generatedId.next()){
                    user.setUserId(generatedId.getInt(1));
                }
            }

            ensureRoles(user, c);
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    private void ensureRoles(IUserDTO user, Connection c) throws SQLException {
        PreparedStatement delroles = c.prepareStatement("delete from " + SQLTools.USER_ROLE_TABLE + " where userId = ?");
        delroles.setInt(1, user.getUserId());
        delroles.executeUpdate();
        System.out.println(user);
        for (String s : user.getRoles()){
            PreparedStatement role = c.prepareStatement("select roleId from " + SQLTools.ROLE_TABLE + " where role = ? limit 1;");
            role.setString(1, s);
            ResultSet res = role.executeQuery();
            if (res.first()){
                addRole(user, c, res);
            }else {
                PreparedStatement newRole = c.prepareStatement("insert into " + SQLTools.ROLE_TABLE + " (role, level) VALUES (?, 1);",
                        Statement.RETURN_GENERATED_KEYS);
                newRole.setString(1, s);

                newRole.executeUpdate();
                try (ResultSet generatedId = newRole.getGeneratedKeys()) {
                    if (generatedId.next()) {
                        addRole(user, c, generatedId);
                    }
                    else {
                        throw new SQLException("Adding role might have failed!");
                    }
                }
            }
        }
    }

    private void addRole(IUserDTO user, Connection c, ResultSet res) throws SQLException {
        if (res.first()) {
            PreparedStatement roleAssign =
                    c.prepareStatement("insert into " + SQLTools.USER_ROLE_TABLE + " (userId, roleId) VALUES (?, ?);");
            roleAssign.setInt(1, user.getUserId());
            roleAssign.setInt(2, res.getInt(1));

            roleAssign.executeUpdate();
        }
    }

    @Override
    public IUserDTO getUser(int userId) throws DALException {

        try (Connection c = SQLTools.createConnection()){
            IUserDTO user = new UserDTO();
            PreparedStatement prep = c.prepareStatement(
                    "select u.*, (SELECT GROUP_CONCAT(r.role) FROM " + SQLTools.USER_ROLE_TABLE + " as ur " +
                        "inner join " + SQLTools.ROLE_TABLE+ " as r on r.roleId = ur.roleId WHERE ur.userId = ?) " +
                    "as roles from " + SQLTools.USER_TABLE + " as u where u.userId = ? limit 1");
            prep.setInt(1, userId);
            prep.setInt(2, userId);
            ResultSet resultSet = prep.executeQuery();
            if (resultSet.first()){
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setIni(resultSet.getString("ini"));
                user.setCpr(resultSet.getString("cpr"));
                user.setPassword(resultSet.getString("password"));

                String e = resultSet.getString("roles");
                if (e == null){
                    return user;
                }else{
                    String[] split = resultSet.getString("roles").split(",");
                    List<String> roles = Arrays.asList(split);
                    user.setRoles(roles);
                }
            }

            return user;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }



    @Override
    public List<IUserDTO> getUserList() throws DALException {
        try (Connection c = SQLTools.createConnection()){
            List<IUserDTO> users = new ArrayList<>();
            PreparedStatement prep = c.prepareStatement("Select userId from " + SQLTools.USER_TABLE);
            ResultSet resultSet = prep.executeQuery();
            while (resultSet.next()){
                users.add(getUser(resultSet.getInt("userId")));
            }

            return users;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public List<IUserRolesDTO> getRoles() throws DALException {
        try (Connection c = SQLTools.createConnection()){
            List<IUserRolesDTO> roles = new ArrayList<>();
            PreparedStatement prep = c.prepareStatement("Select roleId, role, level from " + SQLTools.ROLE_TABLE);
            ResultSet resultSet = prep.executeQuery();
            while (resultSet.next()){
                IUserRolesDTO role = new UserRolesDTO(
                        resultSet.getInt("roleId"),
                        resultSet.getString("role"),
                        resultSet.getInt("level"));
                roles.add(role);
            }

            return roles;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }


    @Override
    public void updateUser(IUserDTO user) throws DALException {
        try (Connection c = SQLTools.createConnection()){
            PreparedStatement select = c.prepareStatement("Select * from " + SQLTools.USER_TABLE + " where userId = ? limit 1");
            select.setInt(1, user.getUserId());
            ResultSet resultSet = select.executeQuery();
            if (resultSet.first()){
                PreparedStatement update = c.prepareStatement("update " + SQLTools.USER_TABLE + " set userName = ?, " +
                                "ini = ? where userId = ?",
                        Statement.RETURN_GENERATED_KEYS);
                update.setString(1, user.getUserName());
                update.setString(2, user.getIni());
                update.setInt(3, user.getUserId());
                update.executeUpdate();
                ensureRoles(user, c);
            }
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException {
        try (Connection c = SQLTools.createConnection()){
            PreparedStatement roles = c.prepareStatement("delete from " + SQLTools.USER_ROLE_TABLE + " where userId = ? ");
            System.out.println("Deleted user " + userId + " roles");
            PreparedStatement user = c.prepareStatement("delete from " + SQLTools.USER_TABLE + " where userId = ? ");
            roles.setInt(1, userId);
            user.setInt(1, userId);

            roles.executeUpdate();
            user.executeUpdate();
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
}
