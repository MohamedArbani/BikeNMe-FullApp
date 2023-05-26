package ma.ac.emi.ginfo.restfull.entities;

import javax.persistence.*;

@Entity
public class CustomizedPricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;

    private Double customPrice = 0.0;

    public CustomizedPricing() {
    }

    public CustomizedPricing(double customPrice) {
        this.customPrice = customPrice;
    }

    public CustomizedPricing(int customPrice) {
        this.customPrice = (double) customPrice;
    }

    public CustomizedPricing(Bike bike, double customPrice) {
        this.bike = bike;
        this.customPrice = customPrice;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCustomPrice() {
        return customPrice;
    }

    public void setCustomPrice(double customPrice) {
        this.customPrice = customPrice;
    }
}