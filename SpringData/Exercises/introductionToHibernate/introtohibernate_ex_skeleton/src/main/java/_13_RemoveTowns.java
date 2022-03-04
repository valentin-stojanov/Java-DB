import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class _13_RemoveTowns {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter town name:");
        String townName = scanner.nextLine();

        Town town = entityManager
                .createQuery("select t from Town t where t.name = :townName",
                        Town.class)
                .setParameter("townName", townName)
                .getSingleResult();

        int affectedRows = removeAddressesById(town.getId(), entityManager);

        entityManager.getTransaction().begin();
        entityManager.remove(town);
        entityManager.getTransaction().commit();

        System.out.printf("%d addresses in %s is deleted", affectedRows, townName);

    }

    private static int removeAddressesById(Integer id, EntityManager entityManager) {
        List<Address> addresses = entityManager
                .createQuery("SELECT a FROM Address a WHERE a.town.id = :townId", Address.class)
                .setParameter("townId", id)
                .getResultList();

        entityManager.getTransaction().begin();
        addresses.forEach(entityManager::remove);
        entityManager.getTransaction().commit();

        return addresses.size();
    }
}
