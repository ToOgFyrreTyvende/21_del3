package dal;

import dto.IUserDTO;
import dto.IUserRolesDTO;
import dto.UserDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAODisk implements IUserDAO {

    public UserDAODisk() throws IOException{
    }

    public void open_file(){

    }

    // ---- GET USER -------------------------------------------------------------------------------------------------------
    @Override
    public IUserDTO getUser(int userId) {

        List<IUserDTO> user_list = new ArrayList<>();

        try{

            FileInputStream fis = new FileInputStream("src\\main\\resources\\disk.txt");

            try{
                ObjectInputStream ois = new ObjectInputStream(fis);
                user_list = (ArrayList) ois.readObject();
                ois.close();
            } catch (EOFException e){
                user_list = new ArrayList<>();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            fis.close();

        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        for (IUserDTO user : user_list){
            if (user.getUserId() == userId){
                return user;
            }
        }
        return null;
    }

    // ---- GET USER LIST --------------------------------------------------------------------------------------------------
    @Override
    public List<IUserDTO> getUserList() {

        List<IUserDTO> user_list = new ArrayList<>();

        try{

            FileInputStream fis = new FileInputStream("src\\main\\resources\\disk.txt");

            try{
                ObjectInputStream ois = new ObjectInputStream(fis);
                user_list = (ArrayList) ois.readObject();
                ois.close();
            } catch (EOFException e){
                user_list = new ArrayList<>();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            fis.close();

        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        return user_list;
    }

    @Override
    public List<IUserRolesDTO> getRoles() throws DALException {
        return null;
    }


    // ---- CREATE USER ----------------------------------------------------------------------------------------------------
    @Override
    public void createUser(IUserDTO user) {

        List<IUserDTO> user_list = new ArrayList<>();

        try{

            FileInputStream fis = new FileInputStream("src\\main\\resources\\disk.txt");

            try{
                ObjectInputStream ois = new ObjectInputStream(fis);
                user_list = (ArrayList) ois.readObject();
                ois.close();
            } catch (EOFException e){
                user_list = new ArrayList<>();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            fis.close();

            user_list.add(user);

            FileOutputStream fos = new FileOutputStream("src\\main\\resources\\disk.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(user_list);

            oos.close();
            fos.close();


        } catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    // ---- UPDATE USER ----------------------------------------------------------------------------------------------------
    @Override
    public void updateUser(IUserDTO inputUser) {
        List<IUserDTO> user_list = new ArrayList<>();

        try{

            FileInputStream fis = new FileInputStream("src\\main\\resources\\disk.txt");

            try{
                ObjectInputStream ois = new ObjectInputStream(fis);
                user_list = (ArrayList) ois.readObject();
                ois.close();
            } catch (EOFException e){
                user_list = new ArrayList<>();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            fis.close();

            int inputUserId = inputUser.getUserId();
            int i = 0;
            for (IUserDTO userInList : user_list){
                if (userInList.getUserId() == inputUserId){
                    user_list.set(i, inputUser);
                }
                i++;
            }

            FileOutputStream fos = new FileOutputStream("src\\main\\resources\\disk.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(user_list);

            oos.close();
            fos.close();

        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    // ---- DELETE USER ----------------------------------------------------------------------------------------------------
    @Override
    public void deleteUser(int userId) {
        List<UserDTO> user_list = new ArrayList<>();

        try{

            FileInputStream fis = new FileInputStream("src\\main\\resources\\disk.txt");

            try{
                ObjectInputStream ois = new ObjectInputStream(fis);
                user_list = (ArrayList) ois.readObject();
                ois.close();
            } catch (EOFException e){
                user_list = new ArrayList<>();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            fis.close();

            int i = 0;
            for (UserDTO user : user_list){
                if (user.getUserId() == userId){
                    user_list.remove(i);
                }
                i++;
            }

            FileOutputStream fos = new FileOutputStream("src\\main\\resources\\disk.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(user_list);

            oos.close();
            fos.close();

        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
