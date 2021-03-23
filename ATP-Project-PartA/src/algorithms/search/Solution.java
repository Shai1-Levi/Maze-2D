package algorithms.search;


import java.util.ArrayList;

/**
 * this class represents the road between start position of maze and its goal position. AState is part of the road.
 *
 */
public class Solution {

    private ArrayList<AState> path;

    public Solution() {
        this.path = new ArrayList<AState>();
    }

    public void copyArray(ArrayList<AState> arr){
        for(int i = 0; i<arr.size(); i++)
        {
            this.path.add(arr.get(i));
        }
    }

    public ArrayList<AState> getSolutionPath()
    {
        ArrayList<AState> sol = new ArrayList<AState>();
        if(this.path.size() > 0) {
            AState prev = this.path.get(this.path.size() - 1);
            while (prev != null) {
                sol.add(0, prev);
                prev = prev.getCameFrom();
            }
        }
        return sol;
    }
}
