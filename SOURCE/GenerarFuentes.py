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
    def __init__(self):
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
        for number in range(0, 7):
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

                    
                    im.save(path + "/"+  str(i) +"_" + str(number) + ".png")
                    
		

    def load_data(self, folder='test-set-519', train_percent = 0.8):
    

            x_train = []
            

            x_test = []
            
            DIR = folder
            data_count = len([name for name in os.listdir(DIR) if os.path.isfile(os.path.join(DIR, name))])
            train_count = int(data_count * train_percent)
            
            for j in range(0,7):
    			# Get unique selection for each images.
                random_selection = random.sample(range(round(data_count/7)), round(data_count/7))
                
                for i in random_selection:
                    path = folder + str(i) + "_" + str(j) +".png"
                    img = misc.imread(path, mode="L") # / 255
                    if train_count > 0:
                        x_train.append(img)
                        
                        train_count -= 1
                    else:
                        
                        x_test.append(img)
                    
            return np.array(x_train), np.array(x_train), np.array(x_test), np.array(x_test)

        