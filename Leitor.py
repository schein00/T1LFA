import sys


try:
   file = open('entrada.txt', 'rb')
   for i in range(10):
      words = file.readline()
      words = words.strip()
      if words.isalnum() != True:
         print ("noob\n")
      else :
         print (words.decode(encoding='UTF-8',errors='strict'), "\n")
    


   file.close()
except:
	print("Leia o Readme")


