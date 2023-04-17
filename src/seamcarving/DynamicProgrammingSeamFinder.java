package seamcarving;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 * @see SeamCarver
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    @Override
    public List<Integer> findSeam(Picture picture, EnergyFunction f) {
        // TODO: Replace with your code
        // 1.Create a dp table
        double[][] dpTable = new double[picture.width()][picture.height()];
        // 2.initialize the dp table value
        // left most edge just store pixel energy
        for(int y = 0; y < picture.height(); y++){
            dpTable[0][y] = f.apply(picture, 0, y);
        }
        // rest edges will be lowest-energy predecessor to the pixel + the energy of the given pixel
        for(int x = 1; x < picture.width(); x++){
            for(int y = 0; y < picture.height(); y++){
                // no energy will be greater than the positive infinity
                double miniPreEnergy = Double.POSITIVE_INFINITY;
                // find the lowest-energy predecessor from its left-up, left-middle, and left-down neighbors.
                for(int j = y-1; j <= y+1; j++){
                    if(j >= 0 && j < picture.height()){
                        if(dpTable[x-1][j] < miniPreEnergy){
                            miniPreEnergy = dpTable[x-1][j];
                        }
                    }
                }
                dpTable[x][y] = f.apply(picture, x, y) + miniPreEnergy;
            }
        }
        // 3.find the shortest path using dp table
        List<Integer> path = new ArrayList<>();
        int minY = 0;
        // find the rightmost pixel with the lowest energy
        for(int y = 0; y < picture.height(); y++){
            if(dpTable[picture.width()-1][y] < dpTable[picture.width()-1][minY]){
                minY = y;
            }
        }
        // minY is equal the y coordinate from the previous right edge
        path.add(minY);
        // trace all lowest-energy predecessor back to leftmost edge
        for(int x = picture.width()-2; x >= 0; x--){
            int preY = path.get(path.size()-1);
            minY = preY;
            for(int y = preY-1; y <= preY+1; y++){
                if(y >= 0 && y < picture.height()){
                    if(dpTable[x][y] < dpTable[x][minY]){
                        minY = y;
                    }
                }
            }
            path.add(minY);
        }

        Collections.reverse(path);
        return path;
    }
}
