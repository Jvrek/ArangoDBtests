import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import models.Turist;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public final class TuristRepositoryAdapter {

    private final ArangoDB arangoDB;
    private final String databaseName;
    private final String collectionName;

    public TuristRepositoryAdapter(ArangoDB arangoDB, String databaseName, String collectionName) {
        this.arangoDB = arangoDB;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    public void add(String key, Turist turist) {
        BaseDocument documentObject = new BaseDocument(key);
        documentObject.addAttribute("name", turist.getName());
        documentObject.addAttribute("moneyToSpend", turist.getMoneyToSpend().toString());
        documentObject.addAttribute("isFlying", turist.isFlying());

        try {
            arangoDB.db(databaseName).collection(collectionName).insertDocument(documentObject);
            System.out.println("\tDocument created");
        } catch (ArangoDBException e) {
            System.err.println("Failed to create document. " + e.getMessage());
        }
    }

    public Turist getByKey(String key) {
        try {
            BaseDocument myDocument = arangoDB.db(databaseName).collection(collectionName).getDocument(key, BaseDocument.class);

            String name = (String) myDocument.getAttribute("name");
            Double moneyToSpend = Double.valueOf(myDocument.getAttribute("moneyToSpend").toString());
            boolean isFlying = Boolean.valueOf(myDocument.getAttribute("isFlying").toString());
            Turist turist = new Turist(name, moneyToSpend, isFlying);
            System.out.println(turist);
            return turist;
        } catch (ArangoDBException e) {
            throw new ArangoDBException(e.getMessage());
        }
    }

    public void updateDocument(String key, Turist turist) {
        try {

            BaseDocument updatedDocument = arangoDB.db(databaseName).collection(collectionName).getDocument(key, BaseDocument.class);
            updatedDocument.updateAttribute("name", turist.getName());
            updatedDocument.updateAttribute("moneyToSpend", turist.getMoneyToSpend());
            updatedDocument.updateAttribute("isFlying", turist.isFlying());
            arangoDB.db(databaseName).collection(collectionName).updateDocument(key, updatedDocument);
        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
    }

    public void delete(String key) {
        try {
            arangoDB.db(databaseName).collection(collectionName).deleteDocument(key);
        } catch (ArangoDBException e) {
            System.err.println("Failed to delete document. " + e.getMessage());
        }
    }

    public List<Turist> getByName(String name) {
        List<Turist> turists = new LinkedList<>();
        try {
            String query = "FOR t IN turist FILTER t.name == @name RETURN t";
            Map<String, Object> bindedValues = Collections.singletonMap("name", name);
            ArangoCursor<BaseDocument> cursor = arangoDB.db(databaseName).query(query, bindedValues, null, BaseDocument.class);
            cursor.forEachRemaining(aDocument -> {
                String name2 = aDocument.getAttribute("name").toString();
                Double moneyToSpend = Double.valueOf(aDocument.getAttribute("moneyToSpend").toString());
                boolean isFlying = Boolean.valueOf(aDocument.getAttribute("isFlying").toString());
                Turist turist = new Turist(name2, moneyToSpend, isFlying);
                turists.add(turist);
            });
            System.out.println("Turysci o imieniu\t");
            for (Turist turist : turists) {
                System.out.println(turist);
            }
            return turists;
        } catch (ArangoDBException e) {
            System.err.println("Failed to execute query. " + e.getMessage());
        }
        return null;
    }

    public Double findAverageMoneyToSpend(boolean isFlying) {
        List<Turist> turists = new LinkedList<>();
        try {
            Map<String, Object> bindedValues = Collections.singletonMap("isFlying", isFlying);
            String query = "FOR t IN turist FILTER t.isFlying == @isFlying RETURN t";
            ArangoCursor<BaseDocument> cursor = arangoDB.db(databaseName).query(query, bindedValues, null, BaseDocument.class);
            cursor.forEachRemaining(aDocument -> {
                String name = aDocument.getAttribute("name").toString();
                Double moneyToSpend = Double.valueOf(aDocument.getAttribute("moneyToSpend").toString());
                boolean isFlying2 = Boolean.valueOf(aDocument.getAttribute("isFlying").toString());
                Turist turist = new Turist(name, moneyToSpend, isFlying2);
                turists.add(turist);
            });
        } catch (ArangoDBException e) {
            System.err.println("Failed to execute query. " + e.getMessage());
        }

        AtomicReference<Double> priceSum = new AtomicReference<>(0.0);
        turists.forEach(turist -> priceSum.updateAndGet(v -> v + turist.getMoneyToSpend()));
        Double average = priceSum.get() / turists.size();
        if (isFlying){
            System.out.println("Srednia ilośc pieniedzy do wydania przez turystow ktorzy latają " + " wynosi " + average + ".");
        }else{
            System.out.println("Srednia ilośc pieniedzy do wydania przez turystow ktorzy nie latają " + " wynosi " + average + ".");
        }

        return priceSum.get() / turists.size();
    }
}
