# -*- coding: utf-8 -*-

import PIL
from PIL import ImageFont
from PIL import Image
from PIL import ImageDraw
import sys
import string
import os

import glob


for number in range(0, 7):
    
    for letra in list(string.ascii_lowercase):
        
        for fuente in glob.glob("C:/Users/Barbassss/Documents/GitHub/TFG_OliverDiazAlejo_Javier/SOURCE/pilfonts/*.pil"):
            
            fuenteB = fuente.split("\\")[1]
        
            im = Image.new("RGBA",(28,28))
            
            draw = ImageDraw.Draw(im)
            
            # use a bitmap font
            font = ImageFont.load("C:/Users/Barbassss/Documents/GitHub/TFG_OliverDiazAlejo_Javier/SOURCE/pilfonts/" + fuenteB)
            
            draw.text((5, 5), letra , font=font, lign="center")
            
            # use a truetype font
            draw = ImageDraw.Draw(im)
            if number >= 5:
                path="letras_fuente/" + letra + "/validation"
            else:
                path= "letras_fuente/" + letra + "/train"
                
            if not os.path.exists(path):
                if not os.path.exists("letras_fuente/" + letra):
                    os.mkdir("letras_fuente/" + letra)
                os.mkdir(path)
            im.save(path+"/" + str(number) + "_" + fuenteB + ".png")
        
    for letra in list(string.ascii_uppercase):
        
        for fuente in glob.glob("C:/Users/Barbassss/Documents/GitHub/TFG_OliverDiazAlejo_Javier/SOURCE/pilfonts/*.pil"):
            
            fuenteB = fuente.split("\\")[1]
        
            im = Image.new("RGBA",(28,28))
            
            draw = ImageDraw.Draw(im)
            
            # use a bitmap font
            font = ImageFont.load("C:/Users/Barbassss/Documents/GitHub/TFG_OliverDiazAlejo_Javier/SOURCE/pilfonts/" + fuenteB)
            
            draw.text((5, 5), letra , font=font, lign="center")
            
            # use a truetype font
            draw = ImageDraw.Draw(im)
            if number >= 5:
                path="letras_fuente/" + letra + "_UPPER_" + "/validation"
            else:
                path= "letras_fuente/" + letra + "_UPPER_" + "/train"
                
            if not os.path.exists(path):
                if not os.path.exists("letras_fuente/" + letra + "_UPPER_"):
                    os.mkdir("letras_fuente/" + letra + "_UPPER_")
                os.mkdir(path)
            im.save(path + "/"+ str(number) + "_" + fuenteB + ".png")
        