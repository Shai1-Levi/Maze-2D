package algorithms.search;

import java.util.*;


public class BreadthFirstSearch extends ASearchingAlgorithm {

    private Queue<AState> Open;
    private ArrayList<AState> Closed;
    private HashSet<String> hash;
    private HashSet<String> HashClosed;

    public BreadthFirstSearch() {
        this.Open = new LinkedList<AState>();
        this.Closed = new ArrayList<AState>();
        this.counter = 0;
        this.hash = new HashSet<String>();
        this.HashClosed = new HashSet<String>();
        this.counter = 0;
    }

    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }

    @Override
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
                    /*
                    the change between BreadthFirstSearch and BestFirstSearch is that BreadthFirstSearch does not care
                    about the cost of the movement
                     */
                    if(this.getName().equals("BestFirstSearch")) {
                        total_cost = neighbor.getCost() + bestScore.getCost();
                        neighbor.setCost(total_cost);
                    }
                    neighbor.setCameFrom(bestScore);
                    this.Open.add(neighbor);
                    this.hash.add(neighbor.toString());
                }
            }
        }
        return sol;
    }



    /*@Override
    public Solution solve(ISearchable domain) {
        Solution sol = new Solution();
        if(domain == null){
            return sol;
        }
        ArrayList<AState> queue = new ArrayList<AState>();
        HashMap<String, AState> hash_visited = new HashMap<String, AState>();
        AState s = domain.getStartState();
        queue.add(s);
        hash_visited.put(s.toString(),s);
        this.counter++;

        while(queue.isEmpty() == false)
        {
            s = queue.remove(0);
            this.counter++;
            if(s.equals(domain.getGoalState())){
                break;
            }

            for(AState neighbor: domain.getAllPossibleStates(s))
            {
                if(!(hash_visited.containsKey(neighbor.toString())))
                {
                    queue.add(neighbor);
                    hash_visited.put(neighbor.toString(),neighbor);
                    neighbor.setCameFrom(s);
                }
            }
        }
        queue.add(s);
        this.counter++;
        sol.copyArray(queue);
        return sol;

    }*/

}