package Model;
import Client.*;
import IO.MyDecompressorInputStream;
import Server.*;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;


public class MyModel extends Observable implements IModel {

    private Server serverStrategyGenerateMaze;
    private Server serverStrategySolveSearchProblem;
    private Maze mazeGenrated;

    private Solution mazeSolution;
    private ArrayList<String> solutionByString = null;

    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;

    private int positionEndMazeRow = 1;
    private int positionEndMazeCol = 1;
    private boolean reachTheChesse = false;

    /**
     * The constructor is uploading the servers
     */
    public MyModel(){
        this.serverStrategyGenerateMaze =  new Server(5400, 1000, new ServerStrategyGenerateMaze());
        this.serverStrategySolveSearchProblem =  new Server(5401, 1000, new ServerStrategySolveSearchProblem());
    }

    /**
     * start the servers and prepare them to accept clients
     */

    public void startServers() {

        this.serverStrategyGenerateMaze.start();
        this.serverStrategySolveSearchProblem.start();
    }

    /**
     * stop the server from and realise all clients
     */
    public void stopServers() {
        this.serverStrategyGenerateMaze.stop();
        this.serverStrategySolveSearchProblem.stop();
    }

    /**
     * The method is generate maze from Generate server as new client
     * @param width
     * @param height
     */

    @Override
    public void generateMaze(int width, int height) {
        //Generate maze
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new GenerateServerStrategy(width, height));
            solutionByString = null;
            reachTheChesse = false;
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method get maze that has been created and solve the maze and update by the Observable
     */

    public void solveMaze(){
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {

                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);

                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(mazeGenrated); //send maze to server
                        toServer.flush();
                        mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        setMazeSolution();

                        /* locate here because all the command need to be exacute in before communicate with server*/
                        setChanged();
                        notifyObservers();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }); client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void save(File file)
    {
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(mazeGenrated);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method get a file that represent a maze that saved before and load and update the relevant object by the Observable
     * @param file
     */

    @Override
    public void load(File file)
    {
        try{
         ObjectInputStream objectInputStream= new ObjectInputStream((new FileInputStream(file)));
         this.mazeGenrated = (Maze) objectInputStream.readObject();

        characterPositionRow = mazeGenrated.getStartPosition().getRowIndex();
        characterPositionColumn = mazeGenrated.getStartPosition().getColumnIndex();

        positionEndMazeRow = mazeGenrated.getGoalPosition().getRowIndex();
        positionEndMazeCol = mazeGenrated.getGoalPosition().getColumnIndex();

        solutionByString = null;
        reachTheChesse = false;

        setChanged();
        notifyObservers();

        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method check if the requested move is possible at the game
     * @param movement
     */
    @Override
    public void moveCharacter(KeyCode movement) {
        switch (movement) {
            case DIGIT8:
            case NUMPAD8://case UP

                if((this.mazeGenrated.is_valid(characterPositionRow-1,characterPositionColumn))&& (this.mazeGenrated.get_val(characterPositionRow-1,characterPositionColumn) == 0)) {
                    characterPositionRow--;
                }
                break;

            case DIGIT2:
            case NUMPAD2://case DOWN:
                if((this.mazeGenrated.is_valid(characterPositionRow+1,characterPositionColumn))&& (this.mazeGenrated.get_val(characterPositionRow+1,characterPositionColumn) == 0)){
                    characterPositionRow++;
               }
                break;

            case DIGIT6:
            case NUMPAD6: //case RIGHT:
                if((this.mazeGenrated.is_valid(characterPositionRow,characterPositionColumn+1))&& (this.mazeGenrated.get_val(characterPositionRow,characterPositionColumn+1) == 0)) {
                    characterPositionColumn++;
                }
                break;

            case DIGIT4:
            case NUMPAD4://case LEFT:
                if((this.mazeGenrated.is_valid(characterPositionRow,characterPositionColumn-1))&& (this.mazeGenrated.get_val(characterPositionRow,characterPositionColumn-1) == 0)) {
                    characterPositionColumn--;
               }
                break;

            case DIGIT9:
            case NUMPAD9: //case LEFT UP
                if((this.mazeGenrated.is_valid(characterPositionRow - 1,characterPositionColumn + 1)) && (this.mazeGenrated.get_val(characterPositionRow-1,characterPositionColumn+1)!= 1) &&
                        ((this.mazeGenrated.get_val(characterPositionRow-1,characterPositionColumn) != 1) || (this.mazeGenrated.get_val(characterPositionRow,characterPositionColumn+1) != 1))) {
                    characterPositionColumn++;
                    characterPositionRow--;
                }
                break;

            case DIGIT7:
            case NUMPAD7: //case RIGHT UP:
                if((this.mazeGenrated.is_valid(characterPositionRow -1,characterPositionColumn-1))&& (this.mazeGenrated.get_val(characterPositionRow -1,characterPositionColumn-1)  != 1) &&
                        ((this.mazeGenrated.get_val(characterPositionRow ,characterPositionColumn-1) !=1)|| (this.mazeGenrated.get_val(characterPositionRow -1,characterPositionColumn) !=1))){
                    characterPositionColumn--;
                    characterPositionRow--;
                }
                break;

            case DIGIT1:
            case NUMPAD1: //case RIGHT DOWN:
                if((this.mazeGenrated.is_valid(characterPositionRow + 1 ,characterPositionColumn-1)) &&(this.mazeGenrated.get_val(characterPositionRow+1,characterPositionColumn-1)!= 1) &&
                        ((this.mazeGenrated.get_val(characterPositionRow ,characterPositionColumn-1) !=1)|| (this.mazeGenrated.get_val(characterPositionRow + 1 ,characterPositionColumn) !=1))) {
                    characterPositionColumn--;
                    characterPositionRow++;
                }
                break;

            case DIGIT3:
            case NUMPAD3: //case LEFT DOWN:
                if((this.mazeGenrated.is_valid(characterPositionRow+1,characterPositionColumn+1) && (this.mazeGenrated.get_val(characterPositionRow+1,characterPositionColumn+1)!= 1) &&
                        ((this.mazeGenrated.get_val(characterPositionRow,characterPositionColumn+1) != 1) || (this.mazeGenrated.get_val(characterPositionRow+1,characterPositionColumn) !=1)))) {
                    characterPositionColumn++;
                    characterPositionRow++;
                }
                break;
        }
        if((characterPositionColumn == positionEndMazeCol) && (characterPositionRow == positionEndMazeRow))
        {
            reachTheChesse = true;
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean isReachTheChesse() {
        return reachTheChesse;
    }

    public ArrayList<String>  getMazeSolution(){
        return solutionByString;
    }

    private void setMazeSolution() {
        solutionByString = new ArrayList<String>();
        for( AState sol : mazeSolution.getSolutionPath()){
            solutionByString.add(sol.toString());
        }
    }

    @Override
    public int[][] getMaze() {
        return this.mazeGenrated.getMaze();
    }

    @Override
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    @Override
    public int getPositionEndMazeRow() {
        return positionEndMazeRow;
    }

    @Override
    public int getPositionEndMazeCol() {
        return positionEndMazeCol;
    }

    /**
     * this is outside class for the implementaion of the interface IClientStrategy
     * and update the required object/ variables by the Observable
     */
    class GenerateServerStrategy implements IClientStrategy{
        private int width;
        private int height;
        public GenerateServerStrategy(int widthARG, int heightARG){
            width=widthARG;
            height=heightARG;
        }
        @Override
        public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
            try {
                ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                toServer.flush();
                int[] mazeDimensions = new int[]{width,height};
                toServer.writeObject(mazeDimensions); //send maze dimensions to server
                toServer.flush();
                byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                byte[] decompressedMaze = new byte[height * width + 10000/*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                is.read(decompressedMaze); //Fill decompressedMaze with bytes
                mazeGenrated = new Maze(decompressedMaze);
                characterPositionRow=mazeGenrated.getStartPosition().getRowIndex();
                characterPositionColumn=mazeGenrated.getStartPosition().getColumnIndex();
                positionEndMazeRow = mazeGenrated.getGoalPosition().getRowIndex();
                positionEndMazeCol = mazeGenrated.getGoalPosition().getColumnIndex();

                /* locate here because all the command need to be exacute in before communicate with server*/
                setChanged();
                notifyObservers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
