package Model;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class contains implementation of all the actions defined by the game
 */
public class EngineCommand {

    /**
     * This method would check if the map with given Name exists
     * If it does it would load that map and would check with player if they want to edit the map
     * Otherwise it would create new WargameMap object and would prompt user for next commands
     *
     * @param p_mapName name of the given map
     * @return existing or new WargameMap object
     */
    public WargameMap editMap(String p_mapName) {
        WargameMap l_map = null;
        String l_mapfilePath = "src/main/resources/maps/" + p_mapName + ".map";
        File l_mapFile = new File(l_mapfilePath);
        if (l_mapFile.exists()) {
            System.out.println("Map " + p_mapName + ".map already exists, follow below commands to edit it");
        } else {
            System.out.println(p_mapName + " does not exist.");
            System.out.println("Creating a new Map named " + p_mapName);
            l_map = new WargameMap(p_mapName);
        }
        return l_map;
    }

    public boolean saveMap(WargameMap p_map, String p_fileName) {
        try {
            BufferedWriter l_writer = new BufferedWriter(new FileWriter("src/main/resources/maps/" + p_fileName + ".map"));
            l_writer.write("name " + p_fileName + " Map");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean addContinentToMap(WargameMap p_gameMap, int p_continentID, String p_continentName){
        //first of all checking is this given continent already exists
        //if it does not exists then create the new continent object and add it to the game map
        if(!(GameGraph.isContinentExists(p_gameMap,p_continentName)))
        {
            Continent l_continent = new Continent(p_continentName,p_continentID);
            p_gameMap.addContinents(p_continentName,l_continent);
            return true;
        }
        else{
            return false;
        }
    }







}