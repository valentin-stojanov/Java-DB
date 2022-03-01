import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class _05_EmployeeFromDepartment {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        String department = "Research and Development";

        List<Employee> employeeList = entityManager.createQuery("SELECT e FROM Employee e WHERE e.department.name = :dep ORDER BY e.salary ASC, e.id ASC", Employee.class)
                .setParameter("dep", department)
                .getResultList();

        for (Employee employee : employeeList) {
            System.out.println(employee.toString());
        }

        entityManager.getTransaction().commit();
    }
}
