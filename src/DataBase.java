import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.push;
import static com.mongodb.client.model.Updates.set;

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
     * If document already in db, REPLACE it
     *
     * @param document what need to add
     */
    public static void addDoc(Document document) {
        // create unique id
        Object id = document.getTitle() + document.getAuthors();
        document.setDocument_id(id);

        String type = "document";
        if (document instanceof Book) type = "book";
        if (document instanceof AudioVideo) type = "av";
        if (document instanceof JournalArticle) type = "ja";

        // create Json document
        org.bson.Document docJson = new org.bson.Document("_id", id)
                .append("type", type)
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

        // check if already have in db this id
        Document check = getDoc(id);
        if (check != null) {
            documents.replaceOne(eq("_id", id), docJson);
        } else {
            documents.insertOne(docJson);
        }
    }

    /**
     * Get document in DataBase by id
     *
     * @param id of document
     * @return Document or null if not in DataBase
     */
    private static Document getDoc(Object id) {
        org.bson.Document docJson = documents.find(eq("_id", id)).first();

        // if not in DataBase
        if (docJson == null)
            return null;

        return jsonToDoc(docJson);
    }

    /**
     * Get all Document
     *
     * @return ArrayList with all documents
     */
    public static ArrayList<Document> getAllDoc() {
        ArrayList<Document> allDoc = new ArrayList<>();
        for (org.bson.Document json : documents.find()) {
            allDoc.add(jsonToDoc(json));
        }
        return allDoc;
    }

    /**
     * Parse org.bson.Document to class Document
     *
     * @param docJson that need to parse
     * @return new class Document
     */
    private static Document jsonToDoc(org.bson.Document docJson) {
        if (docJson.get("type").toString().equals("book")) {
            return  new Book (docJson.get("_id"), docJson.getString("title"),
                    docJson.getString("authors"), docJson.getInteger("price"),
                    docJson.getInteger("copies"), docJson.getBoolean("reference"),
                    docJson.getString("description"), docJson.getString("publisher"),
                    docJson.getInteger("edition"), docJson.getInteger("year"),
                    docJson.getBoolean("best-seller"));
        } else if (docJson.get("type").toString().equals("av")) {
            return new AudioVideo (docJson.get("_id"), docJson.getString("title"),
                    docJson.getString("authors"), docJson.getInteger("price"),
                    docJson.getInteger("copies"), docJson.getBoolean("reference"),
                    docJson.getString("description"), docJson.getString("publisher"),
                    docJson.getInteger("edition"), docJson.getInteger("year"),
                    docJson.getBoolean("best-seller"));
        } else if (docJson.get ("type").toString().equals("ja")) {
            return new JournalArticle (docJson.get("_id"), docJson.getString("title"),
                    docJson.getString("authors"), docJson.getInteger("price"),
                    docJson.getInteger("copies"), docJson.getBoolean("reference"),
                    docJson.getString("description"), docJson.getString("publisher"),
                    docJson.getInteger("edition"), docJson.getInteger("year"),
                    docJson.getBoolean("best-seller"));
        }
        return new Document (docJson.get("_id"), docJson.getString("title"),
                docJson.getString("authors"), docJson.getInteger("price"),
                docJson.getInteger("copies"), docJson.getBoolean("reference"),
                docJson.getString("description"), docJson.getString("publisher"),
                docJson.getInteger("edition"), docJson.getInteger("year"),
                docJson.getBoolean("best-seller"));
    }

    /**
     * Delete document from db
     *
     * @param id of document
     */
    public static void deleteDoc(Object id) {
        documents.deleteOne(eq("_id", id));
    }

    /**
     * Add user to DataBase
     * If document already in db, REPLACE it
     *
     * @param user what need to add
     */
    public static void addUser(User user) {
        // create unique id
        Object id = user.getUsername();

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

        // check if already have in DataBase this id
        User check = getUser(id);
        if (check != null) {
            users.replaceOne(eq("_id", id), userJson);
            deleteAllUserOrders(user);
        } else {
            users.insertOne(userJson);
        }

        // parse booking to order
        for (Booking booking : user.getBookings()) {
            doOrderWithBooking(user, booking);
        }
    }

    /**
     * Get user from DataBase
     *
     * @param id of user
     * @return User or null if not in DataBase
     */
    private static User getUser(Object id) {
        org.bson.Document userJson = users.find(eq("_id", id)).first();

        // if not in DataBase
        if (userJson == null) {
            return null;
        }

        return jsonToUser(userJson);
    }

    /**
     * Get all User
     *
     * @return ArrayList with all user
     */
    public static ArrayList<User> getAllUser() {
        ArrayList<User> allUser = new ArrayList<>();
        for (org.bson.Document json : users.find()) {
            allUser.add(jsonToUser(json));
        }
        return allUser;
    }

    /**
     * Parse org.bson.Document to class User
     *
     * @param userJson that need to parse
     * @return new class User
     */
    private static User jsonToUser(org.bson.Document userJson) {
        ArrayList<Booking> bookings = getAllOrderedDoc(userJson.get("_id"));
        User user = new User(userJson.get("_id"), userJson.getString("username"),
                userJson.getString("password"), userJson.getBoolean("isFaculty"),
                userJson.getString("firstName"), userJson.getString("secondName"),
                userJson.getString("address"), userJson.getString("phone"));
        user.setBookings(bookings);
        return user;
    }

    /**
     * Delete user from database
     *
     * @param id of user
     */
    public static void deleteUser(Object id) {
        users.deleteOne(eq("_id", id));
    }

    /**
     * Make order between user and document
     *
     * @param user     take doc
     * @param document what take
     */
    /*public static void doOrder(User user, Document document, int duration, boolean reqBuLib, boolean reqByUser) {
        // create unique id
        Object id = user.getUser_id();

        // find if user already took something
        org.bson.Document orderJson = orders.find(eq("_id", id)).first();
        if (orderJson == null) {
            //TODO: 3 DEADLINE TASK: start and end time
            orderJson = new org.bson.Document("_id", id)
                    .append("userId", user.getUser_id())
                    .append("documents", Arrays.asList(new org.bson.Document("_id", document.getDocument_id())
                            .append("reqByLib", reqBuLib)
                            .append("reqByUser", reqByUser)
                            .append("date", 0) // add date all date
                            .append("duration", duration)));
            orders.insertOne(orderJson);
        } else {
            orders.updateOne(
                    eq("_id", id),
                    push("documents",
                            new org.bson.Document("_id", document.getDocument_id())
                                    .append("reqByLib", reqBuLib)
                                    .append("reqByUser", reqByUser)
                                    .append("date", 0)// add date all date
                                    .append("duration", duration)));
        }
    }*/

    /**
     * Make order between user and document
     *
     * @param user     take doc
     * @param document what take
     */
    public static void doOrder(User user, Document document, int duration, boolean reqBuLib, boolean reqByUser) {
        // create unique id
        Object id = user.getUser_id();

        // find if user already took something
        org.bson.Document orderJson = orders.find(eq("_id", id)).first();
        if (orderJson == null) {
            //TODO: 3 DEADLINE TASK: start and end time
            orderJson = new org.bson.Document("_id", id)
                    .append("userId", user.getUser_id())
                    .append("documents", Arrays.asList(new org.bson.Document("_id", document.getDocument_id())
                            .append("reqByLib", reqBuLib)
                            .append("reqByUser", reqByUser)
                            .append("date", 0) // add date all date
                            .append("duration", duration)));
            orders.insertOne(orderJson);
        } else {
            boolean hasBook = false;
            org.bson.Document[] oldDoc = (org.bson.Document[]) orderJson.get("documents");
            org.bson.Document[] newDoc = new org.bson.Document[oldDoc.length - 1];
            // find book in order
            for (int i = 0; i < oldDoc.length; i++) {
                org.bson.Document doc = oldDoc[i];
                if (!(doc.get("_id") == document.getDocument_id())) {
                    newDoc[i] = doc;
                } else {
                    hasBook = true;
                }
            }

            if (!hasBook) {
                orders.updateOne(
                        eq("_id", id),
                        push("documents",
                                new org.bson.Document("_id", document.getDocument_id())
                                        .append("reqByLib", reqBuLib)
                                        .append("reqByUser", reqByUser)
                                        .append("date", 0)// add date all date
                                        .append("duration", duration)));
            } else {
                orders.updateOne(
                        eq("_id", id),
                        set("documents", newDoc));
            }
        }
    }

    /**
     * Do order with booking
     *
     * @param user    that need order
     * @param booking with book and date
     */
    private static void doOrderWithBooking(User user, Booking booking) {
        doOrder(user, booking.getDoc(), booking.getTimeLeft(), booking.hasRequestedByLib(), booking.hasRequestedByUser());
    }

    /**
     * Delete one user all orders
     *
     * @param user that order net delete
     */
    public static void deleteAllUserOrders(User user) {
        orders.deleteOne(eq("_id", user.getUser_id()));
    }

    /**
     * Delete book from orders
     *
     * @param user     that have order
     * @param document doc to delete from order
     */
    public static void deleteOrder(User user, Document document) {
        ArrayList<Booking> oldBookings = getAllOrderedDoc(user);
        deleteAllUserOrders(user);

        for (Booking old : oldBookings) {
            if (!old.getDoc().equals(document)) {
                doOrderWithBooking(user, old);
            }
        }

        user.setBookings(oldBookings);
    }

    /**
     * Get list of all order from one user
     *
     * @param user that order need get
     * @return ArrayList of all order
     */
    public static ArrayList<Booking> getAllOrderedDoc(User user) {
        // get order id
        Object id = user.getUser_id();
        return getAllOrderedDoc(id);
    }

    /**
     * Helper for getAllOrderedDoc, that found by id
     *
     * @param id of user
     * @return list of all order
     */
    private static ArrayList<Booking> getAllOrderedDoc(Object id) {
        // get order
        org.bson.Document orderJson = orders.find(eq("_id", id)).first();

        if (orderJson == null) {
            return new ArrayList<>();//no order book
        }

        ArrayList<Booking> bookings = new ArrayList<>();
        // make list of booking
        ArrayList list = (ArrayList) orderJson.get("documents");
        for (Object doc : list) {
            Object document_id = ((org.bson.Document) doc).get("_id");
            Document document = jsonToDoc(documents.find(eq("_id", document_id)).first());
            Booking booking = new Booking(document, ((org.bson.Document) doc).getInteger("duration"));
            if (((org.bson.Document) doc).getBoolean("reqByLib")) booking.libRequest();
            if (((org.bson.Document) doc).getBoolean("reqByUser")) booking.userRequest();
            bookings.add(booking);
        }
        return bookings;
    }

}