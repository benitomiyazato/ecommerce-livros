# ecommerce-livros
Ecommerce de Livros feito com Spring Framework (Boot, MVC, Data JPA), MySQL, Thymeleaf e Bootstrap.

O projeto foi desenvolvido utilizando Spring Boot para inicialização e configuração.
O Spring MVC juntamente do Thymeleaf + Bootstrap faz com que seja possível o mapeamento dos endpoints da aplicação, fazendo display de telas dinâmicas no front-end.
O MySQL aliado ao Spring Data JPA possibilitou a conexão com o banco de dados relacional.

##

O projeto ainda está em desenvolvimento, no momento possui apenas um painel administrativo, onde é possível fazer:
  - Cadastro de Autores:
    Autores podem ser cadastrados no sistema com Nome, uma breve biografia, data de nascimento e uma foto.
    
    ![image](https://user-images.githubusercontent.com/106701116/194637393-81144bdc-80cd-49e1-8e68-fb891c8d075b.png)

    ##
    
  - Cadastro de Gêneros:
    Gêneros podem ser cadastrados no sistema com nome e uma breve descrição, data de nascimento.
    
    ![image](https://user-images.githubusercontent.com/106701116/194637418-1a6cff7e-e776-4b85-8085-10760b7226f4.png)

    ##

  - Cadastro de Livros:
    Cada livro pode ter vários gêneros, porém um único autor.
    O livro terá: Título, preço, descrição, autor, gênero(s), data de publicação, quantidade em estoque e unidades vendidas. 
    Ao salvar um novo livro, você pode escolher em fazer upload de até três imagens dele.
    
    ![image](https://user-images.githubusercontent.com/106701116/194637348-6f858d39-456f-486f-b7b0-bcd354fc8107.png)

    ##
   
No painel administrativo, além de poder fazer o cadastro, você pode requisitar a listagem de autores/livros/gêneros, sendo que cada entidade tem uma página mais detalhada.

Listagem de Livros:
[foto virá aqui]

##

Página detalhada de um Livro:
[foto virá aqui]

##

Listagem de Autores:
[foto virá aqui]

##

Página detalhada de um Autor:
[foto virá aqui]

##

Listagem de Gêneros:
[foto virá aqui]

##

Página detalhada de um Gênero:
[foto virá aqui]
  
##
  
