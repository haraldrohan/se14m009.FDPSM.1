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
        Entity movie = schema.addEntity("MovieEntity");
        movie.setTableName("MovieEntity"); // note that we can actually manually provide a table name!
        //person.addIdProperty();
        movie.addStringProperty("Title");
        movie.addStringProperty("Runtime");
        movie.addStringProperty("Year");
        movie.addStringProperty("Poster");
        movie.addStringProperty("ImdbId");
        final Property searchId = movie.addLongProperty("SearchId").notNull().getProperty();

        Entity search = schema.addEntity("SearchEntity");
        search.setTableName("SearchEntity"); // note that we can actually manually provide a table name!
        search.addIdProperty();
        search.addStringProperty("SearchTerm");

        new DaoGenerator().generateAll(schema, TARGET_FOLDER);
    }
}
