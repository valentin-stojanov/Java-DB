package SalesEntities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
public class Sale {
    private Integer id;
    private Product product;
    private Customer customer;
    private StoreLocation storeLocation;
    private LocalDateTime date;

    public Sale() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "product_id_custom_name", referencedColumnName = "id")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id_custom_name")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne
    @JoinColumn(name = "store_location_id_custom_name")
    public StoreLocation getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(StoreLocation storeLocation) {
        this.storeLocation = storeLocation;
    }

    @Column(name = "date")
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
