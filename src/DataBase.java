
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;
import java.util.Date;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * INFO:
 * All information in JSON:
 * {
 * "name" : "MongoDB",
 * "type" : "database",
 * "count" : 1,
 * "versions": [ "v3.2", "v3.0", "v2.6" ],
 * "info" : { x : 203, y : 102 }
 * }
 */

public class DataBase {
    private static MongoClient mongoClient = new MongoClient();
    private static MongoDatabase database = mongoClient.getDatabase("library");
    private static MongoCollection<org.bson.Document> users = database.getCollection("users");
    private static MongoCollection<org.bson.Document> documents = database.getCollection("documents");
    private static MongoCollection<org.bson.Document> orders = database.getCollection("orders");

    /**
     * Add doc to the DataBase,
     * throw Error if book already in db
     *
     * @param document that need to add
     */

    public static Integer addDoc(Document document) {
        //TODO: first find if this book already in db
        Integer id = (document.getTitle() + document.getYear()).hashCode();
        org.bson.Document docJson = new org.bson.Document("_id", id)
                .append("title", document.getTitle())
                .append("description", document.getDescription())
                .append("publisher", document.getPublisher())
                .append("edition", document.getEdition())
                .append("year", document.getYear())
                .append("price", document.getPrice())
                .append("copies", document.getCopies())
                .append("reference", document.isReference())
                .append("best-seller", document.isBestSeller());
        documents.insertOne(docJson);
        return id;
    }

    public static Document getDoc() {
        throw new Error("no such book");
    }

    public static Integer addUser(Patron patron) {
        //TODO: first find if this user already in db
        Integer id = (patron.getUsername() + patron.getPassword()).hashCode();
        org.bson.Document userJson = new org.bson.Document("_id", id)
                .append("name", patron.getUsername())
                .append("password", patron.getPassword())
                .append("address", patron.getAddress())
                .append("phone", patron.getPhone())
                .append("isFaculty", patron.isFaculty());
        users.insertOne(userJson);
        return id;
    }

    public static Patron getUser(String name) {
        throw new Error("no such user");
    }

    public static void doOrder(Patron patron, Book book, Date date) {
        // TODO: rewrite for id
        org.bson.Document docUser = users.find(eq("name", patron.getUsername())).first();
        org.bson.Document docBook = documents.find(and(eq("title", book.getTitle()),
                eq("editon", book.getEdition()))).first();
        //TODO: test DBRefs
    }

    public static void doOrder(Patron patron, JournalArticle article) {

    }

    public static void doOrder(Patron patron, AudioVideo audioVideo) {

    }
}