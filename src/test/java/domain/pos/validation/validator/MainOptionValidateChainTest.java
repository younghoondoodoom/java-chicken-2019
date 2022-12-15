package domain.pos.validation.validator;

import static org.assertj.core.api.Assertions.assertThat;

import domain.pos.dto.InputValidationRequest;
import domain.pos.dto.InputValidationResponse;
import domain.pos.type.ValidationType;
import java.util.List;
import org.junit.jupiter.api.Test;

class MainOptionValidateChainTest {

    private final MainOptionValidateChain mainOptionValidateChain = new MainOptionValidateChain();

    @Test
    public void validateSuccess() {
        //given
        mainOptionValidateChain.setNext(new InputValidateSuccessChain());
        List<ValidationType> validationTypes = List.of(ValidationType.MAIN_OPTION);
        String target = "1";
        InputValidationRequest request = new InputValidationRequest(validationTypes,
            target);

        //when
        InputValidationResponse response = mainOptionValidateChain.validate(request);

        //then
        assertThat(response.isValid()).isTrue();
    }

    @Test
    public void validateNotExpectedNumberFailure() {
        //given
        mainOptionValidateChain.setNext(new InputValidateSuccessChain());
        List<ValidationType> validationTypes = List.of(ValidationType.MAIN_OPTION);
        String target = "4";
        InputValidationRequest request = new InputValidationRequest(validationTypes,
            target);

        //when
        InputValidationResponse response = mainOptionValidateChain.validate(request);

        //then
        assertThat(response.isValid()).isFalse();
    }
}