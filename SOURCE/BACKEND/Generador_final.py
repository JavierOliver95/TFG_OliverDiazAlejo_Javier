# -*- coding: utf-8 -*-
"""
Created on Mon Apr  9 17:22:48 2018

@author: Capitan Webrels
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
from bson.binary import Binary
import pickle
import pymongo

class Generador:
    letrasMinus = []
    MongoCliente=""
    bbdd = ""
    letrasMayus = []
    
    MODELO="Modelos/modelo_"
    MODELO1="_minus"
    MODELO2="_mayus"
    batch_size = 200
    original_dim = 784
    latent_dim = 2
    intermediate_dim = 256
    # build a digit generator that can sample from the learned distribution
    
    n = 1000  # figure with 15x15 digits
    
    digit_size = 50
    
    # linearly spaced coordinates on the unit square were transformed through the inverse CDF (ppf) of the Gaussian
    # to produce values of the latent variables z, since the prior of the latent space is Gaussian
    grid_x = norm.ppf(np.linspace(0.05, 0.95, n))
    grid_y = norm.ppf(np.linspace(0.05, 0.95, n))
    
    def __init__(self):
        self.MongoCliente = pymongo.MongoClient()
        self.bbdd = self.MongoCliente["VAE_FUENTES"]
        for letra in list(string.ascii_lowercase):
            MODELFIN=self.MODELO+letra+self.MODELO1
            generator = load_model(MODELFIN)
            self.letrasMinus.append(generator)
            
        for letra in list(string.ascii_uppercase):
            MODELFIN2=self.MODELO+letra+self.MODELO2
            generator = load_model(MODELFIN2)
            self.letrasMayus.append(generator)
    
    def guardarFuente(self, fuente, idFuente, idUser=None):
        self.MongoCliente = pymongo.MongoClient()
        self.bbdd = self.MongoCliente["VAE_FUENTES"]
        fuenteCod = Binary(pickle.dumps( fuente, protocol=2))
        collection = self.bbdd['fuentes']
        if idUser==None:
           result = collection.insert_one({"IDFuente":idFuente,"arrayFuente":fuenteCod, "idUser":""})
        else:
           result = collection.insert_one({"IDFuente":idFuente,"arrayFuente":fuenteCod, "idUser":idUser})

        self.MongoCliente.close()
        return result
    
    def getFuente(self,nombre):
        self.MongoCliente = pymongo.MongoClient()
        self.bbdd = self.MongoCliente["VAE_FUENTES"]
        collection = self.bbdd['fuentes']
        fuenteCod = collection.find_one({"IDFuente":nombre})
        fuente = pickle.loads(fuenteCod["arrayFuente"])
    
        self.MongoCliente.close()
    
        return fuente
    
        
    def borrarFuente(self,nombre):
        self.MongoCliente = pymongo.MongoClient()
        self.bbdd = self.MongoCliente["VAE_FUENTES"]
        collection = self.bbdd['fuentes']
        result = collection.delete_one({"IDFuente":nombre})
        
        self.MongoCliente.close()
        
        return result
    
    def generarFuente(self, x, y):
        cadena="abcdefghijklmnopqrstuvwxyz"
        digit_size = self.digit_size
        
        x_sel = x
        y_sel = y
        
        figure = np.zeros((digit_size * 2, digit_size * len(cadena)))
        
        posicion=0;
        
        for s in cadena:
            

            i = string.ascii_lowercase.index(s)
            generador=self.letrasMinus[i]

            j= string.ascii_uppercase.index(s.upper())
            generador2=self.letrasMayus[j]
            
            z_sample = np.array([[x_sel, y_sel]])
            x_decoded = generador.predict(z_sample)
            x_decoded2 = generador2.predict(z_sample)
            digit = x_decoded[0].reshape(digit_size, digit_size)
            digit2 = x_decoded2[0].reshape(digit_size, digit_size)
            
            
            figure[0 * digit_size: 1 * digit_size,
                   posicion * digit_size: (posicion + 1) * digit_size] = digit
                   
            figure[1 * digit_size: 2 * digit_size,
                   posicion * digit_size: (posicion + 1) * digit_size] = digit2
        
            posicion+=1
            
        return figure
        
    def escribirFuenteNueva(self, texto, x, y):
        fuente = self.generarFuente(x, y)
        digit_size = self.digit_size
        
        x_dib=0
        y_dib=0
        
        lineas = int(len(texto)/50) + texto.count('\n') +1
        if (len(texto)>50):
            columnas = 50
        else:
            columnas = len(texto)
        figure = np.zeros((digit_size * lineas, digit_size * columnas))
        
        for s in texto:
            if s=="ñ":
                s="n"
            if s.isalnum():
                if s.islower():
                    index=0
                    index_letra=string.ascii_lowercase.index(s)
                elif s.isupper():
                    index=1
                    index_letra=string.ascii_uppercase.index(s)
                    
                digit=fuente[index * digit_size: (index + 1) * digit_size,
                             index_letra * digit_size: (index_letra + 1) * digit_size]
                

                figure[x_dib * digit_size: (x_dib + 1) * digit_size,
                       y_dib * digit_size: (y_dib + 1) * digit_size]=digit

                
            y_dib+=1
            if (y_dib>255 or s=='\n'):
                y_dib=0
                x_dib+=1
        
        return figure

    def EscribirFuente(self, texto, idFuente):
        digit_size = self.digit_size
        self.MongoCliente = pymongo.MongoClient()
        self.bbdd = self.MongoCliente["VAE_FUENTES"]
        collection=self.bbdd["fuentes"]
        fuenteCod = collection.find_one({"IDFuente":idFuente})
        fuente = pickle.loads(fuenteCod["arrayFuente"])
        
        x_dib=0
        y_dib=0
        
        lineas = int(len(texto)/50) + texto.count('\n') +1
        if (len(texto)>50):
            columnas = 50
        else:
            columnas = len(texto)
        figure = np.zeros((digit_size * lineas, digit_size * columnas))
        
        for s in texto:
            if s=="ñ":
                s="n"
            if s.isalnum():
                if s.islower():
                    index=0
                    index_letra=string.ascii_lowercase.index(s)
                elif s.isupper():
                    index=1
                    index_letra=string.ascii_uppercase.index(s)
                    
                digit=fuente[index * digit_size: (index + 1) * digit_size,
                             index_letra * digit_size: (index_letra + 1) * digit_size]
                

                figure[x_dib * digit_size: (x_dib + 1) * digit_size,
                       y_dib * digit_size: (y_dib + 1) * digit_size]=digit

                
            y_dib+=1
            if (y_dib>(columnas-1) or s=='\n'):
                y_dib=0
                x_dib+=1
        
        self.MongoCliente.close()
        
        return figure

    
        
    
    
        