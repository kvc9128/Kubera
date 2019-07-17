import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * This is the class we will be using to access EODDATA
 * to get daily stock information. This class will be responsible for gathering and storing the
 * stock tickers for all the stocks in the NYSE. This class will then call other classes
 * to analyse the data.
 *
 * Step1: Class Raavan
 *          1. Get stocks from EODDATA - Complete
 *          2. Store them in appropriate data structure - complete
 *              a. Small mid large cap division - TBD
 *              b. top 100 companies - TBD
 *              c. bottom 100 companies -TBD
 *              d. Average Market Capitalization -TBD
 *
 * Step2: Class X - TBD
 *          1. Represent a user's portfolio and hold a bunch of stocks that the user wants
 *          2. Allow the user to track and follow his stocks and portfolio
 *          3. Represent user's wealth and resources
 *          4. Offer a visual representation of portfolio.(possible GUI)
 *          5. Class Z can be used here to further augment stock's value
 *
 * Step3: Class Y
 *          1. Machine learning model will be here
 *          2. Training set and testing set will be stored here
 *          3. Buy EOD NYSE 5Yr records and build a system to predict stock price
 *          4. The goal will be to predict a company;s stock price based on various factors
 *          5. Aim to be 0.80 or 1.20 of NYSE Composite
 *          6. We will use the training set to teach the model
 *          7. The testing model will be used to check the model
 *
 * Step4: Class Z
 *          1. This is a supporting class for class Y
 *          2. This class will be similar to raavan in the sense that it will also be accessing
 *             the web and be a social media taste checker
 *          3. This class will scan twitter to check for financial updates, company's
 *             trending values etc.
 *          4. Possible use of neural nets here
 */
public class Raavan
{
    // a webclient. It will be used as a web browser. We can enter website url into it
    public static WebClient web;
    public static List<HtmlPage> pages = new ArrayList<>();
    public static Map<String, Stock> NYSE = new HashMap<>();
    public static String path = "Stock data";
    public static boolean append_to_file = true;
    public static PrintWriter print_line;

    /**
     * Constructor that generates a new web page
     */
    public Raavan()
    {
        web = new WebClient();
        // we will set CSS and Javascript to false as we do not want to deal with that
        web.getOptions().setCssEnabled(false);
        web.getOptions().setJavaScriptEnabled(false);
        try
        {
            //we will also write to a text file so that we can do further operations on it
            FileWriter write = new FileWriter(path, append_to_file);
            //since Filewriter parses bytes, we will use print writer to give it a line of text
            PrintWriter print_line = new PrintWriter(write);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * The main function which calls all the functions and handles all issues
     * @param args not used here
     */
    public static void main(String[] args)
    {
        // generates the web browser
        new Raavan();
        //generates the web pages of stocks alphabetically
        GenerateWebpages();
           try{
               //function that creates each stock
               FirstAlpha(pages);
           } catch (Exception e) {
               e.printStackTrace();
           }
    }

    /**
     * Main driver Program that creates a Stock item individually
     * @param pages list is used which is generated by generate Web pages function
     */
    public static void FirstAlpha(List<HtmlPage> pages)
    {
        try
        {
            for (HtmlPage page:pages) // for each page in the list if web pages
            {
                // we first get the table body that contains all the information using the XPath
                HtmlTableBody tableBody = page.getFirstByXPath("//*[@id=\"ctl00_cph1_divSymbols\"]/table/tbody");
                // we then get all the rows in the table. Each row denotes a Stock
                List<HtmlTableRow> rows = tableBody.getRows();
                int i = 2;
                // here we remove the topmost row which represents the uppermost demarcation row
                rows.remove(0);
                //for each row,
                    for (HtmlTableRow row:rows)
                    {
                        // all the variables and data we expect to receive
                        String Code, Name, High, Low, Close, Volume, DivYield, Earning_per_share, Price_Earnings_to_growth_ratio, MarketCapitalization;
                        // we get all the cells in each row
                        List<HtmlTableCell> cells = row.getCells();
                        // we get all the data we can on that page
                        Code = String.valueOf(cells.get(0).asText());
                        Name = String.valueOf(cells.get(1).asText());
                        High = String.valueOf(cells.get(2).asText());
                        Low = String.valueOf(cells.get(3).asText());
                        Close = String.valueOf(cells.get(4).asText());
                        // Here we try to extract the Href of the Code of the stock so that we can get more data first we get the HtmlAnchor
                        HtmlAnchor Detailedanchor = cells.get(0).getFirstByXPath("//*[@id=\"ctl00_cph1_divSymbols\"]/table/tbody/tr[" + i + "]/td[1]/a");
                        //now we will get the Href attribute of the anchor
                        String DetailedURL = Detailedanchor.getHrefAttribute();
                        //now we create a web page of the specific company by putting together standard url and Href attribute
                        HtmlPage DetailedShare = web.getPage("http://eoddata.com" + DetailedURL);

                        // Here we obtain more complex values like the Volume of shares, Dividend yield etc.
                        HtmlTableBody body = DetailedShare.getFirstByXPath("//*[@id=\"ctl00_cph1_divFundamentals\"]/table/tbody");
                        List<HtmlTableRow> detailrows = body.getRows();
                        //get details about total volume of shares
                        List<HtmlTableCell> volumecells = detailrows.get(9).getCells();
                        Volume = volumecells.get(1).asText().equals("N/A")? "0.0": volumecells.get(1).asText();
                        //get details about dividend yield of the company
                        List<HtmlTableCell> divcells = detailrows.get(5).getCells();
                        DivYield = divcells.get(1).asText().equals("N/A")? "0.0": divcells.get(1).asText();
                        //get details about Earning per share
                        List<HtmlTableCell> EPScells = detailrows.get(4).getCells();
                        Earning_per_share = EPScells.get(1).asText().equals("N/A")? "0.0": EPScells.get(1).asText();
                        //get details about Price/Earning to growth ratio
                        List<HtmlTableCell> PEGcells = detailrows.get(3).getCells();
                        Price_Earnings_to_growth_ratio = PEGcells.get(1).asText().equals("N/A")? "0.0": PEGcells.get(1).asText();
                        //get details about market capitalization of the company
                        List<HtmlTableCell> MarketCapcells = detailrows.get(10).getCells();
                        MarketCapitalization = MarketCapcells.get(1).asText().equals("N/A")? "0.0": MarketCapcells.get(1).asText();

                        // we create a new stock item by calling the constructor and passing items to it
                        Stock stock = new Stock(Code, Name, High, Low, Close, Volume, DivYield, Earning_per_share, Price_Earnings_to_growth_ratio, MarketCapitalization);
                        //we add each individual stock to the Hash map NYSE that contains all the stocks
                        NYSE.put(Code, stock);
                        print_line.print(stock.toString());
                        i++;
                    }
            }
            Lakshmi lakshmi = new Lakshmi(NYSE);
            print_line.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Function that creates a List of all the web pages that contain stock information
     * It is used to populate a list which is then used to access each stock individually.
     */
    public static void GenerateWebpages()
    {
        try
        {
            List<String> searchURL = new ArrayList<>();
            String searchA = "http://eoddata.com/stocklist/NYSE/A.htm";
            searchURL.add(searchA);
            String searchB = "http://eoddata.com/stocklist/NYSE/B.htm";
            searchURL.add(searchB);
            String searchC = "http://eoddata.com/stocklist/NYSE/C.htm";
            searchURL.add(searchC);
            String searchD = "http://eoddata.com/stocklist/NYSE/D.htm";
            searchURL.add(searchD);
            String searchE = "http://eoddata.com/stocklist/NYSE/E.htm";
            searchURL.add(searchE);
            String searchF = "http://eoddata.com/stocklist/NYSE/F.htm";
            searchURL.add(searchF);
            String searchG = "http://eoddata.com/stocklist/NYSE/G.htm";
            searchURL.add(searchG);
            String searchH = "http://eoddata.com/stocklist/NYSE/H.htm";
            searchURL.add(searchH);
            String searchI = "http://eoddata.com/stocklist/NYSE/I.htm";
            searchURL.add(searchI);
            String searchJ = "http://eoddata.com/stocklist/NYSE/J.htm";
            searchURL.add(searchJ);
            String searchK = "http://eoddata.com/stocklist/NYSE/K.htm";
            searchURL.add(searchK);
            String searchL = "http://eoddata.com/stocklist/NYSE/L.htm";
            searchURL.add(searchL);
            String searchM = "http://eoddata.com/stocklist/NYSE/M.htm";
            searchURL.add(searchM);
            String searchN = "http://eoddata.com/stocklist/NYSE/N.htm";
            searchURL.add(searchN);
            String searchO = "http://eoddata.com/stocklist/NYSE/O.htm";
            searchURL.add(searchO);
            String searchP = "http://eoddata.com/stocklist/NYSE/P.htm";
            searchURL.add(searchP);
            String searchQ = "http://eoddata.com/stocklist/NYSE/Q.htm";
            searchURL.add(searchQ);
            String searchR = "http://eoddata.com/stocklist/NYSE/R.htm";
            searchURL.add(searchR);
            String searchS = "http://eoddata.com/stocklist/NYSE/S.htm";
            searchURL.add(searchS);
            String searchT = "http://eoddata.com/stocklist/NYSE/T.htm";
            searchURL.add(searchT);
            String searchU = "http://eoddata.com/stocklist/NYSE/U.htm";
            searchURL.add(searchU);
            String searchV = "http://eoddata.com/stocklist/NYSE/V.htm";
            searchURL.add(searchV);
            String searchW = "http://eoddata.com/stocklist/NYSE/W.htm";
            searchURL.add(searchW);
            String searchX = "http://eoddata.com/stocklist/NYSE/X.htm";
            searchURL.add(searchX);
            String searchY = "http://eoddata.com/stocklist/NYSE/Y.htm";
            searchURL.add(searchY);
            String searchZ = "http://eoddata.com/stocklist/NYSE/Z.htm";
            searchURL.add(searchZ);

            for (String URL:searchURL)
            {
                HtmlPage page = web.getPage(URL);
                pages.add(page);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}