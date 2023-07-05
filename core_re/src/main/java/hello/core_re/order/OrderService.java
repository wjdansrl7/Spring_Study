package hello.core_re.order;

public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
