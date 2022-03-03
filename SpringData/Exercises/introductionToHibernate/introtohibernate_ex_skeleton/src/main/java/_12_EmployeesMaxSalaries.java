
import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class _12_EmployeesMaxSalaries {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        List<Department> departmentList = entityManager.createQuery("select d from Department d group by d.name", Department.class)
                .getResultList();

        StringBuilder output = new StringBuilder();
        BigDecimal lower = new BigDecimal(30000);
        BigDecimal upper = new BigDecimal(70000);

        for (Department department : departmentList) {
            Set<Employee> employees = department.getEmployees();

            Employee employee = employees.stream()
                    .max(Comparator.comparing(Employee::getSalary))
                    .orElse(null);

            BigDecimal salary = employee.getSalary();
            // Returns:
            //-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
            if (salary.compareTo(lower) < 0 || salary.compareTo(upper) > 0){
                output.append(String.format("%s %.2f%n", department.getName(), employee.getSalary()));
            }
        }

        entityManager.getTransaction().commit();
        System.out.println(output);
    }
}
