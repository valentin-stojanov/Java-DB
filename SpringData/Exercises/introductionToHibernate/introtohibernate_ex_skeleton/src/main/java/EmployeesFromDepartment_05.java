import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EmployeesFromDepartment_05 {
    public static void main(String[] args) {

        EntityManager entityManager = Persistence
                .createEntityManagerFactory("PU_Name")
                .createEntityManager();

        entityManager.createQuery("SELECT e FROM Employee e " +
                        "WHERE e.department.name = 'Research and Development' " +
                        "ORDER BY e.salary, e.id", Employee.class)
                .getResultStream()
                .forEach(e ->
                 System.out.printf("%s %s from %s - $%.2f%n",
                 e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));
    }
}
