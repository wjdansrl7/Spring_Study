package hello.core2.order;

public interface OrderService {
    Order createOrder(Long memeberId, String itemName, int itemPrice);
}
