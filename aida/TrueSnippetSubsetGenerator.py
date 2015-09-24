import random
import os
import shutil

os.chdir('data')
with open('TrueSnippets.txt') as f:
    content = f.readlines()
numOfLines = len(content)
randomNums = random.sample(range(numOfLines), 300)
print(randomNums)
if not os.path.exists("TrueSnippets_Subset"):
    os.makedirs("TrueSnippets_Subset")
os.chdir("TrueSnippets_Subset")
subsetDirectory = os.getcwd()
os.chdir("..")
os.chdir('Output_Binary')
for i in randomNums:
    snippet = content[i]
    #remove endline character
    snippet = snippet[:-1]
    shutil.copyfile(snippet, subsetDirectory+'/'+snippet)
