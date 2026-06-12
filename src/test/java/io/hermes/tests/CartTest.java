package io.hermes.tests;

import io.hermes.core.BaseTest;
import io.hermes.screens.CartScreen;
import io.hermes.screens.CatalogScreen;
import io.hermes.screens.ProductScreen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CartTest extends BaseTest {

    @Test
    @Tag("smoke")
    @DisplayName("Adding a product fills the cart")
    void addingProductFillsCart() {
        CatalogScreen catalog = new CatalogScreen(driver);
        ProductScreen product = catalog.openProductAt(0);
        product.addToCart();

        assertEquals("1", product.navBar().cartBadgeText(), "Cart badge should show one item");

        product.navBar().openCart();
        CartScreen cart = new CartScreen(driver);
        assertEquals(1, cart.itemCount(), "Cart should contain exactly one item");
    }

    @Test
    @Tag("regression")
    @DisplayName("Removing the only item empties the cart")
    void removingItemEmptiesCart() {
        CatalogScreen catalog = new CatalogScreen(driver);
        ProductScreen product = catalog.openProductAt(0);
        product.addToCart();
        product.navBar().openCart();

        CartScreen cart = new CartScreen(driver);
        cart.removeFirstItem();

        assertTrue(cart.isEmpty(), "Cart should be empty after removing its only item");
    }
}
