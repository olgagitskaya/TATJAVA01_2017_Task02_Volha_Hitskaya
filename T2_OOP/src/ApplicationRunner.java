import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Volha_Hitskaya on 1/20/2017.
 */
public class ApplicationRunner {

    /*
    Use commands:
    goods - to see the list of available sport equipment;
    search itemName - to search goods in the shop;
    rent itemName1 [itemName2] [itemName3] - to rent goods;
    rentlist - to see the list of rented sport equipment;
    exit - to exit the application.
    */

    public static void main(String[] args) throws Exception
    {
        Shop shop = new Shop();
        shop.initializeFromFile();

        Scanner sc = new Scanner(System.in);
        while(true)
        {
            String command = sc.nextLine();
            if(command.equals("exit"))
            {
                break;
            }
            if(command.equals("goods"))
            {
                Map sportEquipmentList = shop.getListOfSportEquipment();
                for (Object key : sportEquipmentList.keySet())
                {
                    // had problems with type conversion as Map key had to be of SportEquipment type
                    int numberOfUnits = (Integer)sportEquipmentList.get(key);
                    SportEquipment se = (SportEquipment)key;
                    System.out.println(se.toString()+ ", Number of units: " + numberOfUnits);
                }

            }
            if(command.startsWith("search "))
            {
                String[] parts = command.split(" ");
                String title = parts[1];
                Map.Entry<SportEquipment, Integer> goodsEntry = shop.searchSportEquipment(title);
                if(goodsEntry != null)
                {
                    System.out.println(goodsEntry.getKey().toString()+ ", Number of units: " + goodsEntry.getValue());
                }
                else
                {
                    System.out.println("No such equipment available.");
                }
            }
            if(command.startsWith("rent "))
            {
                String[] parts = command.split(" ");
                if(parts.length > 4)
                {
                    System.out.println("You cannot rent more than 3 items at once.");
                    continue;
                }
                ArrayList<String> titles = new ArrayList<String>();
                for(int i=1; i<parts.length;i++)
                {
                    String title = parts[i];
                    titles.add(title);
                }
                RentUnit rentUnit = shop.createRent(titles);
                if (rentUnit == null)
                {
                    System.out.println("No equipment was ordered.");
                }
                else
                {
                    System.out.println(rentUnit);
                }

            }
            if(command.equals("rentlist"))
            {
                ArrayList<RentUnit> rentUnitsList = shop.getListOfRentUnits();
                for (RentUnit rentUnit: rentUnitsList)
                {
                    System.out.println(rentUnit);
                }

            }
        }
    }
}
