# -*- coding: utf-8 -*-
"""
Created on Tue Apr 17 17:11:28 2018

@author: Capitan Webrels
"""

from tornado.wsgi import WSGIContainer
from tornado.httpserver import HTTPServer
from tornado.ioloop import IOLoop
from APIREST import app

http_server = HTTPServer(WSGIContainer(app))
http_server.listen(5001)
IOLoop.instance().start()