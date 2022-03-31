package restaurant;

import java.util.Objects;

public class MenuItem {
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public MenuItem(Double price, String description, String category, Boolean isNew) {
        this.price = price;
        this.description = description;
        this.category = category;
        this.isNew = isNew;
    }

    private Double price;
    private String description;
    private String category;
    private Boolean isNew;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return description.equals(menuItem.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, description, category, isNew);
    }

    @Override
    public String toString() {
        return "MenuItem:" +
                "\nPrice: " + price +
                "\nDescription: " + description +
                "\nCategory: " + category +
                "\nNew: " + isNew;
    }
}
