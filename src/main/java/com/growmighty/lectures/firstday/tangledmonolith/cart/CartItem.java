package com.growmighty.lectures.firstday.tangledmonolith.cart;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    public static CartItem create(Long productId, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.productId = productId;
        cartItem.quantity = quantity;
        return cartItem;
    }

    void addQuantity(int quantity) {
        checkQuantity(quantity);
        this.quantity += quantity;
    }

    void changeQuantity(int quantity) {
        checkQuantity(quantity);
        this.quantity = quantity;
    }

    void assignCart(Cart cart) {
        this.cart = cart;
    }

    private void checkQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("1개 이상의 상품을 담아주세요.");
        }
    }
}
