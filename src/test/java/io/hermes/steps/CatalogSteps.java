package io.hermes.steps;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.hermes.core.DriverManager;
import io.hermes.elements.CatalogElements;
import io.hermes.pages.CatalogPage;
import io.hermes.pages.ProductPage;
import org.openqa.selenium.By;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CatalogSteps {

    private static final Map<String, By> SORT_OPTIONS = Map.of(
            "preço crescente", CatalogElements.SORT_PRICE_ASCENDING,
            "preço decrescente", CatalogElements.SORT_PRICE_DESCENDING,
            "nome crescente", CatalogElements.SORT_NAME_ASCENDING,
            "nome decrescente", CatalogElements.SORT_NAME_DESCENDING);

    @Entao("pelo menos um produto é listado")
    public void peloMenosUmProdutoEListado() {
        assertTrue(catalogPage().visibleProductCount() > 0,
                "O catálogo deveria listar pelo menos um produto");
    }

    @Quando("o usuário abre o primeiro produto")
    public void oUsuarioAbreOPrimeiroProduto() {
        catalogPage().openProductAt(0);
    }

    @Entao("o preço do produto é exibido")
    public void oPrecoDoProdutoEExibido() {
        ProductPage product = new ProductPage(DriverManager.getDriver());
        assertTrue(product.isLoaded(), "Os detalhes do produto deveriam estar visíveis");
        assertFalse(product.price().isBlank(), "O preço do produto deveria ser exibido");
    }

    @Entao("o botão de adicionar ao carrinho é exibido")
    public void oBotaoDeAdicionarAoCarrinhoEExibido() {
        ProductPage product = new ProductPage(DriverManager.getDriver());
        assertTrue(product.hasAddToCartButton(), "O botão de adicionar ao carrinho deveria estar visível");
    }

    @Quando("o usuário ordena o catálogo por {string}")
    public void oUsuarioOrdenaOCatalogoPor(String criterion) {
        By option = SORT_OPTIONS.get(criterion);
        if (option == null) {
            throw new IllegalArgumentException("Critério de ordenação desconhecido: " + criterion);
        }
        catalogPage().sortBy(option);
    }

    @Entao("os produtos são exibidos em ordem {string}")
    public void osProdutosSaoExibidosEmOrdem(String order) {
        CatalogPage catalog = catalogPage();
        switch (order) {
            case "crescente de preço" ->
                    assertSorted(catalog.visibleProductPrices(), Comparator.naturalOrder(), order);
            case "decrescente de preço" ->
                    assertSorted(catalog.visibleProductPrices(), Comparator.reverseOrder(), order);
            case "alfabética" ->
                    assertSorted(catalog.visibleProductNames(), String.CASE_INSENSITIVE_ORDER, order);
            case "alfabética inversa" ->
                    assertSorted(catalog.visibleProductNames(), String.CASE_INSENSITIVE_ORDER.reversed(), order);
            default -> throw new IllegalArgumentException("Ordem desconhecida: " + order);
        }
    }

    private <T> void assertSorted(List<T> actual, Comparator<T> comparator, String order) {
        List<T> expected = actual.stream().sorted(comparator).toList();
        assertEquals(expected, actual, "Os produtos deveriam estar em ordem " + order);
    }

    private CatalogPage catalogPage() {
        return new CatalogPage(DriverManager.getDriver());
    }
}
