package io.hermes.steps;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.hermes.core.DriverManager;
import io.hermes.model.Address;
import io.hermes.model.PaymentCard;
import io.hermes.pages.CartPage;
import io.hermes.pages.CatalogPage;
import io.hermes.pages.CheckoutAddressPage;
import io.hermes.pages.CheckoutCompletePage;
import io.hermes.pages.CheckoutPaymentPage;
import io.hermes.pages.CheckoutReviewPage;
import io.hermes.pages.components.NavBar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutSteps {

    @Quando("o usuário inicia o checkout a partir do carrinho")
    public void oUsuarioIniciaOCheckoutAPartirDoCarrinho() {
        new NavBar(DriverManager.getDriver()).openCart();
        new CartPage(DriverManager.getDriver()).proceedToCheckout();
    }

    @Quando("o usuário informa o endereço de entrega válido")
    public void oUsuarioInformaOEnderecoDeEntregaValido() {
        CheckoutAddressPage addressPage = new CheckoutAddressPage(DriverManager.getDriver());
        assertTrue(addressPage.isLoaded(), "A tela de endereço deveria estar visível");
        addressPage.fillAndGoToPayment(Address.valid());
    }

    @Quando("o usuário informa um cartão de pagamento válido")
    public void oUsuarioInformaUmCartaoDePagamentoValido() {
        fillPayment(PaymentCard.valid());
    }

    @Quando("o usuário informa um cartão de pagamento inválido")
    public void oUsuarioInformaUmCartaoDePagamentoInvalido() {
        fillPayment(PaymentCard.withInvalidNumber());
    }

    @Quando("o usuário revisa e confirma o pedido")
    public void oUsuarioRevisaEConfirmaOPedido() {
        CheckoutReviewPage reviewPage = paymentPage().reviewOrder();
        assertTrue(reviewPage.isLoaded(), "A tela de revisão do pedido deveria estar visível");
        reviewPage.placeOrder();
    }

    @Quando("o usuário tenta revisar o pedido")
    public void oUsuarioTentaRevisarOPedido() {
        paymentPage().reviewOrder();
    }

    @Entao("a tela de pedido concluído é exibida")
    public void aTelaDePedidoConcluidoEExibida() {
        CheckoutCompletePage completePage = new CheckoutCompletePage(DriverManager.getDriver());
        assertTrue(completePage.isLoaded(), "A tela de pedido concluído deveria estar visível");
    }

    @Entao("continuar comprando leva de volta ao catálogo")
    public void continuarComprandoLevaDeVoltaAoCatalogo() {
        CatalogPage catalog = new CheckoutCompletePage(DriverManager.getDriver()).continueShopping();
        assertTrue(catalog.isLoaded(), "O catálogo deveria ser exibido após continuar comprando");
    }

    @Entao("o campo de cartão exibe uma mensagem de erro")
    public void oCampoDeCartaoExibeUmaMensagemDeErro() {
        assertFalse(paymentPage().cardNumberError().isBlank(),
                "O campo de cartão deveria exibir uma mensagem de erro");
    }

    private void fillPayment(PaymentCard card) {
        CheckoutPaymentPage paymentPage = paymentPage();
        assertTrue(paymentPage.isLoaded(), "A tela de pagamento deveria estar visível");
        paymentPage.fillCard(card);
    }

    private CheckoutPaymentPage paymentPage() {
        return new CheckoutPaymentPage(DriverManager.getDriver());
    }
}
