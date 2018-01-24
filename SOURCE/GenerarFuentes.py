# -*- coding: utf-8 -*-

import PIL
from PIL import ImageFont
from PIL import Image
from PIL import ImageDraw
import sys
import string

import glob




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
        im.save("letras_fuente/" + letra + "_" + fuenteB + ".png")
    
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
        im.save("letras_fuente/" + letra + "_UPPER_" + fuenteB + ".png")
    