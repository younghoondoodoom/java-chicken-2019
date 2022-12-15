package common.option;

public interface OptionStrategy<T> {

    T executeOption() throws IllegalArgumentException;
}
