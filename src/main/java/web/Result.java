package web;

public class Result {

    private Object result;

    private int status;

    public Result(Object result, int status) {
        this.result = result;
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
