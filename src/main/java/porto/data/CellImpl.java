package porto.data;

import porto.data.api.Cell;

public record CellImpl(
    Integer numCell,
    Integer capacity
)implements Cell {
    
}
