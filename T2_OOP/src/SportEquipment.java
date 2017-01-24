
/**
 * Created by Volha_Hitskaya on 1/20/2017.
 */
public class SportEquipment {
    private Category category;
    private String title;
    private int price;

    public SportEquipment(Category category, String title, int price){
        this.category = category;
        this.title = title;
        this.price = price;
    }

    public String toString()
    {
        return "Title: '" + this.title + "', Category: '" + this.category.categoryName + "', Price: '" + this.price + "'";
    }

    public String getTitle() {
        return title;
    }
}
