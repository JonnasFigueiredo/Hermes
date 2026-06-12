package io.hermes.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.hermes.core.DriverManager;
import io.hermes.model.User;
import io.hermes.pages.CatalogPage;
import io.hermes.pages.LoginPage;
import io.hermes.pages.components.NavBar;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Steps shared across features: app entry state, authentication and navigation.
 */
public class CommonSteps {

    @Dado("que o app está aberto no catálogo")
    public void queOAppEstaAbertoNoCatalogo() {
        CatalogPage catalog = new CatalogPage(DriverManager.getDriver());
        assertTrue(catalog.isLoaded(), "O catálogo deveria estar visível");
    }

    @Dado("que o usuário abre a tela de login")
    public void queOUsuarioAbreATelaDeLogin() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver()).open();
        assertTrue(loginPage.isLoaded(), "A tela de login deveria estar visível");
    }

    @Dado("que o usuário está logado")
    public void queOUsuarioEstaLogado() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver()).open();
        loginPage.loginAs(User.standard());
        CatalogPage catalog = new CatalogPage(DriverManager.getDriver());
        assertTrue(catalog.isLoaded(), "O catálogo deveria ser exibido após o login");
    }

    @Quando("o usuário abre o carrinho")
    public void oUsuarioAbreOCarrinho() {
        new NavBar(DriverManager.getDriver()).openCart();
    }

    @Quando("o usuário volta ao catálogo pelo menu")
    public void oUsuarioVoltaAoCatalogoPeloMenu() {
        new NavBar(DriverManager.getDriver()).openCatalogFromMenu();
    }

    @Entao("o catálogo é exibido")
    public void oCatalogoEExibido() {
        CatalogPage catalog = new CatalogPage(DriverManager.getDriver());
        assertTrue(catalog.isLoaded(), "O catálogo deveria ser exibido");
    }

    @Entao("a tela de login é exibida")
    public void aTelaDeLoginEExibida() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        assertTrue(loginPage.isLoaded(), "A tela de login deveria ser exibida");
    }
}
