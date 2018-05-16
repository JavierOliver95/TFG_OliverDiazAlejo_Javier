# -*- coding: utf-8 -*-
"""
Created on Mon Mar 19 18:30:59 2018

@author: Barbassss
"""
import os
import random
import string
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import norm
from keras.preprocessing.image import ImageDataGenerator
from keras.layers import Input, Dense, Lambda, Layer
from keras.models import Model
from keras.models import load_model
from keras import backend as K
from keras import metrics
from keras.datasets import mnist

MODELO="modelo_"
MODELO1="_minus"
batch_size = 200
original_dim = 784
latent_dim = 2
intermediate_dim = 256
# build a digit generator that can sample from the learned distribution

n = 20  # figure with 15x15 digits
tama=6
digit_size = 28
figure = np.zeros((digit_size * tama, digit_size * tama))
# linearly spaced coordinates on the unit square were transformed through the inverse CDF (ppf) of the Gaussian
# to produce values of the latent variables z, since the prior of the latent space is Gaussian
grid_x = norm.ppf(np.linspace(0.05, 0.95, n))
grid_y = norm.ppf(np.linspace(0.05, 0.95, n))

x_dib=0
y_dib=0

for letra in list(string.ascii_lowercase):
    MODELFIN=MODELO+letra+MODELO1
    generator = load_model(MODELFIN)
    x_sel = random.choice(list(enumerate(grid_x)))[1]
    y_sel = random.choice(list(enumerate(grid_y)))[1]
    z_sample = np.array([[x_sel, y_sel]])
    x_decoded = generator.predict(z_sample)
    digit = x_decoded[0].reshape(digit_size, digit_size)
    figure[x_dib * digit_size: (x_dib + 1) * digit_size,
           y_dib * digit_size: (y_dib + 1) * digit_size] = digit
                
    y_dib+=1
    if(y_dib==tama):
        y_dib=0
        x_dib+=1

plt.figure(figsize=(10, 10))
plt.imshow(figure, cmap='Greys_r')
plt.show()

