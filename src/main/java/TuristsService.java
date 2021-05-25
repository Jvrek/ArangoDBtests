import models.Turist;

import java.util.List;
import java.util.Scanner;

public class TuristsService {

    private TuristRepositoryAdapter turistRepositoryAdapter;

    public TuristsService(TuristRepositoryAdapter turistRepositoryAdapter) {
        this.turistRepositoryAdapter = turistRepositoryAdapter;
    }

    public void add() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj klucz:");
        String key = scanner.nextLine();

        System.out.println("Podaj imie:");
        String name = scanner.nextLine();

        System.out.println("Podaj kwote do wydania:");
        Double money = scanner.nextDouble();

        System.out.println("Czy mozliwa podroz samolotem?:");
        boolean isFlying = scanner.nextBoolean();

        Turist turist = new Turist(name, money, isFlying);
        turistRepositoryAdapter.add(key, turist);

    }

    public Double findAveragePriceByMark(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Latajacy?");
        boolean isFlying = scanner.nextBoolean();

        return turistRepositoryAdapter.findAverageMoneyToSpend(isFlying);
    }

    public Turist searchByKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj klucz:");
        String key = scanner.nextLine();

        return turistRepositoryAdapter.getByKey(key);
    }

    public List<Turist> searchByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj imie:");
        String name = scanner.nextLine();

        return turistRepositoryAdapter.getByName(name);
    }

    public void delete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj klucz:");
        String key = scanner.nextLine();

        turistRepositoryAdapter.delete(key);
    }

    public void update() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj klucz:");
        String key = scanner.nextLine();


        System.out.println("Podaj imie:");
        String name = scanner.nextLine();

        System.out.println("Podaj kwote do wydania:");
        Double money = scanner.nextDouble();

        System.out.println("Czy mozliwa podroz samolotem?:");
        boolean isFlying = scanner.nextBoolean();

        Turist turist = new Turist(name, money, isFlying);
        turistRepositoryAdapter.updateDocument(key, turist);
    }
}
