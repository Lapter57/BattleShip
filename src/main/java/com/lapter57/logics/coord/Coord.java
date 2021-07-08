package com.lapter57.logics.coord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coord {
    public int row = 0;
    public int col = 0;

    public Coord(Coord crd){
        row = crd.row;
        col = crd.col;
    }


}
