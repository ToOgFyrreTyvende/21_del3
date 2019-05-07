package dal;

import java.util.List;

import dto.*;

public interface IUserDAO {

    IUserDTO getUser(int userId) throws DALException;
    List<IUserDTO> getUserList() throws DALException;
    List<IUserRolesDTO> getRoles() throws DALException;

    void createUser(IUserDTO user) throws DALException;
    void updateUser(IUserDTO user) throws DALException;
    void deleteUser(int userId) throws DALException;

    class DALException extends Exception {

        /**
         *
         */
        private static final long serialVersionUID = 7355418246336739229L;

        public DALException(String msg, Throwable e){
            super(msg, e);
        }

        public DALException(String msg){
            super(msg);
        }

    }

}
