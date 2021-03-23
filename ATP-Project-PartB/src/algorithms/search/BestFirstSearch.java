package algorithms.search;

import java.util.*;

class The_Comparator implements Comparator<AState> {
    public int compare(AState s1, AState s2){
        int S1 = s1.getCost();
        int S2 = s2.getCost();
        int res = (S1 - S2);
        return res;
    }
}

/**
 * the solve method is implemented at BreadthFirstSearch class because the algorithm implementation
 * best is particular case of BreadthFirstSearch
 */

public class BestFirstSearch extends BreadthFirstSearch {
    private PriorityQueue<AState> Open;
    private ArrayList<AState> Closed;
    private HashSet<String> hash;
    private HashSet<String> HashClosed;

    public BestFirstSearch(){
        this.Open = new PriorityQueue<AState>(new The_Comparator());
        this.Closed = new ArrayList<AState>();
        this.counter = 0;
        this.hash = new HashSet<String>();
        this.HashClosed = new HashSet<String>();
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }

    /*@Override
    public Solution solve(ISearchable domain) {

        Solution sol = new Solution();
        if(domain == null){
            return sol;
        }
        this.Open.add(domain.getStartState());
        this.hash.add(domain.getStartState().toString());
        this.counter++;
        AState bestScore;
        int total_cost;
        while(!this.Open.isEmpty())
        {
            bestScore = this.Open.poll();
            this.Closed.add(bestScore);
            this.HashClosed.add(bestScore.toString());
            if(bestScore.equals(domain.getGoalState()))
            {
                this.Closed.add(bestScore);
                this.counter = this.Closed.size() + this.Open.size();
                sol.copyArray(this.Closed);
                return sol;
            }

            for(AState neighbor : domain.getAllPossibleStates(bestScore))
            {
                if((this.HashClosed.contains(neighbor.toString())))
                {
                    continue;
                }
                if(!(this.hash.contains(neighbor.toString())))
                {
                    //total_cost = neighbor.getCost() + bestScore.getCost();
                    //neighbor.setCost(total_cost);
                    neighbor.setCameFrom(bestScore);
                    this.Open.add(neighbor);
                    this.hash.add(neighbor.toString());
                }
            }
        }
        return sol;
    }*/
}

