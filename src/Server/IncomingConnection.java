package Server;
import common.Duplexer;
import common.Kumbhakarna;

import java.io.IOException;
import java.net.Socket;

class IncomingConnection extends Thread{
    private Duplexer connection;

    public IncomingConnection(Socket connection)
    {
        try
        {
            this.connection = new Duplexer(connection);
        }
        catch (IOException io)
        {

        }
    }

    public void run(){
        connection.send(Kumbhakarna.CONNECT);
        //Primary loop, process incoming requests here
        try{
            while (true)
            {
                String message = connection.receive();
                System.out.println("Message: " + message);
                String[] tokens = message.split(" ");
                switch (tokens[0]){
                    case Kumbhakarna.ADD:
                        break;
                    case Kumbhakarna.DROP:
                        break;
                    case Kumbhakarna.STOP:
                        break;
                }
            }
        } catch (Exception ex)
        {
            connection.send(Kumbhakarna.ERROR);
        }
    }
}