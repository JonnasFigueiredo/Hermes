# language: pt
Funcionalidade: Login
  Como cliente do My Demo App
  Quero me autenticar com minhas credenciais
  Para comprar usando a minha conta

  @smoke
  Cenário: Credenciais válidas levam ao catálogo
    Dado que o usuário abre a tela de login
    Quando o usuário faz login com credenciais válidas
    Então o catálogo é exibido

  @regression
  Esquema do Cenário: Credenciais rejeitadas exibem mensagem de erro
    Dado que o usuário abre a tela de login
    Quando o usuário faz login com "<usuário>" e "<senha>"
    Então a mensagem de erro de login contém "<trecho da mensagem>"

    Exemplos:
      | usuário           | senha        | trecho da mensagem |
      | bob@example.com   | senha-errada | do not match       |
      | alice@example.com | 10203040     | locked             |

  @regression
  Cenário: Login sem credenciais exige o usuário
    Dado que o usuário abre a tela de login
    Quando o usuário envia o formulário de login vazio
    Então o campo de usuário exibe uma mensagem de erro

  @regression
  Cenário: Login sem senha exige a senha
    Dado que o usuário abre a tela de login
    Quando o usuário faz login apenas com o usuário "bob@example.com"
    Então o campo de senha exibe uma mensagem de erro

  @regression
  Cenário: Logout encerra a sessão
    Dado que o usuário está logado
    Quando o usuário faz logout pelo menu
    Então a tela de login é exibida
