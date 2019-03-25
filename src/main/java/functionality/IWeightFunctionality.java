package functionality;

// TODO: Check datatypes of methods are as we want
// TODO: Check methods parameters make sense
// Very early stage, error may... nay... WILL occur!
public interface IWeightFunctionality {
    String requestInput(String msg);
    //UserDTO getUser(int userID) throws UserInputException;
    boolean getConfirmation(String msg);
    void taraWeight();
    String getWeight();
}
