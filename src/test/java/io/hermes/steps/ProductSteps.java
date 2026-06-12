package io.hermes.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.hermes.core.DriverManager;
import io.hermes.pages.CatalogPage;
import io.hermes.pages.ProductPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductSteps {

    @Dado("que o usuário está na tela do primeiro produto")
    public void queOUsuarioEstaNaTelaDoPrimeiroProduto() {
        ProductPage product = new CatalogPage(DriverManager.getDriver()).openProductAt(0);
        assertTrue(product.isLoaded(), "Os detalhes do produto deveriam estar visíveis");
    }

    @Quando("o usuário aumenta a quantidade {int} vez(es)")
    public void oUsuarioAumentaAQuantidade(int times) {
        ProductPage product = productPage();
        for (int i = 0; i < times; i++) {
            product.increaseQuantity();
        }
    }

    @Quando("o usuário diminui a quantidade {int} vez(es)")
    public void oUsuarioDiminuiAQuantidade(int times) {
        ProductPage product = productPage();
        for (int i = 0; i < times; i++) {
            product.decreaseQuantity();
        }
    }

    @Quando("o usuário adiciona o produto ao carrinho com quantidade {int}")
    public void oUsuarioAdicionaOProdutoAoCarrinhoComQuantidade(int quantity) {
        productPage().addToCartWithQuantity(quantity);
    }

    @Entao("o contador de quantidade mostra {string}")
    public void oContadorDeQuantidadeMostra(String expected) {
        assertEquals(expected, productPage().counterAmount(),
                "O contador de quantidade deveria mostrar " + expected);
    }

    private ProductPage productPage() {
        return new ProductPage(DriverManager.getDriver());
    }
}
