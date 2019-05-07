package functionality;

import dal.IUserDAO.*;
import dto.*;

import java.util.List;

public interface IUserFunctionality {
    IUserDTO getUser(int userId) throws UserInputException;
    List<IUserDTO> getUserList() throws DALException;
    List<IUserRolesDTO> getRolesList() throws DALException;

    void createUser(IUserDTO user) throws UserInputException;
    void updateUser(IUserDTO user) throws UserInputException;
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
