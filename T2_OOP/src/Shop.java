import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
 * Created by Volha_Hitskaya on 1/20/2017.
 */
public class Shop {
    private Map<String,Category> categories = new HashMap<String,Category>();
    private Map<SportEquipment, Integer> goods = new HashMap<SportEquipment, Integer>();
    private ArrayList<RentUnit> rentUnits = new ArrayList<RentUnit>();

    public Map<SportEquipment, Integer> getListOfSportEquipment()
    {
        return goods;

    }

    public Map.Entry<SportEquipment, Integer> searchSportEquipment(String title)
    {
        for (Map.Entry<SportEquipment, Integer> entry : this.goods.entrySet())
        {
            if (entry.getKey().getTitle().equals(title))
            {
                return (entry);
            }
        }
        return null;
    }

    public void initializeFromFile()throws Exception
    {
        String filePath = new File("src/init.xml").getAbsolutePath();
        File file = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        NodeList categoryList = document.getElementsByTagName("category");
        for(int i =0; i<categoryList.getLength();i++)
        {
            String categoryName = categoryList.item(i).getTextContent();
            //This class is used here only because it was indicated in the task as a property of the SportEquipment Class.
            Category category = new Category(categoryName);
            this.categories.put(categoryName,category);
        }
        NodeList equipmentList = document.getElementsByTagName("equipment");
        for(int i =0; i<equipmentList.getLength();i++)
        {
            String categoryName = equipmentList.item(i).getAttributes().getNamedItem("category").getNodeValue();
            String title = equipmentList.item(i).getAttributes().getNamedItem("title").getNodeValue();
            int price = Integer.parseInt(equipmentList.item(i).getAttributes().getNamedItem("price").getNodeValue());
            int numberOfUnits = Integer.parseInt(equipmentList.item(i).getAttributes().getNamedItem("numberOfUnits").getNodeValue());
            SportEquipment unit = new SportEquipment(this.categories.get(categoryName),title, price);
            this.goods.put(unit, numberOfUnits);
        }
    }

    public RentUnit createRent(ArrayList<String> titles)
    {
        ArrayList<SportEquipment> equipmentList = new ArrayList<SportEquipment>();
        String notOrdered = "";
        for(String title:titles)
        {
            Map.Entry<SportEquipment,Integer> searchResult = searchSportEquipment(title);
            if (searchResult != null)
            {
                int numberOfUnits = searchResult.getValue();
                if(numberOfUnits>0)
                {
                    searchResult.setValue(numberOfUnits-1);
                    equipmentList.add(searchResult.getKey());
                    continue;
                }
            }
            notOrdered += title + ",";
        }
        Date dateOfRent = new Date();
        RentUnit rentUnit = new RentUnit(equipmentList, dateOfRent);
        if (!notOrdered.isEmpty())
        {
            notOrdered = notOrdered.substring(0, notOrdered.length() - 1);
        }
        rentUnit.setEquipmentNotOrdered(notOrdered);
        if (equipmentList.size()>0)
        {
            rentUnits.add(rentUnit);
            return rentUnit;
        }
        return null;
    }

    public ArrayList<RentUnit>  getListOfRentUnits()
    {
        return this.rentUnits;
    }


}
