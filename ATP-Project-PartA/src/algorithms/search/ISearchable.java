package algorithms.search;

import java.util.ArrayList;

/**
 * This interface is important component at the connection between ISearchingAlgorithms and different problems to solve.
 * one of them is Maze problem
 */
public interface ISearchable {
    AState getStartState();
    AState getGoalState();
    ArrayList<AState> getAllPossibleStates(AState s); //get all the possible moves from s
}
