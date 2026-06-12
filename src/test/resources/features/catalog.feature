# language: pt
Funcionalidade: Catálogo
  Como cliente do My Demo App
  Quero navegar e ordenar o catálogo de produtos
  Para encontrar itens que desejo comprar

  @smoke
  Cenário: Catálogo lista produtos ao abrir o app
    Dado que o app está aberto no catálogo
    Então pelo menos um produto é listado

  @regression
  Cenário: Detalhes do produto exibem preço e adicionar ao carrinho
    Dado que o app está aberto no catálogo
    Quando o usuário abre o primeiro produto
    Então o preço do produto é exibido
    E o botão de adicionar ao carrinho é exibido

  @regression
  Esquema do Cenário: Ordenação do catálogo
    Dado que o app está aberto no catálogo
    Quando o usuário ordena o catálogo por "<critério>"
    Então os produtos são exibidos em ordem "<ordem>"

    Exemplos:
      | critério          | ordem                |
      | preço crescente   | crescente de preço   |
      | preço decrescente | decrescente de preço |
      | nome crescente    | alfabética           |
      | nome decrescente  | alfabética inversa   |
