import entities.Employee;

import javax.persistence.*;
import java.util.List;

public class _05_EmployeeFromDepartment {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.department.name = 'Research and Development' ORDER BY e.salary, e.id", Employee.class);
        List<Employee> resultList = query.getResultList();

        for (Employee e : resultList) {
            System.out.printf("%s %s from %s - $%.2f%n", e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary());
        }


        entityManager.getTransaction().commit();
    }
}
