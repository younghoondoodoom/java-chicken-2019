package domain.pos.controller;

import common.input.InputContext;
import common.input.InputStrategy;
import common.option.OptionContext;
import common.option.OptionStrategy;
import domain.pos.dto.InputValidationRequest;
import domain.pos.dto.InputValidationResponse;
import domain.pos.dto.OrderHistoryResponse;
import domain.pos.repository.MenuRepositoryImpl;
import domain.pos.repository.TableRepository;
import domain.pos.repository.TableRepositoryImpl;
import domain.pos.service.PosService;
import domain.pos.type.PayType;
import domain.pos.type.ValidationType;
import domain.pos.validation.InputValidationChain;
import java.util.List;
import view.InputView;
import view.OutputView;

public class PosController {

    private final PosService posService;
    private final InputValidationChain validator;
    private final InputContext inputContext;
    private final OptionContext optionContext;

    public PosController(PosService posService, InputValidationChain validator, InputContext inputContext,
        OptionContext optionContext) {
        this.posService = posService;
        this.validator = validator;
        this.inputContext = inputContext;
        this.optionContext = optionContext;
    }

    public void run() {
        while (true) {
            int mainOption = startMain();
            if (mainOption == 3) {
                OutputView.printExit();
                break;
            }
            if (mainOption == 1) {
                optionContext.workWithOptionStrategy(this::order);
            }
            if (mainOption == 2) {
                optionContext.workWithOptionStrategy(() -> {
                    int result = pay();
                    OutputView.printFinalPrice(result);
                    return result;
                });
            }
        }
    }

    private int startMain() throws IllegalArgumentException {
        OutputView.printMain();
        System.out.println();
        return getMainOption();
    }

    private boolean order() {
        int tableNumber = selectTable();
        int menuNumber = selectMenu();
        int menuQuantity = getMenuQuantity();
        return posService.order(tableNumber, menuNumber, menuQuantity);
    }

    private int pay() {
        int tableNumber = selectTable();
        printOrderHistory(tableNumber);
        PayType payType = getPayType(tableNumber);
        return posService.pay(tableNumber, payType);
    }

    private void printOrderHistory(int tableNumber) {
        List<OrderHistoryResponse> orderHistories = posService.getOrderHistory(tableNumber);
        OutputView.printOrderHistory(orderHistories);
        System.out.println();
    }

    private int selectMenu() {
        OutputView.printMenus(MenuRepositoryImpl.menus());
        System.out.println();
        int menuNumber = getMenuNumber();
        System.out.println();
        return menuNumber;
    }

    private int selectTable() {
        OutputView.printTables(TableRepositoryImpl.tables());
        System.out.println();
        int tableNumber = getTableNumber();
        System.out.println();
        return tableNumber;
    }

    private PayType getPayType(int tableNumber) {
        return inputContext.workWithInputStrategy(validator, validator -> {
            String input = InputView.inputPayType(tableNumber);
            List<ValidationType> validationTypes = List.of(ValidationType.NUMERIC, ValidationType.NULL_OR_BLANK);
            InputValidationResponse response = validator.validate(new InputValidationRequest(validationTypes, input));
            if (!response.isValid()) {
                throw new IllegalArgumentException(response.getErrorMessage());
            }
            return PayType.findByNumber(input);
        });
    }

    private int getMainOption() {
        return inputContext.workWithInputStrategy(validator, validator -> {
            String input = InputView.inputMainOptionNumber();
            List<ValidationType> validationTypes = List.of(ValidationType.NUMERIC, ValidationType.MAIN_OPTION,
                ValidationType.NULL_OR_BLANK);
            return validateRequest(validator, new InputValidationRequest(validationTypes, input));
        });
    }

    private int getTableNumber() {
        return inputContext.workWithInputStrategy(validator, validator -> {
            String input = InputView.inputTableNumber();
            List<ValidationType> validationTypes = List.of(ValidationType.NUMERIC, ValidationType.NULL_OR_BLANK);
            return validateRequest(validator, new InputValidationRequest(validationTypes, input));
        });
    }

    private int getMenuNumber() {
        return inputContext.workWithInputStrategy(validator, validator -> {
            String input = InputView.inputTableNumber();
            List<ValidationType> validationTypes = List.of(ValidationType.NUMERIC, ValidationType.NULL_OR_BLANK);
            return validateRequest(validator, new InputValidationRequest(validationTypes, input));
        });
    }

    private int getMenuQuantity() {
        return inputContext.workWithInputStrategy(validator, validator -> {
            String input = InputView.inputMenuQuantity();
            List<ValidationType> validationTypes = List.of(ValidationType.NUMERIC, ValidationType.NULL_OR_BLANK,
                ValidationType.MENU_QUANTITY);
            return validateRequest(validator, new InputValidationRequest(validationTypes, input));
        });
    }

    private static int validateRequest(InputValidationChain validator, InputValidationRequest request) {
        InputValidationResponse response = validator.validate(request);
        if (!response.isValid()) {
            throw new IllegalArgumentException(response.getErrorMessage());
        }
        return Integer.parseInt(request.getTarget());
    }
}
