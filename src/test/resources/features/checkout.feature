# language: pt
Funcionalidade: Checkout
  Como cliente autenticado do My Demo App
  Quero finalizar a compra dos itens do carrinho
  Para receber meus produtos em casa

  @smoke
  Cenário: Compra completa de ponta a ponta
    Dado que o usuário está logado
    E o usuário adiciona o primeiro produto ao carrinho
    Quando o usuário inicia o checkout a partir do carrinho
    E o usuário informa o endereço de entrega válido
    E o usuário informa um cartão de pagamento válido
    E o usuário revisa e confirma o pedido
    Então a tela de pedido concluído é exibida
    E continuar comprando leva de volta ao catálogo

  @regression
  Cenário: Checkout exige autenticação
    Dado que o app está aberto no catálogo
    Quando o usuário adiciona o primeiro produto ao carrinho
    E o usuário inicia o checkout a partir do carrinho
    Então a tela de login é exibida

  @regression
  Cenário: Cartão inválido bloqueia o pagamento
    Dado que o usuário está logado
    E o usuário adiciona o primeiro produto ao carrinho
    Quando o usuário inicia o checkout a partir do carrinho
    E o usuário informa o endereço de entrega válido
    E o usuário informa um cartão de pagamento inválido
    E o usuário tenta revisar o pedido
    Então o campo de cartão exibe uma mensagem de erro
