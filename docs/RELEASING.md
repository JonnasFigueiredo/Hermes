# Versões e validação

Estado atual: **1.0.0-SNAPSHOT**. O número do manifesto não comprova publicação.
Fonte de versão: `pom.xml`.

## Validar

```text
mvn -B test-compile
mvn -B test -Dcucumber.execution.dry-run=true
```

O dry-run valida compilação e bindings; não executa interações no dispositivo. Antes de uma release, execute os alvos anunciados na matriz de docs/COMPATIBILITY.md e registre os links dos runs. Se nem todos estiverem validados, declare essa limitação nas notas.

## Preparar uma release

1. Revise o diff e confirme que o commit contém apenas as mudanças previstas.
2. Atualize a versão nos manifestos aplicáveis e as notas de mudanças. Remova
   `-SNAPSHOT` somente ao preparar uma versão publicável. Para versões de teste,
   use um identificador de pré-release e marque essa condição na release.
3. Execute os checks acima e os testes de integração necessários. Registre
   limitações e plataformas não verificadas; não anuncie cobertura não executada.
4. Crie a tag `vMAJOR.MINOR.PATCH` no commit validado e associe os artefatos a ele.
   Tags já publicadas são imutáveis. Não crie versões retroativas só para preencher histórico.
5. Nas notas, informe mudanças, compatibilidade, instalação, migração e limitações.
   Publique somente depois de revisar o pacote e a documentação da mesma versão.

Correções compatíveis incrementam patch; recursos compatíveis incrementam minor;
quebras de contrato exigem revisão de major. Durante 0.x, declare explicitamente
mudanças incompatíveis e a política de suporte. Nem toda alteração de documentação
exige uma nova release de produto.
