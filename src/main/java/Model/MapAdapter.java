package Model;

/**
 * Adapter class provide the bridge between DominationMap and ConquestMap
 *
 *
 */
public class MapAdapter extends DominationTypeMap{

    ConquestTypeMap d_ConquestMap;

    //write down java doc here
    /**
     * Constructor of MapAdapter
     * @param p_conquestMap reference of conquest map
     */
    public MapAdapter(ConquestTypeMap p_conquestMap){
        this.d_ConquestMap = p_conquestMap;
    }


    /**
     * Read the conquest map
     * @return return map to read
     */

    public WargameMap readDominationMap(String p_mapName) {
        return d_ConquestMap.readConquestMap(p_mapName);
    }


    /**
     * Save the conquest map
     * @param p_map map to save
     * @param p_fileName the name of map saved
     * @return return true if map saved succesfully, else false
     */
    public boolean saveMap(WargameMap p_map, String p_fileName) {
        return d_ConquestMap.saveMap(p_map, p_fileName);
    }
}
