package common.option;

import common.input.InputStrategy;
import domain.pos.validation.InputValidationChain;

public class OptionContext {

    private static final String ERROR_PREFIX = "[ERROR] ";

    public <T> T workWithOptionStrategy(OptionStrategy<T> optionStrategy) {
        T result = null;
        while (result == null) {
            try {
                result = optionStrategy.executeOption();
            } catch (IllegalArgumentException e) {
                System.out.println(ERROR_PREFIX + e.getMessage());
            }
        }
        return result;
    }
}
