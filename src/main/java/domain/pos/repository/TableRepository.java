package domain.pos.repository;

import domain.pos.model.Table;
import java.util.Optional;

public interface TableRepository {

    public Optional<Table> findByNumber(int number);
}
