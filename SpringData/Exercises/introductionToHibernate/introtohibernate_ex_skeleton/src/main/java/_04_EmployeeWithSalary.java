import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class _04_EmployeeWithSalary {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        TypedQuery<String> query = entityManager
                .createQuery("SELECT e.firstName FROM Employee e WHERE e.salary > 50000", String.class);

        query.getResultList()
                .forEach(System.out::println);


        entityManager.getTransaction().commit();
    }
}
