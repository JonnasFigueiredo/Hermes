package io.hermes.tests;

import io.hermes.core.BaseTest;
import io.hermes.screens.CatalogScreen;
import io.hermes.screens.ProductScreen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatalogTest extends BaseTest {

    @Test
    @Tag("smoke")
    @DisplayName("Catalog lists products on launch")
    void catalogListsProducts() {
        CatalogScreen catalog = new CatalogScreen(driver);
        assertTrue(catalog.isLoaded(), "Catalog should be displayed on launch");
        assertTrue(catalog.visibleProductCount() > 0, "Catalog should list at least one product");
    }

    @Test
    @Tag("regression")
    @DisplayName("Product details show price and add-to-cart")
    void productDetailsShowPriceAndAddToCart() {
        CatalogScreen catalog = new CatalogScreen(driver);
        ProductScreen product = catalog.openProductAt(0);

        assertTrue(product.isLoaded(), "Product details should be displayed");
        assertFalse(product.price().isBlank(), "Product details should show a price");
        assertTrue(product.hasAddToCartButton(), "Product details should offer add-to-cart");
    }
}
