package dto;

import java.util.List;

public interface IUserDTO {
    int getUserId();

    void setUserId(int userId);

    String getUserName();

    void setUserName(String userName);

    String getIni();

    void setIni(String ini);

    List<String> getRoles();

    void setRoles(List<String> roles);

    void addRole(String role);

    String getCpr();
    void setCpr(String cpr);


    boolean removeRole(String role);

    String getPassword();
    void setPassword(String password);
}
