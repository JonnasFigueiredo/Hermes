package io.hermes.pages;

import io.appium.java_client.AppiumDriver;
import io.hermes.elements.CatalogElements;
import io.hermes.pages.components.NavBar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CatalogPage extends BasePage {

    private final NavBar navBar;

    public CatalogPage(AppiumDriver driver) {
        super(driver);
        this.navBar = new NavBar(driver);
    }

    public NavBar navBar() {
        return navBar;
    }

    public boolean isLoaded() {
        // "products screen" is present on both platforms and does not depend on the
        // product-card structure, which differs between Android and iOS.
        return isVisible(CatalogElements.SCREEN, DEFAULT_TIMEOUT);
    }

    public int visibleProductCount() {
        return waitAllPresent(CatalogElements.STORE_ITEM).size();
    }

    public List<String> visibleProductNames() {
        return textsOf(CatalogElements.STORE_ITEM_TEXT);
    }

    /** Prices currently on screen, parsed from texts like {@code $29.99}. */
    public List<Double> visibleProductPrices() {
        return textsOf(CatalogElements.STORE_ITEM_PRICE).stream()
                .map(price -> Double.parseDouble(price.replace("$", "").trim()))
                .toList();
    }

    public ProductPage openProductAt(int index) {
        List<WebElement> items = waitAllPresent(CatalogElements.STORE_ITEM);
        items.get(index).click();
        return new ProductPage(driver);
    }

    /** Opens the sort modal and picks one of the options. */
    public void sortBy(By sortOption) {
        tap(CatalogElements.SORT_BUTTON);
        tap(sortOption);
    }
}
