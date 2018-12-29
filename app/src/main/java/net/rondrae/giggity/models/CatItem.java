package net.rondrae.giggity.models;

public class CatItem {
    private String category,catUrl;

    public CatItem(String category, String catUrl) {
        this.category = category;
        this.catUrl = catUrl;
    }
    public CatItem(){

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCatUrl() {
        return catUrl;
    }

    public void setCatUrl(String catUrl) {
        this.catUrl = catUrl;
    }
}
