import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class _13_RemoveTowns {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
        String townName = scanner.nextLine();

        Integer addressId = entityManager.createQuery("select a.id from Address a where a.town.name = :townName", Integer.class)
                .setParameter("townName", townName)
                .getSingleResult();

        entityManager.createQuery("delete from Town a where a.name = :townName")
                .setParameter("townName", townName)
                .executeUpdate();

        int deletedAddresses = entityManager.createQuery("DELETE from Address a where a.id = :addressId")
                .setParameter("addressId", addressId)
                .executeUpdate();

        System.out.println(deletedAddresses);
        entityManager.getTransaction().commit();
    }
}
