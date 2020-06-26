package br.com.udesc.mongodb;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import javax.swing.JOptionPane;

public class MongoConnection {

    private static final String HOST = "localhost";
    private static final int PORT = 27017;
    private static final String DB_NAME = "Biblioteca";

    private static MongoConnection uniqInstance;
    private static int mongoInstance = 1;

    private Mongo mongo;
    private DB db;
    private DBCollection coll;

    private MongoConnection() {
        //construtor privado
    }

    //garante sempre uma unica instancia
    public static synchronized MongoConnection getInstance() {
        if (uniqInstance == null) {
            uniqInstance = new MongoConnection();
        }
        return uniqInstance;
    }

    //garante um unico objeto mongo
    public DB getDB() {
        try {
            if (mongo == null) {
                mongo = new Mongo(HOST, PORT);
                db = mongo.getDB(DB_NAME);
                System.out.println("Mongo instance equals :> " + mongoInstance++);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        return db;
    }

    public DBCollection getCollection() {
        try {
            coll = db.getCollection("Livros");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        return coll;
    }

}
