package dal;

import dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserDAOMemory implements IUserDAO {
    //TODO: implement this shit

    private List<UserDTO> users;

    public UserDAOMemory(){
        users = new ArrayList<>();
    }

    @Override
    public UserDTO getUser(int userId) throws DALException{
        for (UserDTO user : users){
            if (user.getUserId() == userId){
                return user;
            }
        }
        return null;
    }

    @Override
    public List<UserDTO> getUserList() throws DALException{
        return users;
    }

    @Override
    public void createUser(UserDTO user) throws DALException{
        users.add(user);
    }

    @Override
    public void updateUser(UserDTO user) throws DALException{
        int userID = user.getUserId();

        int i = 0;
        for (UserDTO upUser : users){
            if (upUser.getUserId() == userID){
                users.set(i, upUser);
            }
            i++;
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException{
        int i = 0;
        for (UserDTO user : users){
            if (user.getUserId() == userId){
                users.remove(i);
            }
            i++;
        }
    }
}
