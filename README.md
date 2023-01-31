# ecommerce-livros
Ecommerce de Livros feito com Spring Framework (Boot, MVC, Data JPA), MySQL, Thymeleaf e Bootstrap.

O projeto foi desenvolvido utilizando Spring Boot.
Thymeleaf + Bootstrap no front-end. (no momento estou estudando Angular pra fazer uma refatoração no frontend)
O MySQL + Spring Data JPA para a conexão com o banco de dados.

##

O projeto ainda está em desenvolvimento, no momento possui um painel administrativo completo. Porém, a parte da loja virtual que será acessível pelo usuário ainda está incompleta.

No painel administrativo é possível fazer:
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
    
    ![image](https://user-images.githubusercontent.com/106701116/197298191-ebddfe7d-754e-4ad2-a0f0-8d6c262a1666.png)

    ##
    
   - Cadastro de Coleções:
     Uma coleção é um conjunto de dois ou mais livro que possuem similaridade, seja por autor, continuidade de histórias, etc. Geralmente fornecem um desconto relativo      à somatória dos preços unitários de cada livro componente desta coleção. Ao cadastrar uma coleção o admin poderá escolher entre colocar uma nova foto, ou utiliar      as mesmas fotos dos livros que compõem a coleção.
     
     ![image](https://user-images.githubusercontent.com/106701116/215895395-f699295b-c5c0-4801-bf73-e0915c497589.png)

     
     ##
    
No painel administrativo, além de poder fazer o cadastro, você pode requisitar a listagem de autores/livros/gêneros, sendo que cada entidade tem uma página mais detalhada.

Listagem de Livros:
![image](https://user-images.githubusercontent.com/106701116/196292343-23439c8c-f9a5-4c25-93be-7eaeafc69ed1.png)

##

Página detalhada de um Livro:
![image](https://user-images.githubusercontent.com/106701116/196292381-fc1d8798-d8b7-4280-9c1d-d53d34f4c92d.png)

##

Listagem de Autores:
![image](https://user-images.githubusercontent.com/106701116/196292407-223b0b5b-f9ed-4b27-ae4b-1afad9a8fc3f.png)

##

Página detalhada de um Autor:
![image](https://user-images.githubusercontent.com/106701116/196292430-adb954a7-f10e-4c0f-8ee3-06938333b8c1.png)

##

Listagem de Gêneros:
![image](https://user-images.githubusercontent.com/106701116/196292457-5033b010-b31f-46e9-a927-31aecf9193cd.png)

##

Página detalhada de um Gênero:
![image](https://user-images.githubusercontent.com/106701116/196292474-8725baf9-0d00-4d7f-9ff1-a08299ff3f4b.png)
  
##

Listagem de Coleções:
![image](https://user-images.githubusercontent.com/106701116/196292729-94ce4494-e56e-41e8-8a69-cc367ca72993.png)

##

Página detalhada de uma Coleção:
    ![image](https://user-images.githubusercontent.com/106701116/215896402-644d9187-2129-428c-be1d-8fe2cf23e118.png)

  
