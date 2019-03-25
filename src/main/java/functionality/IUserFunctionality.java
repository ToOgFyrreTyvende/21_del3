package functionality;

import dal.IUserDAO.*;
import dto.UserDTO;

import java.util.List;

public interface IUserFunctionality {
    UserDTO getUser(int userId) throws UserInputException;
    List<UserDTO> getUserList() throws DALException;
    void createUser(UserDTO user) throws UserInputException;
    void updateUser(UserDTO user) throws UserInputException;
    void deleteUser(int userId) throws UserInputException;


    class UserInputException extends Exception {

        /**
         *
         */
        private static final long serialVersionUID = 7355418246336739229L;

        public UserInputException(String msg, Throwable e){
            super(msg, e);
        }

        public UserInputException(String msg){
            super(msg);
        }

    }
}
