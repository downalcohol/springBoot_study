package study.community.exception;

public class CustomizeException extends RuntimeException {

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.errorMessage = errorCode.getMessage();
    }
}
