# -*- coding: utf-8 -*-
"""
Created on Mon Apr 16 17:27:02 2018

@author: Capitan Webrels
"""
from Generador_final import Generador
from flask import Flask, Response, jsonify, request
from bson import json_util
import pymongo
import base64
from PIL import Image
import numpy as np
from io import BytesIO
import scipy.misc

generador=""
app = Flask(__name__)

@app.route('/login/<user>/<password>')
def login(user,password):
    client = pymongo.MongoClient()
    bbdd = client["VAE_FUENTES"]
    collection = bbdd['usuarios']
    
    _data = collection.find_one({"ID":user,"pass":password})
    
    ret = json_util.dumps({'users':  _data}, default=json_util.default)

    client.close()
    
    return Response(response=ret,
                    status=200,
                    headers=None,
                    content_type='application/json',
                    direct_passthrough=False)

@app.route('/new/<user>/<password>')
def signIn(user,password):
    client = pymongo.MongoClient()
    bbdd = client["VAE_FUENTES"]
    collection = bbdd['usuarios']
    
    exist = collection.find({"ID":user})
    
    client.close()
    
    if(exist.count()>=1):
        return jsonify({'error': 'El usuario ya existe'})
    else:
        collection.insert_one({"ID":user,"pass":password})
        
        return jsonify({'status': 'successful'})

@app.route('/pass/<user>/<password>/<newpass>')
def modificarCUenta(user,password,newpass):
    client = pymongo.MongoClient()
    bbdd = client["VAE_FUENTES"]
    usuarios = bbdd['usuarios']
    
    usuarios.find_one_and_update({ "ID": user, "pass":password }, { "$set" :{ "pass": newpass }})
    
    client.close()
    
    return jsonify({'status': 'successful'})


@app.route('/del/<user>')
def borrarCuenta(user):
    client = pymongo.MongoClient()
    bbdd = client["VAE_FUENTES"]
    usuarios = bbdd['usuarios']
    fuentes = bbdd['fuentes']
    
    usuarios.delete_one({"ID":user})
    fuentes.delete_many({"idUser":user})
    
    client.close()

    return jsonify({'status': 'successful'})

@app.route('/fuenteG/<name>/',methods=['POST'])
@app.route('/fuenteG/<name>/<user>',methods=['POST'])
def guardarFuente(name,user=""):
    data = request.get_json()
    mm= Image.open(BytesIO(base64.b64decode(data['data'].encode('utf-8')))).convert("L") #No se abre bien


    fuenteNumpy = np.asarray(mm, dtype=np.float64) #No se abre bien como array (improbable)
    
    if len(user)==0:
        generador.guardarFuente(fuenteNumpy,name)
    else:
        generador.guardarFuente(fuenteNumpy,name,user)
                
    return jsonify({'status': 'successful'})

@app.route('/fuenteD/<name>')
def borrarFuente(name):
    generador.borrarFuente(name)
    
    return jsonify({'status': 'successful'})

@app.route('/fuente/<name>')
def getFuente(name):
    fuente = generador.getFuente(name)
    
    buffered = BytesIO()
    scipy.misc.imsave(buffered, fuente, format="png") 

    img_str = base64.b64encode(buffered.getvalue())
        
    return jsonify({'fuente': img_str.decode('utf-8')})

@app.route('/fuente/<x>/<y>')
def generarFuente(x, y):
    ff = generador.generarFuente(x,y)
    
    buffered = BytesIO()
    scipy.misc.imsave(buffered, ff, format="png") 

    img_str = base64.b64encode(buffered.getvalue())
        
    return jsonify({'fuente': img_str.decode('utf-8')})
    
@app.route('/texto/<tex>/<x>/<y>')
def escribirFuenteNueva(tex,x,y):
    ff = generador.escribirFuenteNueva(tex,x,y)
    
    buffered = BytesIO()
    scipy.misc.imsave(buffered, ff, format="png") 

    img_str = base64.b64encode(buffered.getvalue())
        
    return jsonify({'texto': img_str.decode('utf-8')})

@app.route('/texto/<tex>/<name>')
def escribirFuente(tex,name):
    ff = generador.EscribirFuente(tex,name)
    
    buffered = BytesIO()
    scipy.misc.imsave(buffered, ff, format="png") 

    img_str = base64.b64encode(buffered.getvalue())
        
    return jsonify({'texto': img_str.decode('utf-8')})

@app.route('/generales')
@app.route('/generales/<usuario>')
def getFuentes(usuario=""):
    listaF = []
    client = pymongo.MongoClient()
    bbdd = client["VAE_FUENTES"]
    collection = bbdd['fuentes']
    
    res = collection.find({"idUser":usuario})
    
    for i in res:
        fuentePy = generador.EscribirFuente(i["IDFuente"],i["IDFuente"])
        
        buffered = BytesIO()
        scipy.misc.imsave(buffered, fuentePy, format="png") 

        img_str = base64.b64encode(buffered.getvalue())
        
        listaF.append({"fuente":img_str.decode('utf-8'),"name": i["IDFuente"]})
        
    client.close()    
    
    return jsonify({"fuentesG":listaF})

if __name__ == '__main__':
    generador=Generador()
    app.run(host='0.0.0.0')