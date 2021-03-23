package Client;


import java.io.InputStream;
import java.io.OutputStream;

/**
 * every client that we would include in our program should implement IClientStrategy
 * and override clientStrategy.
 */
public interface IClientStrategy {
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}
