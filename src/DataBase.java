/*
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;
import java.util.Date;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

*/
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
 *//*

public class DataBase {
    private static MongoClient mongoClient = new MongoClient();
    private static MongoDatabase database = mongoClient.getDatabase("library");
    private static MongoCollection<Document> users = database.getCollection("users");
    private static MongoCollection<Document> documents = database.getCollection("documents");
    private static MongoCollection<Document> orders = database.getCollection("orders");

    */
/**
     * Add book to the DataBase,
     * throw Error if book already in db
     *
     * @param book that need to add
     *//*

    public static void addBook(Book book) {
        //TODO: first find if this book already in db

        Document docBook = new Document("title", book.getTitle())
                .append("authors", Arrays.asList(book.getAuthors()))
                .append("publisher", book.getPublisher())
                .append("edition", book.getEdition())
                .append("year", book.getYear());
        documents.insertOne(docBook);
    }

    public static Book getBook() {
        throw new Error("no such book");
    }

    public static void addUser(Patron patron) {
        //TODO: first find if this user already in db
        Document docUser = new Document("name", patron.getUsername())
                .append("password", patron.getPassword())
                .append("address", patron.getAddress())
                .append("phone", patron.getPhone())
                .append("isFaculty", patron.getFaculty());
        users.insertOne(docUser);
    }

    public static Patron getUser(String name) {
        throw new Error("no such user");
    }

    public static void doOrder(Patron patron, Book book, Date date) {
        Document docUser = users.find(eq("name", patron.getUsername())).first();
        Document docBook = documents.find(and(eq("title", book.getTitle()),
                eq("editon", book.getEdition()))).first();
        //TODO: test DBRefs
        Document docOrder = new Document("date", date.toString())
                .append("patron_id", docUser.get("_id"))
                .append("doc_id", docBook.get("_id"));
    }

    public static void doOrder(Patron patron, JournalArticle article) {

    }

    public static void doOrder(Patron patron, AudioVideo audioVideo) {

    }
}
*/
