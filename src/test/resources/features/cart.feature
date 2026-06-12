# language: pt
Funcionalidade: Carrinho
  Como cliente do My Demo App
  Quero gerenciar os itens do meu carrinho
  Para comprar somente o que eu realmente quero

  @smoke
  Cenário: Adicionar um produto enche o carrinho
    Dado que o app está aberto no catálogo
    Quando o usuário adiciona o primeiro produto ao carrinho
    Então o badge do carrinho mostra "1"
    E o carrinho contém 1 item

  @regression
  Cenário: Adicionar dois produtos diferentes ao carrinho
    Dado que o app está aberto no catálogo
    Quando o usuário adiciona o produto 1 ao carrinho
    E o usuário volta ao catálogo pelo menu
    E o usuário adiciona o produto 2 ao carrinho
    Então o badge do carrinho mostra "2"
    E o carrinho contém 2 itens

  @regression
  Cenário: Remover o único item esvazia o carrinho
    Dado que o app está aberto no catálogo
    Quando o usuário adiciona o primeiro produto ao carrinho
    E o usuário remove o item do carrinho
    Então o carrinho está vazio

  @regression
  Cenário: Carrinho vazio leva de volta às compras
    Dado que o app está aberto no catálogo
    Quando o usuário abre o carrinho
    E o usuário toca em continuar comprando
    Então o catálogo é exibido
