import pandas as pd
import numpy as np
import tensorflow as tf
import matplotlib.pyplot as plt
from sklearn.preprocessing import MinMaxScaler
from keras.models import Sequential
from keras.layers import Dense,Dropout,LSTM

def main():
    # Reading the data file
    NYSE_Dataframe = pd.read_csv("^NYA-new.csv")
    # Converting the date from a string to a datetime format
    NYSE_Dataframe["Date"] = pd.to_datetime(NYSE_Dataframe.Date)
    NYSE_Dataframe.index = NYSE_Dataframe["Date"]
    print(NYSE_Dataframe.head(100))
    # sorting the data oldest to newest
    Data = NYSE_Dataframe.sort_index()

    # making new series for new data
    Dates = pd.Series(Data.Date)
    Prices = pd.Series(Data.Open)
    # making a dictionary
    frame = {"Date":Dates, "Price":Prices}
    # making the dictionary into a new dataframe containing only dates/close values
    new_data = pd.DataFrame(frame)

    # Converting our value to float
    # for i in range(0, len(new_data.Price)):
    #     new_data.Price[i] = float(new_data.Price[i].replace(',', ''))

    # removing the date field before we do any machine learning
    new_data = new_data.drop("Date", 1)

    # making numpy array
    Prices_new = new_data.Price
    Prices_numpy = Prices_new.to_numpy()

    dataset = []
    for i in range(0, len(Prices_numpy) - 1):
        dataset.append([Prices_numpy[i]])

    # we will now normalise the data to allow the ML model to converge easily
    # we will use the minmax scaler to get all data in range (0,1)
    scaler = MinMaxScaler()
    scaled_data = scaler.fit_transform(dataset)
    # splitting the data into training and validation data
    training_data = new_data[:945]
    validation_data = new_data[945:]

    # LSTM's expect to receive data in a 3D array. Since we only have  a single array
    # we will start by creating data in 60 time stamps and converting it to an array
    # using NumPy. Next we Convert the data into a 3 dimensional array with
    # x_train samples, 60 timestamps and one feature at each step
    x_train, y_train = [], []
    for i in range(60, len(training_data)):
        x_train.append(scaled_data[i - 60:i, 0])
        y_train.append(scaled_data[i, 0])
    x_train, y_train = np.array(x_train), np.array(y_train)

    x_train = np.reshape(x_train, (x_train.shape[0], x_train.shape[1], 1))

    # creating a new model
    model = Sequential()
    model.add(LSTM(50))
    model.add(Dense(1))
    model.compile("rmsprop", "mse")
    model.fit(x_train,y_train,1)
    #
    # In order to predict future stock prices we need to do a couple of things
    # after loading in the test set:
    #
    # 1. Merge the training set and the test set on the 0 axis.
    # 2. Set the time step as 60 (as seen previously)
    # 3. Use MinMaxScaler to transform the new dataset
    # 4. Reshape the dataset as done previously
    # 5. After making the predictions we use inverse_transform to get back the
    # stock prices in normal readable format.
    inputs = new_data[len(new_data) - len(validation_data) - 60:].values
    inputs = inputs.reshape(-1, 1)
    inputs = scaler.transform(inputs)

    X_test = []
    for i in range(60, inputs.shape[0]):
        X_test.append(inputs[i - 60:i, 0])
    X_test = np.array(X_test)

    X_test = np.reshape(X_test, (X_test.shape[0], X_test.shape[1], 1))
    closing_price = model.predict(X_test)
    closing_price = scaler.inverse_transform(closing_price)

    train = new_data[:945]
    valid = new_data[945:]
    valid['Predictions'] = closing_price
    plt.plot(train['Price'])
    plt.plot(valid[['Price', 'Predictions']])
    plt.show()

main()