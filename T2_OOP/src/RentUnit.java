import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Volha_Hitskaya on 1/20/2017.
 */
public class RentUnit {
    private ArrayList<SportEquipment> units;
    private Date dateOfRent;
    private String equipmentNotOrdered ="";

    public RentUnit(ArrayList<SportEquipment> units, Date dateOfRent)
    {
        this.units = units;
        this.dateOfRent = dateOfRent;
    }
    public void setEquipmentNotOrdered(String equipmentNotOrdered)
    {
        this.equipmentNotOrdered = equipmentNotOrdered;
    }
    public String toString()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String result ="Rented: " + dateFormat.format(dateOfRent) + "\n";
        for(SportEquipment se : units)
        {
            result += se.toString() + "\n";
        }
        if (!this.equipmentNotOrdered.isEmpty())
        {
            result += "Failed to rent: " + equipmentNotOrdered;
        }
        return result.trim();
    }
}
