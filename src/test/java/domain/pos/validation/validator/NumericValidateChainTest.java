package domain.pos.validation.validator;

import static org.assertj.core.api.Assertions.assertThat;

import domain.pos.dto.InputValidationRequest;
import domain.pos.dto.InputValidationResponse;
import domain.pos.type.ValidationType;
import java.util.List;
import org.junit.jupiter.api.Test;

class NumericValidateChainTest {

    private final NumericValidateChain numericValidateChain = new NumericValidateChain();

    @Test
    public void validateSuccess() {
        //given
        numericValidateChain.setNext(new InputValidateSuccessChain());
        List<ValidationType> validationTypes = List.of(ValidationType.NUMERIC);
        String target = "123";
        InputValidationRequest request = new InputValidationRequest(validationTypes, target);

        //when
        InputValidationResponse response = numericValidateChain.validate(request);

        //then
        assertThat(response.isValid()).isTrue();
    }

    @Test
    public void validateBlankFailure() {
        //given
        numericValidateChain.setNext(new InputValidateSuccessChain());
        List<ValidationType> validationTypes = List.of(ValidationType.NUMERIC);
        String target = "     ";
        InputValidationRequest request = new InputValidationRequest(validationTypes, target);

        //when
        InputValidationResponse response = numericValidateChain.validate(request);

        //then
        assertThat(response.isValid()).isFalse();
    }

    @Test
    public void validateStringFailure() {
        //given
        numericValidateChain.setNext(new InputValidateSuccessChain());
        List<ValidationType> validationTypes = List.of(ValidationType.NUMERIC);
        String target = "test";
        InputValidationRequest request = new InputValidationRequest(validationTypes, target);

        //when
        InputValidationResponse response = numericValidateChain.validate(request);

        //then
        assertThat(response.isValid()).isFalse();
    }
}