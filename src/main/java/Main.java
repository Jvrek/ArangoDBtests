import com.arangodb.ArangoDB;
import org.apache.log4j.BasicConfigurator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        BasicConfigurator.configure();
        ArangoDB arango = ArangoDBInitializer.getDefaultConnectionDB();
        ArangoDBInitializer.createDataBase("turist");
        ArangoDBInitializer.createCollection("turist", "turist");
        TuristRepositoryAdapter serviceRepo = new TuristRepositoryAdapter(arango, "turist", "turist");
        TuristsService turistsService = new TuristsService(serviceRepo);
        Scanner scanner = new Scanner(System.in);
        int choose;

        do {
            System.out.println("0-wyjdz\t\t1-dodaj\t\t2-usun\t\t3-wypisz\t\t4-aktualizuj\t\t5-Szukaj po imieniu\t\t6-Srednia kwot nie/latajacych");
            choose = scanner.nextInt();
            switch (choose) {
                case 0:
                    break;
                case 1:
                    turistsService.add();
                    break;
                case 2:
                    turistsService.delete();
                    break;
                case 3:
                    turistsService.searchByKey();
                    break;
                case 4:
                    turistsService.update();
                    break;
                case 5:
                    turistsService.searchByName();
                    break;
                case 6:
                    turistsService.findAveragePriceByMark();
                    break;
                default:
                    throw new IllegalStateException("Brak opcji: " + choose);
            }
        } while (choose != 0);

    }
}
