/*******************************************************************************
 * ThreeOfKind.java 
 * Author: Clara Durling
 * 
 * This class is for the Three of a Kind location on the score sheet
 * This class accepts a sequence of dice values and calculates the proper score
 ******************************************************************************/
package scorecategories;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Clara Durling
 */
public class ThreeOfKind extends ScoreCategory {
    
    private final String name = "3 of a kind";
    private boolean available = true;
    private int score;

    /**
     *
     * @param die
     * @return int
     */
    @Override
    public int scorer(List<Integer> die) {
        boolean qualifies = qualify(die);
        if(qualifies){
            for(int i = 0; i < die.size(); i++){
                score += die.get(i);
            }
        } else {
            score = 0;
        }
        available = false;
        return score;
    }
    
    private boolean qualify(List<Integer> die){
        boolean qualifies = false;
        
        // Sort the list numerically
        Collections.sort(die);
        
        int d1 = die.get(0);
        int d2 = die.get(1);
        int d3 = die.get(2);
        int d4 = die.get(3);
        int d5 = die.get(4);
        
        if ((d1 == d2) && (d1 == d3)) {
            qualifies = true;
        } else if ((d2 == d3) && (d2 == d4)) {
            qualifies = true;
        } else if ((d3 == d4) && (d3 == d5)) {
            qualifies = true;
        }
        
        return qualifies;
    }

    /**
     *
     * @return String
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     *
     * @return boolean
     */
    @Override
    public boolean getAvailability() {
        return available;
    }
    
}
