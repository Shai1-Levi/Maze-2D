package algorithms.search;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm{

    private Stack<AState> stack;

    public DepthFirstSearch() {
        this.counter = 0;
        this.stack = new Stack<AState>();
    }
    
    @Override
    public String getName() {
        return "DeptFirstSearch";
    }

    /**
     * @return the solution of the maze by DFS algorithms, visited are all the Mazetates we have scaned. stack represents a java stack, needed for the algorithm.
     */
     @Override
    public Solution solve(ISearchable domain) {
         Solution sol = new Solution();
         if(domain == null){
             return sol;
         }
        HashSet<String> visited = new HashSet<String>();
        ArrayList<AState> allPaths = new ArrayList<AState>();
        AState s = domain.getStartState();
        this.stack.push(s);
        this.counter++;

        while (this.stack.isEmpty() == false) {
            s = this.stack.peek();
            stack.pop();
            if(s.equals(domain.getGoalState()))
            {
                break;
            }
            if (visited.contains(s.toString()) == false)
            {
                visited.add(s.toString());
                this.counter = visited.size();
                allPaths.add(s);
            }
            for (AState neighbor : domain.getAllPossibleStates(s))
            {
                if (!(visited.contains(neighbor.toString())))
                {
                    stack.push(neighbor);
                    neighbor.setCameFrom(s);
                }
            }
        }
         allPaths.add(s);
         if(!(allPaths.get(allPaths.size()-1).toString().equals(domain.getGoalState().toString())))
            return sol;
        sol.copyArray(allPaths);
        return sol;
    }
}
