package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class Generator {
    private static final String TARGET_FOLDER = "app/src/main/java/";
    private static final int SCHEMA_VERSION = 1;

    public static final void main(String[] args) throws Throwable {
        Schema schema = new Schema(SCHEMA_VERSION, "at.technikum.se14m009.generated");
        // we are generating the schema here

        // defining the Person entity
        Entity movieItem = schema.addEntity("MovieItem");
        movieItem.setTableName("MovieItem"); // note that we can actually manually provide a table name!
        //person.addIdProperty();
        movieItem.addStringProperty("Title");
        movieItem.addStringProperty("Runtime");
        movieItem.addStringProperty("Year");
        movieItem.addStringProperty("Poster");
        movieItem.addStringProperty("imdbID");

        Entity searchResult = schema.addEntity("SearchResult");
        searchResult.setTableName("SearchResult"); // note that we can actually manually provide a table name!
        //searchResult.addIdProperty();
        searchResult.addStringProperty("SearchTerm");

        new DaoGenerator().generateAll(schema, TARGET_FOLDER);
    }
}
