import domain.pos.config.PosConfig;
import domain.pos.model.Menu;
import domain.pos.repository.MenuRepositoryImpl;
import domain.pos.model.Table;
import domain.pos.repository.TableRepositoryImpl;
import view.InputView;
import view.OutputView;

import java.util.List;

public class Application {
    // TODO 구현 진행
    public static void main(String[] args) {
        PosConfig.config().run();
    }
}
