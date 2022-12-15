package domain.pos.validation.validator;

import domain.pos.dto.InputValidationRequest;
import domain.pos.dto.InputValidationResponse;
import domain.pos.validation.InputValidationChain;

public class InputValidateSuccessChain implements InputValidationChain {

    @Override
    public void setNext(InputValidationChain next) {}

    @Override
    public InputValidationResponse validate(InputValidationRequest request) {
        return new InputValidationResponse(true);
    }
}
