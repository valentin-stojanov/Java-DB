import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class _06_AddingNewAddress {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        entityManager.getTransaction().begin();

        String text = "Vitoshka 15";

        Address address = new Address();
        address.setText(text);
        entityManager.persist(address);

        String lastName = scanner.nextLine();

        entityManager.createQuery("update Employee e set e.address = :address where e.lastName = :name")
                .setParameter("address", address)
                .setParameter("name", lastName)
                .executeUpdate();


        entityManager.getTransaction().commit();
    }
}
