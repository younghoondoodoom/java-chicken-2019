package domain.pos.service;

import domain.pos.dto.OrderHistoryResponse;
import domain.pos.type.PayType;
import java.util.List;

public interface PosService {

    boolean order(int tableNumber, int menuNumber, int quantity);

    int pay(int tableNumber, PayType payType);

    List<OrderHistoryResponse> getOrderHistory(int tableNumber);
}
