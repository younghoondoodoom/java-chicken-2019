package domain.pos.validation.validator;

import domain.pos.dto.InputValidationRequest;
import domain.pos.dto.InputValidationResponse;
import domain.pos.type.ValidationType;
import domain.pos.validation.InputValidationChain;

public class NullOrBlankValidateChain implements InputValidationChain {

    private InputValidationChain next;
    private static final String NULL_OR_BLANK_MESSAGE = "빈 값은 입력할 수 없습니다.";

    @Override
    public void setNext(InputValidationChain next) {
        this.next = next;
    }

    @Override
    public InputValidationResponse validate(InputValidationRequest request) {
        if (!request.getValidationTypes().contains(ValidationType.NULL_OR_BLANK)) {
            return next.validate(request);
        }
        if (request.getTarget() == null || request.getTarget().isBlank()) {
            return new InputValidationResponse(false, NULL_OR_BLANK_MESSAGE);
        }
        return next.validate(request);
    }
}
