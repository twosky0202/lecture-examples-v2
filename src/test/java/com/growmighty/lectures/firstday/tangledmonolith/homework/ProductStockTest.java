package com.growmighty.lectures.firstday.tangledmonolith.homework;

import com.growmighty.lectures.firstday.tangledmonolith.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [과제 DDD-2] 상품 재고 규칙 테스트 — 뼈대입니다. fail(...) 을 지우고 채우세요.
 */
class ProductStockTest {

    @Test
    @DisplayName("재고 안에서 차감하면 재고가 줄어든다")
    void 재고를_차감한다() {
        // given
        Product product = Product.create(
                1L, "상품", new BigDecimal("10000"), 10, "상품 설명"
        );

        // when
        product.decreaseStock(3);

        // then
        assertEquals(7, product.getStockQuantity());
    }

    @Test
    @DisplayName("재고보다 많이 차감하면 예외가 나고 재고는 그대로다")
    void 재고_부족이면_예외() {
        // given
        Product product = Product.create(
                1L, "상품", new BigDecimal("10000"), 10, "상품 설명"
        );

        // when & then
        assertThrows(
                IllegalStateException.class,
                () -> product.decreaseStock(11)
        );

        assertEquals(10, product.getStockQuantity());
    }

    @Test
    @DisplayName("0 이하의 수량은 차감할 수 없다")
    void 수량이_0_이하면_예외() {
        // given
        Product product = Product.create(
                1L, "상품", new BigDecimal("10000"), 10, "상품 설명"
        );

        // when & then
        assertThrows(
                IllegalStateException.class,
                () -> product.decreaseStock(-3)
        );

        assertEquals(10, product.getStockQuantity());
    }
}
