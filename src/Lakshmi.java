import java.util.HashMap;
import java.util.Map;

/**
 * Represents the NYSE stock exchange
 */
public class Lakshmi
{
    Map<String, Stock> NYSE = new HashMap<>();
    public Lakshmi(Map<String, Stock> NYSE)
    {
        this.NYSE = NYSE;
    }
}
