package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.HashMap;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private SearchableMaze searchableMaze;
    private HashMap<byte[], Maze> hashMap;

    public ServerStrategySolveSearchProblem() {
        this.searchableMaze = null;
        this.hashMap = new HashMap<byte[], Maze>();
    }

    /**
     * this method read maze from inputStream and solve it by the class the client chose
     * at the config.properties file.
     * the solution it saved to filed. and reused if the method get the same maze.
     * for recognize if the method get maze that has been solve.
     * the mazes saved in hashMap.
     * @param inputStream Maze
     * @param outputStream socket to write the solution of the maze.
     */
    @Override
    public void serverStrategyMethod(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);

            //Server.Configurations configurations = new Server.Configurations();
            //ISearchingAlgorithm best = configurations.getISA();
            ISearchingAlgorithm best = Server.Configurations.getISA();
            //ISearchingAlgorithm best = new BestFirstSearch();

            Maze mazeFromClient = (Maze) fromClient.readObject();
            this.searchableMaze =  new SearchableMaze(mazeFromClient);

            // The getProperty method returns a string containing the value of the property.
            // If the property does not exist, this version of getProperty returns null.
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");

            if(tempDirectoryPath != null) {

                Solution solution;
                if ((this.hashMap.containsKey(mazeFromClient.toByteArray()))){

                    mazeFromClient = this.hashMap.get(mazeFromClient.toByteArray());

                    FileInputStream fi = new FileInputStream(new File(tempDirectoryPath + "\\sol" + mazeFromClient.toByteArray()));
                    ObjectInputStream ois = new ObjectInputStream(fi);
                    solution = (Solution)ois.readObject();

                    ois.close();
                    fi.close();
                }
                else
                {
                   this.hashMap.put(mazeFromClient.toByteArray(), mazeFromClient);

                    //Maze
                    FileOutputStream fo = new FileOutputStream(new File(tempDirectoryPath + "\\maze" +mazeFromClient.toByteArray()));
                    ObjectOutputStream oos = new ObjectOutputStream(fo);

                    // write object to file
                    oos.writeObject(mazeFromClient.toByteArray());

                    oos.close();
                    fo.close();

                    //Solution
                    solution = best.solve(this.searchableMaze);

                    fo = new FileOutputStream(new File(tempDirectoryPath +"\\sol" + mazeFromClient.toByteArray()));
                    oos = new ObjectOutputStream(fo);

                    // write object to file
                    oos.writeObject(solution);

                    oos.close();
                    fo.close();
                }
                toClient.writeObject(solution);
            }
            } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}



