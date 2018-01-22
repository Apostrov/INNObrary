import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

/**
 * INFO:
 * All information in JSON:
 * {
    "name" : "MongoDB",
    "type" : "database",
    "count" : 1,
    "versions": [ "v3.2", "v3.0", "v2.6" ],
    "info" : { x : 203, y : 102 }
 }
 */
public class DataBase {
    private static MongoClient mongoClient = new MongoClient();
    private static MongoDatabase database = mongoClient.getDatabase("library");
    private static MongoCollection<Document> users = database.getCollection("users");
    private static MongoCollection<Document> documents = database.getCollection("documents");
    //TODO: understand how add reference to db
    private static MongoCollection<Document> orders = database.getCollection("orders");

    /**
     * Add book to the DataBase,
     * throw Error if book already in db
     * @param book that need to add
     */
    public static void addBook(Book book){
        //TODO: first find if this book already in db
        Document docBook = new Document("title", book.getTitle())
                .append("authors", Arrays.asList(book.getAuthors()))
                .append("publisher", book.getPublisher())
                .append("edition", book.getEdition())
                .append("year", book.getYear());
        documents.insertOne(docBook);
    }

    public static Book getBook(){
        throw new Error("no such book");
    }

    public static void addUser(Patron patron){
        //TODO: first find if this user already in db
        Document docUser = new Document("name", patron.getName())
                .append("password", patron.getPassword())
                .append("address", patron.getAddress())
                .append("phone", patron.getPhone())
                .append("isFaculty", patron.getFaculty());
        users.insertOne(docUser);
    }
}
