package Client;

/**
 * Display and communicate with the viewer
 * send add/drop messages to server
 * send messages to users
 */


import Server.Raavan;
import common.Indrajit;
import common.Lakshmi;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;

/**
 * A JavaFX GUI for the networked Kubera
 * @author srikamal
 */
public class GUI extends Application implements Observer<Lakshmi>
{
    /**
     * contains all the variables needed to run the GUI
     */
    private Image add;
    private Image drop;
    private Image back;
    private Image background;
    private Image stop;
    private Image portfolio;
    private User user;
    private Lakshmi laskhmi;

    /**
     * Initialises said variables and utilizes them effectively
     */
    @Override
    public void init()
    {
        try
        {
            List<String> args = getParameters().getRaw();

            String host = args.get(0);
            int port = Integer.parseInt(args.get(1));

            this.add = new Image("Client/add.png");
            this.drop = new Image("Client/drop.png");
            this.back = new Image("Client/back.jpg");
            this.background = new Image("Client/background.jpg");
            this.portfolio = new Image("Client/portfolio.png");
            this.stop = new Image("Client/stop.png");

            this.user = new User(host, port, Raavan.lakshmi);
            this.laskhmi = Raavan.lakshmi;
        }
        catch (Indrajit indrajit)
        {
            indrajit.printStackTrace();
        }
    }


    @Override
    public void update(Lakshmi lakshmi) {

    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
