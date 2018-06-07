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
    prohibidas = [6,11,14,73,155,156,37,38,116,199,204,234,323,328,286,287,271,207,211,219,220,242,275,298,301]
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
        for j in range(1,3):
            for number in range(0, 10):
                for letra in list(string.ascii_lowercase):
                    
                    for i in range(len(self.fonts)):
                        
                        #fuenteB = fuente.split("\\")[1]
                        
                        im = Image.new("RGBA",(50,50))
                        
                        draw = ImageDraw.Draw(im)
                        
                        # use a bitmap font
                        #font = ImageFont.load("C:/Users/Barbassss/Documents/GitHub/TFG_OliverDiazAlejo_Javier/SOURCE/pilfonts/" + fuenteB)
                        font = ImageFont.truetype(font=self.fonts[i], size=40)
                        draw.text((5, 0), letra , font=font, lign="center")
                        
                        # use a truetype font
                        
                        if number<5:
                           im =  im.rotate(number*10)
                        elif number>5:
                           im =  im.rotate(-((10-number)*10))
                        
                        path="letras_fuente/" + letra 
    
                            
                        if not os.path.exists(path):
                            if not os.path.exists("letras_fuente/" + letra):
                                os.mkdir("letras_fuente/" + letra)
                                
                        if not(i in self.prohibidas):
                            im.save(path+"/" + str(j) + "_" + str(number) +"_" + str(i) + ".png")
                        
                        
                        
                    
                
                for letra in list(string.ascii_uppercase):
                    
                    for i in range(len(self.fonts)):
                        
                        #fuenteB = fuente.split("\\")[1]
                    
                        im = Image.new("RGBA",(50,50))
                        
                        draw = ImageDraw.Draw(im)
                        
                        # use a bitmap font
                        #font = ImageFont.load("C:/Users/Barbassss/Documents/GitHub/TFG_OliverDiazAlejo_Javier/SOURCE/pilfonts/" + fuenteB)
                        font = ImageFont.truetype(font=self.fonts[i], size=40)
                        draw.text((5, 0), letra , font=font, lign="center")
                        
                        # use a truetype font
                        draw = ImageDraw.Draw(im)
    
                        if number<5:
                           im = im.rotate(number*10)
                        elif number>5:
                           im =  im.rotate(-((10-number)*10))
    
                        path="letras_fuente/" + letra + "_UPPER_" 
                            
                        if not os.path.exists(path):
                            if not os.path.exists("letras_fuente/" + letra + "_UPPER_"):
                                os.mkdir("letras_fuente/" + letra + "_UPPER_")
    
                        if not(i in self.prohibidas):
                            im.save(path + "/"+ str(j)+"_"+ str(number) +"_" + str(i) + ".png")
                        
		

    def load_data(self, folder='test-set-519', train_percent = 0.8):
    
            print("NUMERO DE DATOS!!! " + str(len(self.fonts)))
            x_train = []
            

            x_test = []
            
            DIR = folder
            data_count = len([name for name in os.listdir(DIR) if os.path.isfile(os.path.join(DIR, name))])
            train_count = int(data_count * train_percent)
            for u in range(1,3):
                for j in range(0,10):
        			# Get unique selection for each images.
                    
                    for i in range(len(self.fonts)):
                        path = folder + str(u) + "_" + str(j) + "_" + str(i) +".png"
                        if not(i in self.prohibidas):
                            img = misc.imread(path, mode="L") # / 255
                            if j < 8:
                                x_train.append(img)
                                
                                train_count -= 1
                            else:
                                
                                x_test.append(img)
                                
                    i=0
                j=0
                        
            return np.array(x_train), np.array(x_train), np.array(x_test), np.array(x_test)

        