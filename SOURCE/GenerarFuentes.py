# -*- coding: utf-8 -*-

from PIL import Image
from PIL import ImageFont
from PIL import ImageDraw 
import os
import matplotlib.font_manager
from scipy import misc
import numpy as np
import random
import sys
import glob
import string

class DatasetLetters:
    fonts = []
    prohibidas = [227,200,182,338,312,295,275,267,177,160,127,96,52,4,5,13,45,46,48,63,68,75,113,131,141,143,184,191,192,197,203,205,207,213,252,268,326,340,353,367,402,411,419,500,517,519,523,525,528,530,592,610,618,637,641,647,663,672]
    def __init__(self):
        self.fonts = []
        random.seed()
        self.fonts = self.load_fonts()
        

    def load_fonts(self):
		# Get all font file
        flist = matplotlib.font_manager.findSystemFonts(fontpaths=None, fontext='ttf')
        for i in range(len(flist)):
            flist[i]=flist[i].replace("\\","/")
        
        #fonts = [matplotlib.font_manager.FontProperties(fname=fname).get_file() for fname in flist]
        for fname in flist:
            self.fonts.append(matplotlib.font_manager.FontProperties(fname=fname).get_file())
            
        
    		# Using only selected fonts
        """ 
    		fonts = [
    			'C:/Windows/Fonts/AGENCYR.TTF',
    			'C:/Windows/Fonts/AGARAMONDPRO-BOLD.OTF',
    			'C:/Windows/Fonts/ARIAL.TTF',
    			'C:/Windows/Fonts/CALIBRI.TTF',
    			'C:/Windows/Fonts/CAMBRIA.TTC',
    			'C:/Windows/Fonts/CANDARA.TTF',
    			'C:/Windows/Fonts/CENTURY.TTF',
    			'C:/Windows/Fonts/CONSOLA.TTF',
    			'C:/Windows/Fonts/COMIC.TTF',
    			'C:/Windows/Fonts/GEORGIA.TTF'
    			'C:/Windows/Fonts/AGENCYR.TTF',
    			'C:/Windows/Fonts/AGARAMONDPRO-BOLD.OTF',
    			'C:/Windows/Fonts/ARIAL.TTF',
    			'C:/Windows/Fonts/CALIBRI.TTF',
    			'C:/Windows/Fonts/CAMBRIA.TTC',
    			'C:/Windows/Fonts/AGENCYR.TTF',
    			'C:/Windows/Fonts/AGARAMONDPRO-BOLD.OTF',
    			'C:/Windows/Fonts/ARIAL.TTF',
    			'C:/Windows/Fonts/CALIBRI.TTF',
    			'C:/Windows/Fonts/CAMBRIA.TTC',
        		]
        """
        return self.fonts
    def generate_data(self):
        for number in range(0, 10):
            for letra in list(string.ascii_lowercase):
                
                for i in range(len(self.fonts)):
                    
                    #fuenteB = fuente.split("\\")[1]
                    
                    im = Image.new("RGBA",(28,28))
                    
                    draw = ImageDraw.Draw(im)
                    
                    # use a bitmap font
                    #font = ImageFont.load("C:/Users/Barbassss/Documents/GitHub/TFG_OliverDiazAlejo_Javier/SOURCE/pilfonts/" + fuenteB)
                    font = ImageFont.truetype(font=self.fonts[i], size=25)
                    draw.text((0, 0), letra , font=font, lign="center")
                    
                    # use a truetype font
                    draw = ImageDraw.Draw(im)
          
                    path="letras_fuente/" + letra 

                        
                    if not os.path.exists(path):
                        if not os.path.exists("letras_fuente/" + letra):
                            os.mkdir("letras_fuente/" + letra)
                            
                    if not(i in self.prohibidas):
                        im.save(path+"/" + str(i) +"_" + str(number) + ".png")
                    
                    
                        
                    
                
            for letra in list(string.ascii_uppercase):
                
                for i in range(len(self.fonts)):
                    
                    #fuenteB = fuente.split("\\")[1]
                
                    im = Image.new("RGBA",(28,28))
                    
                    draw = ImageDraw.Draw(im)
                    
                    # use a bitmap font
                    #font = ImageFont.load("C:/Users/Barbassss/Documents/GitHub/TFG_OliverDiazAlejo_Javier/SOURCE/pilfonts/" + fuenteB)
                    font = ImageFont.truetype(font=self.fonts[i], size=25)
                    draw.text((0, 0), letra , font=font, lign="center")
                    
                    # use a truetype font
                    draw = ImageDraw.Draw(im)

                    path="letras_fuente/" + letra + "_UPPER_" 
                        
                    if not os.path.exists(path):
                        if not os.path.exists("letras_fuente/" + letra + "_UPPER_"):
                            os.mkdir("letras_fuente/" + letra + "_UPPER_")

                    if not(i in self.prohibidas):
                        im.save(path + "/"+  str(i) +"_" + str(number) + ".png")
                    
		

    def load_data(self, folder='test-set-519', train_percent = 0.8):
    
            print("NUMERO DE DATOS!!! " + str(len(self.fonts)))
            x_train = []
            

            x_test = []
            
            DIR = folder
            data_count = len([name for name in os.listdir(DIR) if os.path.isfile(os.path.join(DIR, name))])
            train_count = int(data_count * train_percent)
            
            for j in range(0,10):
    			# Get unique selection for each images.
                
                for i in range(len(self.fonts)):
                    path = folder + str(i) + "_" + str(j) +".png"
                    if not(i in self.prohibidas):
                        img = misc.imread(path, mode="L") # / 255
                        if j < 8:
                            x_train.append(img)
                            
                            train_count -= 1
                        else:
                            
                            x_test.append(img)
                            
                i=0
                        
            return np.array(x_train), np.array(x_train), np.array(x_test), np.array(x_test)

        