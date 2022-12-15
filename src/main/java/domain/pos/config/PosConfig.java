package domain.pos.config;

import common.input.InputContext;
import common.option.OptionContext;
import domain.pos.controller.PosController;
import domain.pos.repository.MenuRepositoryImpl;
import domain.pos.repository.TableRepositoryImpl;
import domain.pos.service.PosService;
import domain.pos.service.PosServiceImpl;
import domain.pos.validation.InputValidationChain;
import domain.pos.validation.validator.InputValidateSuccessChain;
import domain.pos.validation.validator.MainOptionValidateChain;
import domain.pos.validation.validator.MenuQuantityValidateChain;
import domain.pos.validation.validator.NullOrBlankValidateChain;
import domain.pos.validation.validator.NumericValidateChain;

public class PosConfig {

    public static PosController config() {
        return new PosController(posServiceDI(), makeValidationChain(), new InputContext(), new OptionContext());
    }

    private static PosService posServiceDI() {
        return new PosServiceImpl(new MenuRepositoryImpl(), new TableRepositoryImpl());
    }

    private static InputValidationChain makeValidationChain() {
        InputValidationChain nullOrBlankValidateChain = new NullOrBlankValidateChain();
        InputValidationChain numericValidateChain = new NumericValidateChain();
        InputValidationChain mainOptionValidateChain = new MainOptionValidateChain();
        InputValidationChain menuQuantityValidateChain = new MenuQuantityValidateChain();
        InputValidationChain inputValidateSuccessChain = new InputValidateSuccessChain();
        nullOrBlankValidateChain.setNext(numericValidateChain);
        numericValidateChain.setNext(mainOptionValidateChain);
        mainOptionValidateChain.setNext(menuQuantityValidateChain);
        menuQuantityValidateChain.setNext(inputValidateSuccessChain);
        return nullOrBlankValidateChain;
    }

}
