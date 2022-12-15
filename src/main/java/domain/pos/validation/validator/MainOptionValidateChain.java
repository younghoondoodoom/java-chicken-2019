package domain.pos.validation.validator;

import domain.pos.dto.InputValidationRequest;
import domain.pos.dto.InputValidationResponse;
import domain.pos.type.ValidationType;
import domain.pos.validation.InputValidationChain;
import domain.pos.validation.util.ValidateUtil;

public class MainOptionValidateChain implements InputValidationChain {

    private InputValidationChain next;
    private static final int[] EXPECTED_NUMBERS = new int[]{1, 2, 3};
    private static final String NOT_EXPECTED_NUMBER_MESSAGE = "1, 2, 3 중에 하나만 선택 가능합니다.";

    @Override
    public void setNext(InputValidationChain next) {
        this.next = next;
    }

    @Override
    public InputValidationResponse validate(InputValidationRequest request) {
        if (!request.getValidationTypes().contains(ValidationType.MAIN_OPTION)) {
            return next.validate(request);
        }
        if (!ValidateUtil.isExpectedNumber(Integer.parseInt(request.getTarget()), EXPECTED_NUMBERS)) {
            return new InputValidationResponse(false, NOT_EXPECTED_NUMBER_MESSAGE);
        }
        return next.validate(request);
    }
}
