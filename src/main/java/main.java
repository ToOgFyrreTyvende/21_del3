import functionality.IUserFunctionality;
import functionality.IWeightFunctionality;
import functionality.UserFunctionality;
import functionality.WeightFunctionality;
import services.IMettlerScale;
import services.MettlerScale;
import ui.IUI;
import dal.*;
import ui.WeightUI;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException, IUserDAO.DALException{

        IUserDAO daoMySQL = new UserDAOMySQL();
        IUserFunctionality userFunc = new UserFunctionality(daoMySQL);
        IMettlerScale scale = new MettlerScale("127.0.0.1", 8000);
        IWeightFunctionality wFunc = new WeightFunctionality(scale);
        IUI ui = new WeightUI(wFunc);

        ui.showMenu();
    }
}