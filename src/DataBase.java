import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
    // connect to Database
    private static MongoClient mongoClient = new MongoClient();
    private static MongoDatabase database = mongoClient.getDatabase("library");
    private static MongoCollection<org.bson.Document> users = database.getCollection("users");
    private static MongoCollection<org.bson.Document> documents = database.getCollection("documents");
    private static MongoCollection<org.bson.Document> orders = database.getCollection("orders");

    /**
     * Add document to DataBase and set id to class
     * @param document what need to add
     */
    public static void addDoc(Document document) {
        Object id = (document.getTitle() + document.getYear()).hashCode();

        // check if already have in db this id
        Document check = getDoc(id);
        if (check != null){
            if(!check.getTitle().equals(document.getTitle())){
                // TODO: change id
            } else {
                // TODO: what to do if already have this id?
            }
        }

        document.setDocument_id(id);

        // create Json document
        org.bson.Document docJson = new org.bson.Document("_id", id)
                .append("title", document.getTitle())
                .append("authors", document.getAuthors())
                .append("description", document.getDescription())
                .append("publisher", document.getPublisher())
                .append("edition", document.getEdition())
                .append("year", document.getYear())
                .append("price", document.getPrice())
                .append("copies", document.getCopies())
                .append("reference", document.isReference())
                .append("best-seller", document.isBestSeller());
        documents.insertOne(docJson);
    }

    /**
     * Get document in DataBase by id
     * @param id of document
     * @return Document or null if not in DataBase
     */
    public static Document getDoc(Object id) {
        org.bson.Document docJson = documents.find(eq("_id", id)).first();

        // if not in DataBase
        if(docJson == null)
            return null;

        return new Document(docJson.getString("title"), docJson.getString("authors"),
                docJson.getInteger("price"), docJson.getInteger("copies"),
                docJson.getBoolean("reference"));
    }

    /**
     * Add user to DataBase
     * @param user what need to add
     */
    public static void addUser(User user) {
        Object id = user.getUsername();

        // check if already have in DataBase this id
        User check = getUser(id);
        if (check != null){
            // TODO: what to do if already have this id?
        }

        user.setUser_id(id);

        // create Json document
        org.bson.Document userJson = new org.bson.Document("_id", id)
                .append("username", user.getUsername())
                .append("password", user.getPassword())
                .append("address", user.getAddress())
                .append("firstName", user.getFirstName())
                .append("secondName", user.getSecondName())
                .append("phone", user.getPhone())
                .append("isFaculty", user.isFaculty());
        users.insertOne(userJson);
    }

    /**
     * Get user from DataBase
     * @param id of user
     * @return User or null if not in DataBase
     */
    public static User getUser(Object id) {
        org.bson.Document userJson = users.find(eq("_id", id)).first();

        // if not in DataBase
        if(userJson == null){
            return null;
        }

        return new User(userJson.getString("username"), userJson.getString("password"),
                userJson.getBoolean("isFaculty"), userJson.getString("firstName"),
                userJson.getString("secondName"), userJson.getString("address"),
                userJson.getString("phone"));
    }

    public static void doOrder(Patron patron, Document document) {
        // TODO: rewrite for id
        // DEADLINE 3
        //TODO: test DBRefs
    }
}