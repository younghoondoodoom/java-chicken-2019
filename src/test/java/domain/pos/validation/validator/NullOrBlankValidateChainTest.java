package domain.pos.validation.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.pos.dto.InputValidationRequest;
import domain.pos.dto.InputValidationResponse;
import domain.pos.type.ValidationType;
import java.util.List;
import org.junit.jupiter.api.Test;

class NullOrBlankValidateChainTest {
    private final NullOrBlankValidateChain nullOrBlankValidateChain = new NullOrBlankValidateChain();

    @Test
    public void validateSuccess() {
        //given
        nullOrBlankValidateChain.setNext(new InputValidateSuccessChain());
        List<ValidationType> validationTypes = List.of(ValidationType.NULL_OR_BLANK);
        String target = "123";
        InputValidationRequest request = new InputValidationRequest(validationTypes, target);

        //when
        InputValidationResponse response = nullOrBlankValidateChain.validate(request);

        //then
        assertThat(response.isValid()).isTrue();
    }

    @Test
    public void validateBlankFailure() {
        //given
        nullOrBlankValidateChain.setNext(new InputValidateSuccessChain());
        List<ValidationType> validationTypes = List.of(ValidationType.NULL_OR_BLANK);
        String target = " ";
        InputValidationRequest request = new InputValidationRequest(validationTypes, target);

        //when
        InputValidationResponse response = nullOrBlankValidateChain.validate(request);

        //then
        assertThat(response.isValid()).isFalse();
    }

    @Test
    public void validateNullFailure() {
        //given
        nullOrBlankValidateChain.setNext(new InputValidateSuccessChain());
        List<ValidationType> validationTypes = List.of(ValidationType.NULL_OR_BLANK);
        String target = null;
        InputValidationRequest request = new InputValidationRequest(validationTypes, target);

        //when
        InputValidationResponse response = nullOrBlankValidateChain.validate(request);

        //then
        assertThat(response.isValid()).isFalse();
    }
}