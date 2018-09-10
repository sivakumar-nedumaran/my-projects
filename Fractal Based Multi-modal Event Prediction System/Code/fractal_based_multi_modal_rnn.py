from sklearn.metrics import confusion_matrix
import matplotlib.pyplot as plt
from keras.models import Sequential
from keras.layers import Dense
import numpy as np
import h5py
import time
from keras import optimizers
import datetime
import itertools

import pickle #as cPickle
# fix random seed for reproducibility
np.random.seed(7)

dataset = np.loadtxt("./dataset_new_long.csv", delimiter=",")
adam = optimizers.Adam(lr=0.1, beta_1=0.9, beta_2=0.999, epsilon=None, decay=0.0, amsgrad=False)


data_num = dataset.shape[0]
random_index = np.random.permutation(data_num)

data = []

for i in range(data_num):
    data.append(dataset[random_index[i],:])
		
data = np.array(data)
print(data.shape)

X_train = data[:3000,0:17]
X_val = data[3000:,0:17]

Y_train = data[:3000,17:]
Y_val = data[3000:,17:]
    
print(X_train.shape)
print(Y_train.shape)

def plot_confusion_matrix(cm, classes,
                          normalize=False,
                          title='Confusion matrix',
                          cmap=plt.cm.Blues):
    """
    This function prints and plots the confusion matrix.
    Normalization can be applied by setting `normalize=True`.
    """
    if normalize:
        cm = cm.astype('float') / cm.sum(axis=1)[:, np.newaxis]
        print("Normalized confusion matrix")
    else:
        print('Confusion matrix, without normalization')

    print(cm)

    plt.imshow(cm, interpolation='nearest', cmap=cmap)
    plt.title(title)
    plt.colorbar()
    tick_marks = np.arange(len(classes))
    plt.xticks(tick_marks, classes, rotation=45)
    plt.yticks(tick_marks, classes)

    fmt = '.2f' if normalize else 'd'
    thresh = cm.max() / 2.
    for i, j in itertools.product(range(cm.shape[0]), range(cm.shape[1])):
        plt.text(j, i, format(cm[i, j], fmt),
                 horizontalalignment="center",
                 color="white" if cm[i, j] > thresh else "black")

    plt.tight_layout()
    plt.ylabel('True label')
    plt.xlabel('Predicted label')

# create model
model = Sequential()
model.add(Dense(512, input_dim=17, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(64, activation='relu'))
model.add(Dense(4, activation='sigmoid'))

# Compile model
model.compile(loss='categorical_crossentropy', metrics=['accuracy'], optimizer=adam)

# Fit the model
history = model.fit(X_train, Y_train, epochs=5, batch_size=10, validation_data=(X_val,Y_val))

timestamp = '{}'.format(datetime.datetime.fromtimestamp(time.time()).strftime('%Y-%m-%d %H %M %S'))

model_pickle_path = './model_checkpoints/model_sn_{}.h5'.format(timestamp)
model.save(model_pickle_path)

#plot accuracy

acc = history.history['acc']
val_acc = history.history['val_acc']

plt.plot(acc)
plt.plot(val_acc)
plt.title('model accuracy')
plt.ylabel('accuracy')
plt.xlabel('epoch')
plt.legend(['train'], loc='upper left')
plt.savefig('./plots/accuracy_{}.png'.format(timestamp))
plt.show()


loss = history.history['loss']
val_loss = history.history['val_loss']

plt.plot(loss)
plt.plot(val_loss)
plt.title('model loss')
plt.ylabel('loss')
plt.xlabel('epoch')
plt.legend(['train'], loc='upper left')
plt.savefig('./plots/loss_{}.png'.format(timestamp))
plt.show()

# calculate predictions
predictions = model.predict(X_val)
# round predictions
# rounded = [round(x[0]) for x in predictions]
# print(predictions)
# print(Y_val)
                      

# evaluate the model
scores = model.evaluate(X_val, Y_val)
print("\n%s: %.2f%%" % (model.metrics_names[1], scores[1]*100))