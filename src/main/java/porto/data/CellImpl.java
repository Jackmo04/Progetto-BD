package porto.data;

import porto.data.api.Cell;

/**
 * Implementation of the Cell interface representing a cell in the Porto data model.
 * This record contains the number of the cell and its capacity.
 */
public record CellImpl(
    Integer numCell,
    Integer capacity
)implements Cell {
    
}
