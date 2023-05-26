package ma.ac.emi.ginfo.restfull.entities;

import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private String subCategory;
    private String brand;
    private String model;
    private String description;
    private int mainPictureIndex = 0;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bike",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Picture> pictures = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bike",cascade = CascadeType.ALL)
    private List<CustomizedPricing> prices = new ArrayList<>();

    private double dailyDiscount;
    private double weeklyDiscount;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private double dailyPrice;
    private double stars;
    private int reviews;
    private boolean favourite;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    User bike_owner;

    @Column(name = "owner_id",insertable = false,updatable = false)
    private Long bike_owner_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User current_user;

    @Column(name = "user_id",insertable = false,updatable = false)
    private Long bike_current_user_id;

    @OneToMany(mappedBy = "bikeRentals",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rental> rentals = new ArrayList<>();

    @OneToMany(mappedBy = "bike",cascade = CascadeType.ALL)
    @JsonIgnore
    List<Notification> notifications = new ArrayList<>();

    private Boolean availability = true;

    public Bike() {
    }


    public Bike(String name, String category, String brand, String model, String description, List<Picture> pictures, Location location, double dailyPrice, double stars, boolean favourite, User bike_owner, User current_user, List<Rental> rentals, Boolean availability, double d, double w, int r) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.pictures = pictures;
        this.location = location;
        this.dailyPrice = dailyPrice;
        this.stars = stars;
        this.favourite = favourite;
        this.setOwnerById(bike_owner);
        this.current_user = current_user;
        this.rentals = rentals;
        this.availability = availability;
        this.dailyDiscount = d;
        this.weeklyDiscount = w;
        this.reviews = r;
    }

    public Bike(String name, String category, String subCategory, String brand, String model, String description, int mainPictureIndex, List<Picture> pictures, List<CustomizedPricing> prices, double dailyDiscount, double weeklyDiscount, Location location, double dailyPrice, double stars, int reviews, boolean favourite, User bike_owner, User current_user, List<Rental> rentals, Boolean availability) {
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.mainPictureIndex = mainPictureIndex;
        this.pictures = pictures;
        this.prices = prices;
        this.dailyDiscount = dailyDiscount;
        this.weeklyDiscount = weeklyDiscount;
        this.location = location;
        this.dailyPrice = dailyPrice;
        this.stars = stars;
        this.reviews = reviews;
        this.favourite = favourite;
        this.bike_owner = bike_owner;
        this.current_user = current_user;
        this.rentals = rentals;
        this.availability = availability;
    }

    public void removeRental(Rental rental){
        this.rentals.remove(rental);
    }

    public void removeNotification(Notification notification){
        this.notifications.remove(notification);
    }

    public void setOwnerById(User bike_owner) {
        bike_owner.add(this);
        this.bike_owner = bike_owner;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + mainPictureIndex + '\'' +
                ", discount=" + dailyDiscount +
                ", location=" + location +
                ", price=" + dailyPrice +
                ", stars=" + stars +
                ", favourite=" + favourite +
                ", bike_owner=" + bike_owner +
                ", current_user=" + current_user +
                ", availability=" + availability +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getBike_owner() {
        return bike_owner;
    }

    public void setBike_owner(User bike_owner) {
        this.bike_owner = bike_owner;
    }

    public User getCurrent_user() {
        return current_user;
    }

    public int getMainPictureIndex() {
        return mainPictureIndex;
    }

    public void setMainPictureIndex(int pictures) {
        this.mainPictureIndex = pictures;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setCurrent_user(User owner) {
        this.current_user = owner;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(double price) {
        this.dailyPrice = price;
    }


    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public double getDailyDiscount() {
        return dailyDiscount;
    }

    public void setDailyDiscount(double discount) {
        this.dailyDiscount = discount;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public double getWeeklyDiscount() {
        return weeklyDiscount;
    }

    public void setWeeklyDiscount(double weeklyDiscount) {
        this.weeklyDiscount = weeklyDiscount;
    }

    public List<CustomizedPricing> getPrices() {
        return prices;
    }

    public void setPrices(List<CustomizedPricing> prices) {
        this.prices = prices;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public Long getBike_owner_id() {
        return bike_owner_id;
    }

    public void setBike_owner_id(Long bike_owner_id) {
        this.bike_owner_id = bike_owner_id;
    }

    public Long getBike_current_user_id() {
        return bike_current_user_id;
    }

    public void setBike_current_user_id(Long bike_current_user_id) {
        this.bike_current_user_id = bike_current_user_id;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
