package functionality;

import services.IMettlerScale;

public class WeightFunctionality implements IWeightFunctionality {
    private IMettlerScale scale;

    public WeightFunctionality(IMettlerScale scale){
        this.scale = scale;
    }

    @Override
    public String requestInput(String msg){
        return scale.requestUserInput(msg).split("\"")[1];
    }

    @Override
    public boolean getConfirmation(String msg) {
        String returnString = scale.requestUserInput(msg);
        return returnString.split(" ")[1].equals("A"); // we automatically return false
    }

    @Override
    public void taraWeight(){
        scale.taraWeight();
    }

    @Override
    public String getWeight(){
        return scale.getWeight();
    }

}
