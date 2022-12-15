package domain.pos.dto;

public class InputValidationResponse {
    private final boolean isValid;
    private String errorMessage;

    public InputValidationResponse(boolean isValid) {
        this.isValid = isValid;
    }

    public InputValidationResponse(boolean isValid, String errorMessage) {
        this.isValid = isValid;
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
