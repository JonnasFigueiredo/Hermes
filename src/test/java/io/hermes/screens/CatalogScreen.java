package io.hermes.screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.hermes.screens.components.NavBar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CatalogScreen extends BaseScreen {

    private static final By STORE_ITEM = AppiumBy.accessibilityId("store item");
    private static final By STORE_ITEM_TEXT = AppiumBy.accessibilityId("store item text");
    private static final By STORE_ITEM_PRICE = AppiumBy.accessibilityId("store item price");

    private final NavBar navBar;

    public CatalogScreen(AndroidDriver driver) {
        super(driver);
        this.navBar = new NavBar(driver);
    }

    public NavBar navBar() {
        return navBar;
    }

    public boolean isLoaded() {
        return isVisible(STORE_ITEM);
    }

    public int visibleProductCount() {
        return waitAllVisible(STORE_ITEM).size();
    }

    public List<String> visibleProductNames() {
        return waitAllVisible(STORE_ITEM_TEXT).stream()
                .map(WebElement::getText)
                .toList();
    }

    public ProductScreen openProductAt(int index) {
        List<WebElement> items = waitAllVisible(STORE_ITEM);
        items.get(index).click();
        return new ProductScreen(driver);
    }
}
