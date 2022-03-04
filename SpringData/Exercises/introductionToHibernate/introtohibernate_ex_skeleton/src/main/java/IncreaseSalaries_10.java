import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class IncreaseSalaries_10 {
    public static void main(String[] args) {

        EntityManager entityManager =
                Persistence.createEntityManagerFactory("PU_Name")
                        .createEntityManager();

         entityManager.getTransaction().begin();

         entityManager.createQuery("UPDATE Employee e " +
                        "SET e.salary = e.salary * 1.12 " +
                        "WHERE e.department.id IN (1, 2, 4, 11)")
                .executeUpdate();

        entityManager.getTransaction().commit();

        entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.department.id IN (1, 2, 4, 11)", Employee.class)
                .getResultStream()
                .forEach(e ->
                        System.out.printf("%s %s ($%.2f)%n",
                                e.getFirstName(),
                                e.getLastName(),
                                e.getSalary()));
    }
}
