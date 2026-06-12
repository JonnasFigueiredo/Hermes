package io.hermes.steps;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.hermes.core.DriverManager;
import io.hermes.pages.CartPage;
import io.hermes.pages.CatalogPage;
import io.hermes.pages.components.NavBar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartSteps {

    @Quando("o usuário adiciona o primeiro produto ao carrinho")
    public void oUsuarioAdicionaOPrimeiroProdutoAoCarrinho() {
        addProductToCart(1);
    }

    @Quando("o usuário adiciona o produto {int} ao carrinho")
    public void oUsuarioAdicionaOProdutoAoCarrinho(int oneBasedIndex) {
        addProductToCart(oneBasedIndex);
    }

    @Quando("o usuário remove o item do carrinho")
    public void oUsuarioRemoveOItemDoCarrinho() {
        new NavBar(DriverManager.getDriver()).openCart();
        cartPage().removeFirstItem();
    }

    @Quando("o usuário toca em continuar comprando")
    public void oUsuarioTocaEmContinuarComprando() {
        cartPage().goShopping();
    }

    @Entao("o badge do carrinho mostra {string}")
    public void oBadgeDoCarrinhoMostra(String expected) {
        NavBar navBar = new NavBar(DriverManager.getDriver());
        assertTrue(navBar.cartBadgeShows(expected),
                "O badge do carrinho deveria mostrar " + expected);
    }

    @Entao("o carrinho contém {int} item/itens")
    public void oCarrinhoContemItens(int expected) {
        new NavBar(DriverManager.getDriver()).openCart();
        assertEquals(expected, cartPage().itemCount(),
                "O carrinho deveria conter " + expected + " item(ns)");
    }

    @Entao("o carrinho está vazio")
    public void oCarrinhoEstaVazio() {
        assertTrue(cartPage().isEmpty(), "O carrinho deveria estar vazio");
    }

    private void addProductToCart(int oneBasedIndex) {
        new CatalogPage(DriverManager.getDriver())
                .openProductAt(oneBasedIndex - 1)
                .addToCart();
    }

    private CartPage cartPage() {
        return new CartPage(DriverManager.getDriver());
    }
}
