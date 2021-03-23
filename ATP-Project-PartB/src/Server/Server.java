package Server;

import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.ISearchingAlgorithm;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


public class Server {
    ServerSocket serverSocket = null;
    int port;                      // the port that Client will connect.
    private int timeToWait;
    private IServerStrategy iServerStrategy;
    private volatile boolean stop;
    ExecutorService pool = null;
    Socket clientSocket = null;
    public Server.Configurations config;

    //public static final Logger logger = LogManager.getLogger();
    public Configurations getConfig() {
        return config;
    }

    public Server(int port, int TimeToWait, IServerStrategy iServerStrategyArg) {
        this.port = port;
        this.timeToWait = TimeToWait;
        this.iServerStrategy = iServerStrategyArg;
        this.stop = false;
        //Server.Configurations config = new Configurations();
        config = new Configurations();
        ///this.pool = Executors.newFixedThreadPool(config.getNumOfThreads());
        this.pool = Executors.newFixedThreadPool(Server.Configurations.getNumOfThreads());
        //this.pool = Executors.newFixedThreadPool(10);
    }

    /**
     * the method open new serverSocket and connected client to the server
     * every client that connect to the server get a Thread and that mean the sever is ready to accept
     * new clients and handle them.
     * we use thread pool to disabled the number of Thread. and less the maintenance with Thread
     */
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(this.timeToWait);
            while (!stop) {
                try {
                    clientSocket = serverSocket.accept();
                    Thread t = new Thread(() -> {
                        clientHandle(clientSocket);
                    });
                    pool.execute(t);

                } catch (IOException e) {
                    // Time out
                }
            }
            pool.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method called from the test class, and allow to the program to run parallel the Threads
     * and not sequentially
     */
    public void start() {
        new Thread(() -> {
            run();
        }).start();
    }

    private void clientHandle(Socket clientSocket) {
        try {
            InputStream inFromClient = clientSocket.getInputStream();   //  READ
            OutputStream outToClient = clientSocket.getOutputStream();  //  WRITE

            this.iServerStrategy.serverStrategyMethod(inFromClient, outToClient);

            inFromClient.close();
            outToClient.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.stop = true;
    }

    /**
     * this class is defined as static class because we want to access the method from any other class in our program
     * for read the data we can't or should not defined ahead.
     */
    public static class Configurations {
        static ISearchingAlgorithm isa = null;
        static IMazeGenerator iMazeGenerator = null;
        static int numOfThreads = -1;
        static boolean configFile = true;

        public void setIsa(String SearchAlgorithm) {
            if (SearchAlgorithm.equals("Best first search")) {
                isa = new BestFirstSearch();
            } else if (SearchAlgorithm.equals("Breath first search")) {
                isa = new BestFirstSearch();
            } else {
                isa = new DepthFirstSearch();
            }
            configFile = false;
        }

        public void setiMazeGenerator(String MazeGenerator) {
            if (MazeGenerator.equals("Prime Maze")) {
                iMazeGenerator = new MyMazeGenerator();
            } else if (MazeGenerator.equals("Empty Maze")) {
                iMazeGenerator = new EmptyMazeGenerator();
            } else {
                iMazeGenerator = new SimpleMazeGenerator();
            }
            configFile = false;

        }

        public void setNumOfThreads(String NumberOfThreads) {
            numOfThreads =  Integer.parseInt(NumberOfThreads);
            configFile = false;
        }
        public static ISearchingAlgorithm getISA() {
            try {
                if(configFile == true) {
                    InputStream input = new FileInputStream("resources/config.properties");
                    Properties prop = new Properties();
                    // load a properties file
                    prop.load(input);
                    String SearchAlgorithm = prop.getProperty("SearchAlgorithm");
                    if (SearchAlgorithm.equals("BestFirstSearch")) {
                        isa = new BestFirstSearch();
                    } else if (SearchAlgorithm.equals("BreathFirstSearch")) {
                        isa = new BestFirstSearch();
                    } else {
                        isa = new DepthFirstSearch();
                    }
                }
                return isa;

            }catch (IOException ex){ex.printStackTrace();}
            return isa;
        }
        public static IMazeGenerator getIMazeGenerator() {
            try {
                if(configFile == true) {
                    InputStream input = new FileInputStream("resources/config.properties");
                    Properties prop = new Properties();
                    // load a properties file
                    prop.load(input);

                    String MazeGenerator = prop.getProperty("MazeGenerator");

                    if (MazeGenerator.equals("MyMazeGenerator")) {
                        iMazeGenerator = new MyMazeGenerator();
                    } else if (MazeGenerator.equals("EmptyMazeGenerator")) {
                        iMazeGenerator = new EmptyMazeGenerator();
                    } else {
                        iMazeGenerator = new SimpleMazeGenerator();
                    }
                }
                return iMazeGenerator;

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return iMazeGenerator;
        }
        public static int getNumOfThreads() {
            try {
                if (configFile == true) {
                    InputStream input = new FileInputStream("resources/config.properties");
                    Properties prop = new Properties();

                    // load a properties file
                    prop.load(input);

                    String NumberOfThreads = prop.getProperty("NumberOfThreads");
                    numOfThreads = Integer.parseInt(NumberOfThreads);
                }
                return numOfThreads;
            /*if(NumberOfThreads.equals("")){
                numOfThreads = 10;
            }
            else{
                numOfThreads =  Integer.parseInt(NumberOfThreads);
            }*/

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return numOfThreads;
        }
    }
}

