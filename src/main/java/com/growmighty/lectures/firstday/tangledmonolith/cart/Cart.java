package com.growmighty.lectures.firstday.tangledmonolith.cart;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    @Column(nullable = false)
    private Long userId;

    public static Cart create(Long userId) {
        Cart cart = new Cart();
        cart.userId = userId;
        return cart;
    }

    public void addItem(Long productId, int quantity) {
        // 수량 체크
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }


        CartItem existingItem = findItem(productId);

        // 같은 상품 수량 합산
        if (existingItem != null) {
            existingItem.addQuantity(quantity);
        } else { // 없는 상품의 수량 변경, 삭제
            CartItem newItem = CartItem.create(productId, quantity);
            this.items.add(newItem);
            newItem.assignCart(this);
        }

//        this.items.add(item);
//
//        if (item.getCart() != this) {
//            item.assignCart(this);
//        }
    }

    public void changeQuantity(Long productId, Integer quantity) {
        CartItem item = findItem(productId);

        if (item == null) {
            throw new IllegalArgumentException("장바구니에 해당 상품이 없습니다.");
        }

        item.changeQuantity(quantity);
    }

    public void removeItem(Long productId) {
        CartItem item = findItem(productId);

        if (item == null) {
            throw new IllegalArgumentException("장바구니에 해당 상품이 없습니다.");
        }

        this.items.remove(item);
    }

    public CartItem findItem(Long productId) {
        return items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public void clear() {
        this.items.clear();
    }
}
