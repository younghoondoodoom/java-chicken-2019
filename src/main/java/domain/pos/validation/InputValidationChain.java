package domain.pos.validation;

import domain.pos.dto.InputValidationRequest;
import domain.pos.dto.InputValidationResponse;

public interface InputValidationChain {

    void setNext(InputValidationChain next);

    InputValidationResponse validate(InputValidationRequest request);
}
