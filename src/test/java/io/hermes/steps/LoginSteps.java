package io.hermes.steps;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.hermes.core.DriverManager;
import io.hermes.model.User;
import io.hermes.pages.LoginPage;
import io.hermes.pages.components.NavBar;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {

    @Quando("o usuário faz login com credenciais válidas")
    public void oUsuarioFazLoginComCredenciaisValidas() {
        loginPage().loginAs(User.standard());
    }

    @Quando("o usuário faz login com {string} e {string}")
    public void oUsuarioFazLoginCom(String username, String password) {
        loginPage().loginAs(new User(username, password));
    }

    @Quando("o usuário envia o formulário de login vazio")
    public void oUsuarioEnviaOFormularioDeLoginVazio() {
        loginPage().submit();
    }

    @Quando("o usuário faz login apenas com o usuário {string}")
    public void oUsuarioFazLoginApenasComOUsuario(String username) {
        loginPage().loginWithUsernameOnly(username);
    }

    @Quando("o usuário faz logout pelo menu")
    public void oUsuarioFazLogoutPeloMenu() {
        new NavBar(DriverManager.getDriver()).logoutFromMenu();
    }

    @Entao("a mensagem de erro de login contém {string}")
    public void aMensagemDeErroDeLoginContem(String fragment) {
        String error = loginPage().genericErrorMessage();
        assertTrue(error.toLowerCase(Locale.ROOT).contains(fragment.toLowerCase(Locale.ROOT)),
                "A mensagem de erro deveria conter '" + fragment + "', mas foi: " + error);
    }

    @Entao("o campo de usuário exibe uma mensagem de erro")
    public void oCampoDeUsuarioExibeUmaMensagemDeErro() {
        assertFalse(loginPage().usernameFieldError().isBlank(),
                "O campo de usuário deveria exibir uma mensagem de erro");
    }

    @Entao("o campo de senha exibe uma mensagem de erro")
    public void oCampoDeSenhaExibeUmaMensagemDeErro() {
        assertFalse(loginPage().passwordFieldError().isBlank(),
                "O campo de senha deveria exibir uma mensagem de erro");
    }

    private LoginPage loginPage() {
        return new LoginPage(DriverManager.getDriver());
    }
}
