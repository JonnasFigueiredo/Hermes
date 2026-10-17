# Compatibilidade e evidências

| Componente | Versão fixada |
|---|---|
| Java | 21 |
| Appium Server | 2.19.0 |
| UiAutomator2 | 4.2.9 |
| XCUITest | 9.10.5 |
| Aplicativo de demonstração | Sauce Labs My Demo App RN 1.3.0 |

As versões dos drivers declaram compatibilidade com Appium 2. Não use a instalação
sem versão, pois uma nova major pode exigir mudanças de servidor, drivers e SDKs.
As versões do cliente Java e das bibliotecas de teste estão no `pom.xml`.

| Alvo | Requisitos | Evidência |
|---|---|---|
| Android local | Android SDK, emulador/dispositivo, Appium + UiAutomator2 | Execução local do usuário |
| Android CI | Emuladores API 30 e 33 em Linux/KVM | [Mobile E2E Tests](https://github.com/JonnasFigueiredo/Hermes/actions/workflows/mobile-tests.yml) |
| iOS CI/local | macOS, Xcode, simulador, Appium + XCUITest | [iOS tests](https://github.com/JonnasFigueiredo/Hermes/actions/workflows/ios-tests.yml) |
| Grade Docker | Docker e imagem definida em docker/ | [Grid smoke](https://github.com/JonnasFigueiredo/Hermes/actions/workflows/grid-smoke.yml) |
| Dispositivos reais | Conta BrowserStack e credenciais | [Farm tests](https://github.com/JonnasFigueiredo/Hermes/actions/workflows/farm-tests.yml), execução opcional |

O workflow disponível não comprova execução em todos os alvos. Consulte o status,
commit, data e artefatos de cada run. Uma execução Android aprovada não valida iOS
ou BrowserStack. O relatório publicado pode representar um commit anterior.

Para instalar em macOS com iOS:

```bash
npm install -g appium@2.19.0
appium driver install xcuitest@9.10.5
```

Estado de distribuição: `1.0.0-SNAPSHOT` é desenvolvimento, não uma release estável.
Uma release deve fixar o commit validado e informar os alvos efetivamente executados.
