package domain.pos.validation.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.pos.dto.InputValidationRequest;
import domain.pos.dto.InputValidationResponse;
import domain.pos.type.ValidationType;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MenuQuantityValidateChainTest {

    private MenuQuantityValidateChain menuQuantityValidateChain = new MenuQuantityValidateChain();

    @Test
    public void validateSuccess() {
        //given
        menuQuantityValidateChain.setNext(new InputValidateSuccessChain());
        List<ValidationType> validationTypes = List.of(ValidationType.MENU_QUANTITY);
        String target = "200";
        InputValidationRequest request = new InputValidationRequest(validationTypes, target);

        //when
        InputValidationResponse response = menuQuantityValidateChain.validate(request);

        //then
        assertThat(response.isValid()).isTrue();
    }

    @Test
    public void validateOutOfRangeFailure() {
        //given
        menuQuantityValidateChain.setNext(new InputValidateSuccessChain());
        List<ValidationType> validationTypes = List.of(ValidationType.MENU_QUANTITY);
        String target = "1100";
        InputValidationRequest request = new InputValidationRequest(validationTypes, target);

        //when
        InputValidationResponse response = menuQuantityValidateChain.validate(request);

        //then
        assertThat(response.isValid()).isFalse();
    }

}