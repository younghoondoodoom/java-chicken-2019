package domain.pos.repository;

import domain.pos.model.Menu;
import java.util.Optional;

public interface MenuRepository {

    public Optional<Menu> findByNumber(int number);
}
