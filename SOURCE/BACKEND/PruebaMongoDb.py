# -*- coding: utf-8 -*-
"""
Created on Wed Apr 11 17:37:41 2018

@author: Capitan Webrels
"""

from Generador_final import Generador
import matplotlib.pyplot as plt
from bson.binary import Binary
import pickle
import pymongo
import base64
from PIL import Image
import numpy as np
from io import BytesIO
import scipy.misc

client = pymongo.MongoClient()

db = client["VAE_FUENTES"]

usuarios = db['usuarios']

res = usuarios.find_one_and_update({ "ID": "Barbassss" }, { "$set" :{ "pass": '12345' }})

"""
amigo = Generador()

fuente = amigo.generarFuente()

#img= Image.fromarray(fuente, 'L') #no se pasa bien a image (improbable)

buffered = BytesIO()
scipy.misc.imsave(buffered, fuente, format="png") #NO se guarda bien
#AQUI UN IMSHOW DEESOS

img_str = base64.b64encode(buffered.getvalue())


mm= Image.open(BytesIO(base64.b64decode(img_str))).convert("L") #No se abre bien

xx = np.asarray(mm,dtype=np.float64) #No se abre bien como array (improbable)

plt.figure(figsize=(10, 10))
plt.imshow(xx, cmap='Greys_r')
plt.show()


hola = Binary(pickle.dumps( fuente, protocol=2))


client = pymongo.MongoClient()

db = client["VAE_FUENTES"]

collection = db['usuarios']

collection.insert_one({"ID":"Barbassss","pass":"12345"})

jeje = collection.find_one({"ID":"Barbassss"})

adios = pickle.loads(hola)

plt.figure(figsize=(10, 10))
plt.imshow(adios, cmap='Greys_r')
plt.show()
"""