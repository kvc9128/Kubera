# Kubera
A simple project aimed at improving my knowldege of Java OOP concepts while building upon my newfound skill of webscraping 
using an external API(HTMLUnit) for the first time. I also hope to test some machine learning models that I will develop as 
part of this project. The ML Model will aim to develop a stock market predictor. (Goal still undefined. Intend to use a 
training data set of 2014-2018 NYSE data and predict for 2019). Possible use of TensorFlow to model and then call using 
this program.

List of Classes used and expected to be built :

Step1: Class Server.Raavan
 *          1. Get stocks from EODDATA - Complete
 *          2. Store them in appropriate data structure - complete
 *              a. Small mid large cap division - TBD
 *              b. top 100 companies - TBD
 *              c. bottom 100 companies - TBD
 *              d. Average Market Capitalization - TBD
 *              e. Find a stock - TBD
 *              f. Access above functions - TBD
 *
Step2: Class Server.Stock 
 *          1. Used to represent an individual stock ticker
 *
Step3: Class Server.Lakshmi
 *          1. Used to represent one stock market. Currently the NYSE. Depicted as an individual class as it allows for expansion
 *
Step4: Class X 
 *          1. Represent a user's portfolio and hold a bunch of stocks that the user wants
 *          2. Allow the user to track and follow his stocks and portfolio
 *          3. Represent user's wealth and resources
 *          4. Offer a visual representation of portfolio.(possible GUI)
 *          5. Class Z can be used here to further augment stock's value
 *
Step5: Class Y
 *          1. Machine learning model will be here
 *          2. Training set and testing set will be stored here
 *          3. Buy EOD NYSE 5Yr records and build a system to predict stock price
 *          4. The goal will be to predict a company;s stock price based on various factors
 *          5. Aim to be 0.80 or 1.20 of NYSE Composite
 *          6. We will use the training set to teach the model
 *          7. The testing model will be used to check the model
 *
Step6: Class Z
 *          1. This is a supporting class for class Y
 *          2. This class will be similar to raavan in the sense that it will also be accessing
 *             the web and be a social media taste checker
 *          3. This class will scan twitter to check for financial updates, company's
 *             trending values etc.
 *          4. Possible use of neural nets here
