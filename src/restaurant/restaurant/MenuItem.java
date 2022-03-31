package restaurant;

public class MenuItem {
    //fields
    private Double price;
    private String description;
    private String category;
    private Boolean isNew;

    //getters
    public Double getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }
    public String getCategory() {
        return category;
    }
    public Boolean getNew() {
        return isNew;
    }

    //setters
    public void setPrice(Double aPrice) {
        this.price = aPrice;
    }
    public void setDescription(String aDescription) {
        this.description = aDescription;
    }
    public void setCategory(String aCategory) {
        this.category = aCategory;
    }
    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    //constructors
    public MenuItem(Double price, String description, String category, Boolean isNew) {
        this.price = price;
        this.description = description;
        this.category = category;
        this.isNew = isNew;
    }

    //overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return description.equals(menuItem.description);
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
