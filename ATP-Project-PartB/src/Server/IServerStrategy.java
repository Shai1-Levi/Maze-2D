package Server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * this class is used for prevent changing at the code for adding new server.
 * new class of server should implement the IServerStrategy
 * and override serverStrategyMethod.
 */
public interface IServerStrategy {
    public void serverStrategyMethod(InputStream inputStream, OutputStream outputStream);

}
