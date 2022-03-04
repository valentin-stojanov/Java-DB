import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class EmployeesSalaryOver_04 {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("PU_Name")
                .createEntityManager();

        entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.salary > :min_salary", Employee.class)
                .setParameter("min_salary", BigDecimal.valueOf(50000))
                .getResultStream()
                .forEach(e -> System.out.println(e.getFirstName()));
    }
}
