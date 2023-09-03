# Smarter Kühlschrank

Abschlussprojekt von Alexander, Sebastian und Benjamin.

## About

**Was kann ich kochen?**

Diese mehrmals täglich gestellte Frage soll die folgende App beantworten!

Füge Deinem Kühlschrank Zutaten hinzu, erstelle neue Rezepte und filtere danach
Deine Rezepte nach vorhandenen Zutaten.


## Installation

Führe folgende Befehle in einer Bash-kompatiblen _shell_ aus:
```bash
$ git clone --depth=1 ssh://git@space.it-frankfurt.com:2222/trainings/java/tempo-team/2023-1/recipe-app.git
$ cd backend
$ mvn install
$ cd ..
$ cd frontend
$ npm install
```

Um das Backend zu starten, führe folgende Befehle aus:
```bash
$ cd backend
$ mvn spring-boot:run
```

Und für das Frontend:
```bash
$ cd frontend
$ ng serve
```


## REST API Dokumentation

### `/recipes`

| Path                          | HTTP Method | Description                                               |
| ----------------------------- | ----------- | --------------------------------------------------------- |
| `/recipes`                    | GET         | Get a list of all recipes                                 |
| `/recipes?cookable=true`      | GET         | Get a list of cookable recipes                            |
| `/recipes/{id}`               | GET         | Get a recipe by ID                                        |
| `/recipes`                    | POST        | Add a new recipe                                          |
| `/recipes`                    | PUT         | Update a recipe                                           |
| `/recipes/{id}`               | DELETE      | Delete a recipe by ID                                     |
| `/recipes/cook/{id}`          | PUT         | Cook recipe with ID                                       |
| `/recipes/byProducts`         | POST        | Get a list of recipes containing a given list of products |

#### Example for POST/PUT `/recipes`

```json
{
    "name": "Spaghetti Bolognese",
    "instructions": "A very long description..."
    "imageUrl": "http://www.images.com/picture.jpg"
    "ingredients": [
        {
            "name": "Pasta",
            "amountInRecipe": 300,
            "unitOfMeasure": "GRAM",
        },
        {
            "name": "Tomatoes",
            "amountInRecipe": 500,
            "unitOfMeasure": "GRAM",
        },
        {
            "name": "Meat",
            "amountInRecipe": 200,
            "unitOfMeasure": "GRAM",
        }
    ],
}
```

### `/products`

| Path             | HTTP Method | Description                |
| ---------------- | ----------- | -------------------------- |
| `/products`      | GET         | Get a list of all products |
| `/products/{id}` | GET         | Get a product by ID        |
| `/products`      | POST        | Add a new product          |
| `/products`      | PUT         | Update a product           |
| `/products/{id}` | DELETE      | Delete a product by ID     |

#### Example for POST/PUT `/products`

```json
{
    "name": "Rice",
    "amountInStock": 3,
    "unitOfMeasure": "KILOGRAM",
    "expirationDate": "2023-10-23",
    "imageUrl": "http://www.images.com/picture.jpg"
}
```
