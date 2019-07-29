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
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
    private Image stop;
    private Image portfolio;
    private User user;
    private Lakshmi laskhmi;
    private Portfolio portfolio_read;

    private Scene scene_initial, scene_portfolio;

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
            this.portfolio = new Image("Client/portfolio.png");
            this.stop = new Image("Client/stop.png");

            this.user = new User(host, port, Raavan.lakshmi);
            this.laskhmi = Raavan.lakshmi;
            this.portfolio_read = new Portfolio(this.laskhmi);
            this.laskhmi.addObserver(this);
        }
        catch (Indrajit indrajit)
        {
            indrajit.printStackTrace();
        }
    }

    /**
     *
     * @param stage the stage where we will be displaying data
     * @throws Indrajit exception
     */
    @Override
    public void start(Stage stage) throws Indrajit
    {
        // scene initial
        BorderPane borderPane = new BorderPane();

        // button portfolio
        Button portfolio_viewer = new Button();
        portfolio_viewer.setGraphic(new ImageView(this.portfolio));
        portfolio_viewer.setOnAction(e -> stage.setScene(scene_portfolio));

        // creating a VBox to have a search bar and a label to display the stock
        TextField search_bar = new TextField();

        String Code = search_bar.getText();
        //setting the label
        Label Stock_info = new Label();
        Stock_info.setText(laskhmi.getAStock(Code).toString());
        //setting up the vbox
        VBox vBox = new VBox();
        vBox.getChildren().addAll(search_bar, Stock_info);

        // button stop
        Button stop_execution = new Button();
        stop_execution.setGraphic(new ImageView(this.stop));
        stop_execution.setOnAction(e -> user.stop());

        // button add
        Button add_stock = new Button();
        add_stock.setGraphic(new ImageView(this.add));
        add_stock.setOnAction(e -> user.ADD(Code));

        // button drop
        Button drop_stock = new Button();
        drop_stock.setGraphic(new ImageView(this.add));
        drop_stock.setOnAction(e -> user.DROP(Code));

        // button go_back
        Button go_back = new Button();
        go_back.setOnAction(e -> stage.setScene(scene_initial));

        // setting the scene background
        borderPane.setTop(portfolio_viewer);
        borderPane.setCenter(vBox);
        borderPane.setBottom(stop_execution);
        borderPane.setLeft(add_stock);
        borderPane.setRight(drop_stock);


        //setting up scene portfolio
        VBox vBox1 = new VBox();
        VBox temp = new VBox();
        Label information = new Label();
        information.setText("This is a list of all the stocks currently in your portfolio");
        Label[] labels = new Label[portfolio_read.Noted_Stocks.size()];
        for (int i = 0; i <= portfolio_read.Noted_Stocks.size(); i++)
        {
            labels[i] = new Label();
            labels[i].setText(portfolio_read.Noted_Stocks.get(i).toString());
            temp.getChildren().addAll(labels[i]);
        }
        vBox1.getChildren().addAll(information, temp, go_back);

        scene_initial = new Scene(borderPane);
        stage.setScene(scene_initial);
        stage.setTitle("Kuberā");

        stage.show();

        this.user.startListener();
    }

    /**
     * severs client connection
     */
    @Override
    public void stop()
    {
        this.user.close();
    }

    /**
     * Helps keep updating the GUI as per board changes
     */
    private void refresh()
    {
//        WAMBoard.Status status = board.getStatus();
//        switch (status)
//        {
//            case ERROR:
//                this.gamestatus.setText(status.toString());
//                break;
//            case GAME_WON:
//                this.gamestatus.setText( "You won. Yay!" );
//                break;
//            case GAME_LOST:
//                this.gamestatus.setText( "You lost. Boo!" );
//                break;
//            case GAME_TIED:
//                this.gamestatus.setText( "Tie game. Meh." );
//                break;
//            case NOT_OVER:
//                this.gamestatus.setText("Score: " + client.score);
//                break;
//            default:
//                this.gamestatus.setText(" ");
//        }
//        help_refresh();
    }


    @Override
    public void update(Lakshmi lakshmi)
    {
        if ( Platform.isFxApplicationThread() )
        {
            this.refresh();
        }
        else
        {
            Platform.runLater(this::refresh);
        }
    }

    /**
     * Main method
     * @param args j
     */
    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.out.println("Usage: java GUI host port");
            System.exit(-1);
        }
        else
        {
            Application.launch(args);
        }
    }

}
