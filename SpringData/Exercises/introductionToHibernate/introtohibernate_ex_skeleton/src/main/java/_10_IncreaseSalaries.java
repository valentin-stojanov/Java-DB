import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class _10_IncreaseSalaries {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");



        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        List<Employee> employeeList = entityManager.createQuery("select e from Employee e where e.department.name in ('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class)
                .getResultList();

        StringBuilder output = new StringBuilder();

        for (Employee employee : employeeList) {
            employee.setSalary(employee.getSalary().multiply(BigDecimal.valueOf(1.12)));
            entityManager.persist(employee);
            output.append(String.format("%s %s ($%.2f)%n", employee.getFirstName(), employee.getLastName(), employee.getSalary()));
        }

        entityManager.getTransaction().commit();
        System.out.println(output);
    }
}
