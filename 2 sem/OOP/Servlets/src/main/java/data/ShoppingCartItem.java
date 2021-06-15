package data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartItem {
    private int userId;
    private int productId;
    private int quantity;

    public ShoppingCartItem(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
