# language: pt
Funcionalidade: Detalhes do produto
  Como cliente do My Demo App
  Quero ajustar a quantidade de um produto
  Para comprar mais de uma unidade de uma vez

  @regression
  Cenário: Contador de quantidade aumenta e diminui
    Dado que o usuário está na tela do primeiro produto
    Quando o usuário aumenta a quantidade 2 vezes
    E o usuário diminui a quantidade 1 vez
    Então o contador de quantidade mostra "2"

  @regression
  Cenário: Adicionar ao carrinho com quantidade maior que um
    Dado que o usuário está na tela do primeiro produto
    Quando o usuário adiciona o produto ao carrinho com quantidade 2
    Então o badge do carrinho mostra "2"
