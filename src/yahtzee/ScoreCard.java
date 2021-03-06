/*******************************************************************************
 * ScoreCard.java
 * Author: Clara Durling
 * 
 * This class handles keeping track of scores and then calculating the end
 * scores earned by a player in a single game
 ******************************************************************************/
package yahtzee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import scorecategories.ScoreCategory;

/**
 *
 * @author Clara Durling
 */
public class ScoreCard {
    private final Map<String, Integer> UPPERCARD = new HashMap();
    private final Set<String> TOP = UPPERCARD.keySet();
    private final Map<String, Integer> LOWERCARD = new HashMap();
    private final Set<String> BOTTOM = LOWERCARD.keySet();
    private boolean topBonus = false;
    private final String playerName;
    private int preTotal;
    private int upperTotal;
    private int lowerTotal;
    private int grandTotal;
    
    /**
     *
     * @param name
     * @param categories
     */
    public ScoreCard(String name, List<ScoreCategory> categories){
        this.playerName = name;
        
        for(int i = 0; i < 6; i++){
            // fill the card and set scores to -1
            ScoreCategory cat = categories.get(i);
            String catName = cat.getName();
            UPPERCARD.put(catName, -1);
        }
        
        for(int i = 6; i < categories.size(); i++){
            ScoreCategory cat = categories.get(i);
            String catName = cat.getName();
            LOWERCARD.put(catName, -1);
        }
    }
    
    //**************************************************************************
    
    /**
     *
     * @param cat
     * @param score
     */
    public void inputScore(ScoreCategory cat, int score){
        
        // Update score
        if(catOnTop(cat)){
            UPPERCARD.put(cat.getName(), score);
        } else {
            LOWERCARD.put(cat.getName(), score);
        }
        
        // Check if the top half is ready to be calculated
        if(isTopFull()){
            calculateTop();
        }
        
        // Check if the bottom half is ready to be calclulated
        if(isBottomFull()){
            calculateBottom();
        }
    } // End public void inputScore(ScoreCategory, int)
    
    //**************************************************************************
    
    private boolean catOnTop(ScoreCategory cat){
        boolean present = false;
        for(String key: TOP){
            if(key.equals(cat.getName())){
                present = true;
            }
        }
        return present;
    } // End  private Boolean catOnTop(ScoreCategory);
    
    //**************************************************************************
    
    private void calculateTop(){
        this.preTotal = 0;
        for(String key: TOP){
            // Loop through the list of upperCard scores and get the score values for each one
            int nextScore = UPPERCARD.get(key);
            this.preTotal += nextScore;
        }
        
        // Determine if bonus points have been earned and incorporate points into total if earned
        if(this.preTotal >= 63){
            this.upperTotal = this.preTotal + 35;
            this.topBonus = true;
        } else {
            this.upperTotal = this.preTotal;
        }
    } // End private void calculateTop()
     
    //**************************************************************************
    
    private void calculateBottom(){
        this.lowerTotal = 0;
        for(String key: BOTTOM){
            // Loop through the list of upperCard scores and get the score values for each one
            int nextScore = LOWERCARD.get(key);
            this.lowerTotal += nextScore;
        }
    } // End private void calculateBottom()
    
    //**************************************************************************
    
    private boolean isTopFull(){
        boolean full = true;
        
        // Check if the upper half of the card is full
        for(String key: this.TOP){
            int score = UPPERCARD.get(key);
            if(score < 0){
                full = false;
            }
        }
        
        return full;
    } // End private boolean isTopFull()
    
    //**************************************************************************
    
    private boolean isBottomFull(){
        boolean full = true;
        
        // Check if the lower half of the card is full
        for(String string: this.BOTTOM){
            int score = LOWERCARD.get(string);
            if(score < 0){
                full = false;
            }
        }
        
        return full;
    } // End private Boolean isBottomFull()
    
    //**************************************************************************

    /**
     *
     * @return boolean
     */
    public boolean isFull(){
        return (this.isBottomFull() && this.isTopFull());
    } // End public boolean isFull()
    
    //**************************************************************************
    
    /**
     *
     * @return Integer
     */
    public int getFinalScore(){
        // Calculate the final total from the game
        this.grandTotal = this.upperTotal + this.lowerTotal;
        return this.grandTotal;
    } // End public Final int get Final Score
    
    //**************************************************************************

    @Override
    public String toString() {
        int total;
        int bonus;
        
        if(topBonus == true){
            bonus = 35;
        } else {
            bonus = 0;
        }
        
        if(this.grandTotal < 0){
            total = 0;
        } else {
            total = this.grandTotal;
        }
        
        System.out.println("-1 indicates that a score has not yet been entered for a field.\n\n" +
                "Score card for: " + playerName + "\n" 
                + "UPPER SECTION\n"
                + "Aces: " + this.UPPERCARD.get("aces") + "\n" +
                "Twos: " + this.UPPERCARD.get("twos") + "\n" +
                "Threes: " + this.UPPERCARD.get("threes") + "\n" +
                "Fours: " + this.UPPERCARD.get("fours") + "\n" +
                "Fives: " + this.UPPERCARD.get("fives") + "\n" +
                "Sixes: " + this.UPPERCARD.get("sixes") + "\n" +
                "Total Score: " + this.preTotal + "\n" +
                "BONUS (for scores 63 or higher): " + bonus + "\n" +
                "UPPER TOTAL: " + this.upperTotal + "\n\n" +
                "LOWER SECTION\n" +
                "3 of a Kind: " + this.LOWERCARD.get("3 of a kind") + "\n" +
                "4 of a Kind: " + this.LOWERCARD.get("4 of a kind") + "\n" +
                "Full House: " + this.LOWERCARD.get("full house") + "\n" +
                "Small Straight: " + this.LOWERCARD.get("small straight") + "\n" +
                "Large Straight: " + this.LOWERCARD.get("large straight") + "\n" +
                "Yahtzee: " + this.LOWERCARD.get("yahtzee") + "\n" +
                "Chance: " + this.LOWERCARD.get("chance") + "\n\n" +
                "UPPER TOTAL: " + this.upperTotal + "\n" +
                "LOWER TOTAL: " + this.lowerTotal
        );
        return "Grand Total: " + total;
    } // End public String toString()
    
} // End ScoreCard class