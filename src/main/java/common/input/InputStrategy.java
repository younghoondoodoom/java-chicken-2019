package common.input;

import domain.pos.validation.InputValidationChain;

public interface InputStrategy<T> {

    T executeInput(InputValidationChain validator) throws IllegalArgumentException;
}
