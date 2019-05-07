package dal;

import dto.IUserDTO;
import dto.IUserRolesDTO;
import dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserDAOMemory implements IUserDAO {
    //TODO: implement this shit

    private List<IUserDTO> users;

    public UserDAOMemory(){
        users = new ArrayList<>();
    }

    @Override
    public IUserDTO getUser(int userId) throws DALException{
        for (IUserDTO user : users){
            if (user.getUserId() == userId){
                return user;
            }
        }
        return null;
    }

    @Override
    public List<IUserDTO> getUserList() {
        return users;
    }

    @Override
    public List<IUserRolesDTO> getRoles() throws DALException {
        return null;
    }

    @Override
    public void createUser(IUserDTO user) {
        users.add(user);
    }

    @Override
    public void updateUser(IUserDTO user) {
        int userID = user.getUserId();

        int i = 0;
        for (IUserDTO upUser : users){
            if (upUser.getUserId() == userID){
                users.set(i, upUser);
            }
            i++;
        }
    }

    @Override
    public void deleteUser(int userId) {
        int i = 0;
        for (IUserDTO user : users){
            if (user.getUserId() == userId){
                users.remove(i);
            }
            i++;
        }
    }
}
