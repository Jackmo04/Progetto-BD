package porto.data.api;

/**
 * Interface representing a Cell in the Porto data model.
 * This interface can be extended to define specific behaviors or properties of a Cell.
 */
public interface Cell {

    /**
     * 
     * @return number of cell.
     */
    Integer numCell();

    /**
     * 
     * @return capacity of cell.
     */
    Integer capacity();
    
}
