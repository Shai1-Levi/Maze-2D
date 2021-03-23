package Server;


import algorithms.mazeGenerators.IMazeGenerator;
import IO.MyCompressorOutputStream;
import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    public ServerStrategyGenerateMaze(){}

    /**
     * the method get int[] at the inputStream and generate new maze by the the class the client should chose
     * at the config.properties file.
     * return the maze to the client
     * @param inputStream the size of maze to create
     * @param outputStream write maze Object.
     */
    @Override
    public void serverStrategyMethod(InputStream inputStream, OutputStream outputStream) {
    try {
        ObjectInputStream fromClient = new ObjectInputStream(inputStream);  // READ
        ObjectOutputStream toClient = new ObjectOutputStream(outputStream); // WRITE
        ByteArrayOutputStream baos = new ByteArrayOutputStream();


        int [] maze_diam = (int[]) fromClient.readObject();
        //Server.Configurations configurations = new Server.Configurations();
        //IMazeGenerator mg = configurations.getIMazeGenerator();
        IMazeGenerator mg = Server.Configurations.getIMazeGenerator();
        //IMazeGenerator mg = new MyMazeGenerator();

        byte[] maze_byteArray = (mg.generate(maze_diam[0], maze_diam[1])).toByteArray();
        MyCompressorOutputStream myCompressorOutputStream = new MyCompressorOutputStream(baos);
        myCompressorOutputStream.write(maze_byteArray);

        toClient.writeObject(baos.toByteArray());

        toClient.flush();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}
