
package Model;

/**
 * Interface for map file handling operations.
 */
public interface IMapFileHandler {
    /**
     * Reads a map file and loads the data.
     * @param fileName The name of the file to read.
     * @return true if the file was read successfully, false otherwise.
     */
    boolean readMapFile(String fileName);

    /**
     * Writes map data to a file.
     * @param fileName The name of the file to write to.
     * @return true if the file was written successfully, false otherwise.
     */
    boolean writeMapFile(String fileName);
}
