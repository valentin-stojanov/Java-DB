import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class _03_ContainsEmployee {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split(" ");

        Long result = entityManager.createQuery("SELECT count(e) FROM Employee e WHERE e.firstName = :firstName AND e.lastName = :lastName", Long.class)
                .setParameter("firstName", input[0])
                .setParameter("lastName", input[1])
                .getSingleResult();

        if (result > 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        entityManager.getTransaction().commit();
    }
}
