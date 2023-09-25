package Model;


/**
 * The GameGraph class is responsible for creating, validating and analyzing the game map.
 *
 */
public class GameGraph {

    /**
     * Checking whether the continent exists in the war game map and return boolean value
     * @param p_gameMap Object of the WargameMap class consist of the details like # of continents and # of countries
     * @param p_continentName Continent's name that we are checking against
     * @return false if there is no continent of given p_continentName in WargameMap else returns true
     */
    public static boolean isContinentExists(WargameMap p_gameMap, String p_continentName){
        p_continentName=p_continentName.toLowerCase();
        if(p_gameMap.getContinents().containsKey(p_continentName))
        {
            return true;
        }
        else
        {
            return false;
        }
    }




}
