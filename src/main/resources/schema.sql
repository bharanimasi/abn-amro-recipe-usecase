CREATE TABLE Recipes (
    id integer NOT NULL,
    recipeName VARCHAR NOT NULL,
    recipeInstructions VARCHAR NOT NULL,
    recipetype VARCHAR NOT NULL ,
    servingsNumber integer NOT NULL,
    ingredients VARCHAR,
    create_date TIMESTAMP,
    update_date TIMESTAMP
);