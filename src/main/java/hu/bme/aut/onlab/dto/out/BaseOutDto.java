package hu.bme.aut.onlab.dto.out;

public class BaseOutDto {

    private boolean error;

    public BaseOutDto() {
        error = false;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
