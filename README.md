# Projeto: Construção de uma API e execução de Testes Unitários

## Objetivo

- Execução de testes unitários em uma API REST
- Utilizar os principais frameworks de teste:
  - JUnits
  - Mockito
  - Hamcreast
- Criar uma funcionalidade utilizando o princípio de TDD (Desenvolvimento Guiado por Teste)

## Sobre o projeto e o que foi feito

- O projeto tratava-se de criar uma API REST para controle de estoque de cerveja.
- A princípio, foi criada uma API onde podemos cadastrar, encontrar uma ou todas as cervejas e deletar uma cerveja
- A ideia foi gerar uma API tradicional, onde após criada seria executado testes unitários em todas as funcionalidades da camada de Controller e Service
- Sobre os testes da camada Service
  - Cada serviço nessa camada pode trazer basicamente duas possibilidades: 
    - A primeira é executar o comando perfeitamente, conforme esperava-se. Ex: Criar uma cerveja
    - A segunda é trazer alguma exceção na funcionalidade, nesse caso relativo ao serviço, como por exemplo tentar cadastrar uma cerveja já existente.
      - OBS: Regras relativas ao preenchimento será visto na camada controller.
  - Os testes foram então feito fazendo simulações que chamassem essas possibilidades em todas as nossos serviços
- Sobre os testes na camada Controller
  - Aqui também foram realizados os testes simulando as possibilidade que cada funcionalidade poderia chamar, nesse caso o foco foi feito focando no Spring, verificando se os métodos como POST, GET e DELETE foram chamados com sucesso e entrando a nível de DTO.
- Por fim foi criado uma nova funcionalidade para incremento de quantidade de cerveja, usando o princípio do TDD (Teste Driven Development), visando praticar e aprender essa metodologia.
  - Foi usado o princípio de falhar e rescrever o teste, para então criar um teste mais simples que passe e então refatorar
  - Assim como os outros testes, esse foi feito a nível de Controller e Service
- Em breve devo estar voltando a esse projeto para criar um método de Decremento, visto que foi realizado apenas para o Incremento.