import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Scanner;

public class FindEmployeesByFirstName_11 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EntityManager entityManager =
                Persistence.createEntityManagerFactory("PU_Name")
                        .createEntityManager();

        String pattern = scanner.nextLine();

        entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE lower(e.firstName) LIKE concat(:string, '%')", Employee.class)
                .setParameter("string", pattern.toLowerCase())
                .getResultStream()
                .forEach(e ->
                        System.out.printf("%s %s - %s - ($%.2f)%n",
                                e.getFirstName(),
                                e.getLastName(),
                                e.getJobTitle(),
                                e.getSalary()));
    }
}
