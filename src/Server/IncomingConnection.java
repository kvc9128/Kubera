package Server;
import java.io.IOException;
import java.net.Socket;
import common.Duplexer;

class IncomingConnection extends Thread{
    private Duplexer connection;

    public IncomingConnection(Socket connection){
        try {
            this.connection = new Duplexer(connection);
        } catch (IOException io){}
    }

    public void run(){
        //Primary loop, process incoming requests here
        while (true){
            String message = connection.receive();
            //TODO: Add command handling, once commands are decided
            //Temporary, for testing purposes
            connection.send(message);
        }
    }
}