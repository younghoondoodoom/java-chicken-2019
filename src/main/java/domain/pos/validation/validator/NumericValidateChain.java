package domain.pos.validation.validator;

import domain.pos.dto.InputValidationRequest;
import domain.pos.dto.InputValidationResponse;
import domain.pos.type.ValidationType;
import domain.pos.validation.InputValidationChain;

public class NumericValidateChain implements InputValidationChain {

    private InputValidationChain next;
    private static final String NOT_NUMERIC_VALUE_MESSAGE = "숫자만 입력 가능합니다.";

    @Override
    public void setNext(InputValidationChain next) {
        this.next = next;
    }

    @Override
    public InputValidationResponse validate(InputValidationRequest request) {
        if (!request.getValidationTypes().contains(ValidationType.NUMERIC)) {
            return next.validate(request);
        }
        if (!isNumeric(request.getTarget())) {
            return new InputValidationResponse(false, NOT_NUMERIC_VALUE_MESSAGE);
        }
        return next.validate(request);
    }

    private boolean isNumeric(final String target) {
        return !target.isBlank() && target.chars().allMatch(Character::isDigit);
    }
}
