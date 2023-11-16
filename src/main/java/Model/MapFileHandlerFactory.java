
package Model;

/**
 * Factory method to create the appropriate map file handler.
 */
public class MapFileHandlerFactory {

    /**
     * Creates a map file handler based on the file format.
     * @param fileName The name of the file to handle.
     * @return An instance of IMapFileHandler appropriate for the file format.
     */
    public static IMapFileHandler createMapFileHandler(String fileName) {
        if (fileName.endsWith(".domination")) {
            return new LoadGraphRefactored();
        } else if (fileName.endsWith(".conquest")) {
            return new ConquestFileAdapter();
        } else {
            // Handle other formats or throw an exception
            throw new IllegalArgumentException("Unsupported file format: " + fileName);
        }
    }
}
