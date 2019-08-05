import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

from sklearn.preprocessing import MinMaxScaler

import tensorflow as tf
from keras.models import Sequential
from keras.layers import Dense,Dropout,LSTM

def main():
    # reading data from file
    tata_global_dataframe = pd.read_csv("Dataset")
    tata_global_dataframe["Date"] = pd.to_datetime(tata_global_dataframe.Date)
    tata_global_dataframe.index = tata_global_dataframe["Date"]

    # sorting the data oldest to newest
    data = tata_global_dataframe.sort_index()

    # making new series for new data
    Dates = pd.Series(data.Date)
    Closes = pd.Series(data.Close)
    # making a dictionary
    frame = {"Date":Dates, "Close":Closes}
    # making the dictionary into a new dataframe containing only dates/close values
    new_data = pd.DataFrame(frame)

    new_data = new_data.drop("Date", 1)

    # making numpy array
    Closes_numpy = Closes.to_numpy()

    dataset = []
    for i in range(0, len(Closes_numpy) - 1):
        dataset.append([Closes_numpy[i]])

    # we will now normalise the data to allow the ML model to converge easily
    # we will use the minmax scaler to get all data in range (0,1)
    scaler = MinMaxScaler()
    scaled_data = scaler.fit_transform(dataset)

    training_data = new_data[:987]
    validation_data = new_data[987:]
    # IDK what the fuck happens in this. Best guess making it a 3d array
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

    train = new_data[:987]
    valid = new_data[987:]
    valid['Predictions'] = closing_price
    plt.plot(train['Close'])
    plt.plot(valid[['Close', 'Predictions']])
    plt.show()


main()