# Callenge LiterAlura 

![image](https://github.com/user-attachments/assets/17d80e4e-bd57-458d-bb51-b0b76584f065)

Catálogo de Libros que ofrece 5 opciones de interacción con los usuarios (vía consola).
Los libros se buscan a través de una API y se guardan en una base de datos.

## Opciones del menú:
![image](https://github.com/user-attachments/assets/dbeead74-0849-4ef0-a691-08e0b23b39e1)

* Buscar libros por titulo
Realiza una consulta a la API externa para buscar un libro por su título.
Si el libro existe en la base de datos, informa que ya está registrado.
Si no está registrado, lo inserta en la base de datos junto con su autor (creando el autor si no existe previamente).

* Listar Libros registrados
Recupera y muestra todos los libros almacenados en la base de datos.

* Listar autores registrados
Recupera y muestra todos los autores registrados en la base de datos.

*Listar autores por año (autores que vicieron en determinada fecha)
Solicita un año al usuario y muestra los autores que estaban vivos durante ese año.

*Listar libros por idioma
Solicita un idioma al usuario y muestra los libros disponibles en ese idioma.
Utiliza la enumeración CategoriaIdioma para validar y mapear los idiomas.

![image](https://github.com/user-attachments/assets/d1e579fc-2871-44f7-af37-0e80942a2f60)


### Tecnologías

* Java
* Spring Data JPA
* JPA/Hibernate
* HTTP Client
* JSON Parsing
* API REST-Gutendex API
* PostgreSQL
