import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;

public class EmployeesMaximumSalaries_12 {
    public static void main(String[] args) {

        EntityManager entityManager =
                Persistence.createEntityManagerFactory("PU_Name")
                        .createEntityManager();

        List<Object[]> resultList = entityManager.createNativeQuery("SELECT d.name, MAX(e.salary) max FROM departments d\n" +
                        "JOIN employees e on d.department_id = e.department_id\n" +
                        "GROUP BY d.department_id\n" +
                        "HAVING max NOT BETWEEN 30000 AND 70000;")
                .getResultList();

        resultList.stream()
                .forEach(e ->
                        System.out.printf("%s %.2f%n", e[0],e[1]));

    }
}
