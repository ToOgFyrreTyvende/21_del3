package functionality;

import dal.IUserDAO;
import dto.UserDTO;

import java.util.List;

import dal.IUserDAO.*;


public class UserFunctionality implements IUserFunctionality {
    private IUserDAO userDAO;

    public UserFunctionality(IUserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public UserDTO getUser(int userId) throws UserInputException{
        if (userId >= 11 && userId <= 99){
            try{
                return userDAO.getUser(userId);
            } catch (DALException e){
                throw new UserInputException(e.getMessage());
            }
        } else {
            throw new UserInputException("Ugyldigt bruger ID. Indtast venligst et bruger ID mellem 11 og 99 (inklusivt)");
        }
    }

    @Override
    public List<UserDTO> getUserList() throws DALException{
        return userDAO.getUserList();
    }

    @Override
    public void createUser(UserDTO user) throws UserInputException{
        if (user.getUserId() >= 11 && user.getUserId() <= 99){
            try{
                userDAO.createUser(user);
            } catch (DALException e){
                throw new UserInputException("Fejl, kan være bruger oplysninger. " + e.getMessage());
            }
        } else {
            throw new UserInputException("Fejl, kan være bruger oplysninger. ");
        }
    }

    @Override
    public void updateUser(UserDTO user) throws UserInputException{
        try{
            userDAO.updateUser(user);
        } catch (DALException e){
            throw new UserInputException("Fejl, kan være bruger oplysninger. " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(int userId) throws UserInputException{
        try{
            userDAO.deleteUser(userId);
        } catch (DALException e){
            throw new UserInputException("Fejl, kan være bruger ID. " + e.getMessage());
        }
    }
}
