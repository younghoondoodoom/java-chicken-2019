package domain.pos.validation.validator;

import domain.pos.dto.InputValidationRequest;
import domain.pos.dto.InputValidationResponse;
import domain.pos.type.ValidationType;
import domain.pos.validation.InputValidationChain;
import domain.pos.validation.util.ValidateUtil;
import javax.xml.stream.events.EndDocument;

public class MenuQuantityValidateChain implements InputValidationChain {

    private InputValidationChain next;
    private static final String OUT_OF_RANGE_MESSAGE = "%s ~ %s개까지 입력할 수 있습니다.";
    private static final int START = 1;
    private static final int END = 1000;

    @Override
    public void setNext(InputValidationChain next) {
        this.next = next;
    }

    @Override
    public InputValidationResponse validate(InputValidationRequest request) {
        if (!request.getValidationTypes().contains(ValidationType.MENU_QUANTITY)) {
            return next.validate(request);
        }
        if (!ValidateUtil.isInRange(Integer.parseInt(request.getTarget()), START, END)) {
            return new InputValidationResponse(false, String.format(OUT_OF_RANGE_MESSAGE, START, END));
        }
        return next.validate(request);
    }
}
