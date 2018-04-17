# -*- coding: utf-8 -*-
"""
Created on Mon Apr 16 17:27:02 2018

@author: Capitan Webrels
"""

from flask import Flask, request, Response, jsonify
import json
from bson import json_util
from pymongo import MongoClient

app = Flask(__name__)

@app.route('/')
def home():
    return 'All OKAY!'


if __name__ == '__main__':
    app.run()