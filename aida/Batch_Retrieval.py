import urllib
import json
import os
import sys
from pgmagick import Image
import time
import shutil

# This script will pull all images for all newspapers in a certain year range from Chronicaling America.
# Dates are taken in via the command line but are optional. You can either give no dates (in which case the script will
# attempt to pull EVERY image from the Chronicaling America Database) or you can give two years which will cause
# the script to pull only images from between those two years. If you want only images from a single year just input the year twice.
# The Chronicaling America Database currently starts at 1830.
# The script will also make all neccessary directories using the location of the script as the start directory.
# Usage: python Batch_Retrieval.py "Optional: Begin Year" "Optional: End Year"

#Gets the 50 full batches
def getFullBatches():
    mainDirectory = os.getcwd()
    if not os.path.exists(mainDirectory+'/batches_json'):
        os.makedirs('batches_json')
    os.chdir('batches_json')
    batchName = "all_batches1.json"
    flag = 1
    urllib.urlretrieve('http://chroniclingamerica.loc.gov/batches.json', batchName)
    count = 2
    dots = 0
    #continue getting batch files until no more exist
    while flag is 1:
        file = open(batchName, 'r')
        data = json.load(file)
        try:
            nextURL = data['next']
            batchName = "all_batches"+str(count)+".json"
            tryflag = 1
            while tryflag is 1:
                try:
                    urllib.urlretrieve(nextURL, batchName)
                    pass
                    tryflag = 0
                except IOError:
                    sys.stdout.write("\rConnnection Problem: Retrying Connection...           ")
                    time.sleep(5)
		    sys.stdout.flush()
            if dots is 0:
                sys.stdout.write("\rRetrieveing Full Batches   ")
                sys.stdout.flush()
                dots += 1
            elif dots is 1:
                sys.stdout.write("\rRetrieveing Full Batches.  ")
                sys.stdout.flush()
                dots += 1
            elif dots is 2:
                sys.stdout.write("\rRetrieveing Full Batches.. ")
                sys.stdout.flush()
                dots += 1
            elif dots is 3:
                sys.stdout.write("\rRetrieveing Full Batches...")
                sys.stdout.flush()
                dots = 0
            count += 1
        except:
            flag = 0
        file.close()
        #break
    sys.stdout.write("\rGot all Full Batches       ")
    print("")
    os.chdir('..')
    return

#Retrieves the smaller batches that points to a subset of full newspapers
def getIndividualBatches():
    mainDirectory = os.getcwd()
    if not os.path.exists(mainDirectory+'/issueBatches'):
        os.makedirs('issueBatches')
    os.chdir('batches_json')
    fullBatchCount = len(os.listdir(os.getcwd()))
    currentBatch = 0
    problemFiles = []
    for i in os.listdir(os.getcwd()):
        #Only work on .json files
        if i.endswith(".json"):
            currentBatch += 1
            exception = 0
            file = open(i, 'r')
            try:
                data = json.load(file)
            except:
                exception = 1
                problemFiles.append(i)
            if exception is 0:
                size = len(data['batches'])
                count = 0
                for x in range(0, size):
                    count += 1
                    issueBatchURL = data['batches'][x]['url']
                    batchName = issueBatchURL.replace('http://chroniclingamerica.loc.gov/batches/batch_', '')
                    flag = 1
                    while flag is 1:
                        try:
                            urllib.urlretrieve(issueBatchURL, mainDirectory+"/issueBatches/"+batchName)
                            pass
                            flag = 0
                        except IOError:
                            sys.stdout.write("\rConnnection Problem: Retrying Connection...           ")
                            time.sleep(5)
			    sys.stdout.flush()
                    sys.stdout.write("\rIndividual Batches: Full Batch "+str(currentBatch)+"/"+str(fullBatchCount)+" Newspaper: "+str(count)+"/"+str(size))
                    sys.stdout.flush()
            file.close()
            #break
    os.chdir('..')
    if len(problemFiles) > 0:
        print("These are files that had problems. Please Check them manually.")
        print(problemFiles)
    else:
        print("")
    return

#Retrieves each issue of a newspaper for its entire existance
#Also allows for date ranges to cut down on how many issues we are retrieving
#No years for all newspapers, two years for a date range, one year entered twice for a single year
def getIssues(beginYear=0, endYear=2015):
    mainDirectory = os.getcwd()
    if not os.path.exists(mainDirectory+'/individualIssueBatches'):
        os.makedirs('individualIssueBatches')
    os.chdir('issueBatches')
    newspaperCount = len(os.listdir(os.getcwd()))
    currentPaper = 0
    problemFiles = []
    for i in os.listdir(os.getcwd()):
        #Only work on .json files
        if i.endswith(".json"):
            currentPaper += 1
            exception = 0
            file = open(i, 'r')
            try:
                data = json.load(file)
            except:
                exception = 1
                problemFiles.append(i)
            if exception is 0:
                size = len(data['issues'])
                name = i[:-5]
                total = 0
                for y in range(0, size):
                    yearIssued = data['issues'][y]['date_issued'][:4]
                    year = int(yearIssued)
                    if year >= int(beginYear) and year <= int(endYear):
                        total += 1
                count = 0
                for x in range(0, size):
                    issueURL = data['issues'][x]['url']
                    yearIssued = data['issues'][x]['date_issued'][:4]
                    year = int(yearIssued)
                    #makes the check for year issued. If not in the range then it won't retrieve the file.
                    if year >= int(beginYear) and year <= int(endYear):
                        if not os.path.exists(mainDirectory+'/individualIssueBatches/'+name):
                            os.makedirs(mainDirectory+'/individualIssueBatches/'+name)
                        count += 1
                        issueName = issueURL.replace('http://chroniclingamerica.loc.gov/lccn/', '').replace('/', '_')
                        flag = 1
                        while flag is 1:
                            try:
                                urllib.urlretrieve(issueURL, mainDirectory+"/individualIssueBatches/"+name+'/'+issueName)
                                pass
                                flag = 0
                            except IOError:
                                sys.stdout.write("\rConnnection Problem: Retrying Connection...                                              ")
                                time.sleep(5)
				sys.stdout.flush()
                        sys.stdout.write("\rIssues: Newspaper "+str(currentPaper)+"/"+str(newspaperCount)+": got issue "+str(count)+"/"+str(total)+" in "+name+"      ")
                        sys.stdout.flush()
                    #break
            file.close()
            #break
    os.chdir('..')
    if len(problemFiles) > 0:
        print("These are files that had problems. Please Check them manually.")
        print(problemFiles)
    else:
        print("")
    return

#gets the final .json files that ultimately point to the JP2 images
def getPages():
    mainDirectory = os.getcwd()
    if not os.path.exists(mainDirectory+'/pagesBatches'):
        os.makedirs('pagesBatches')
    pagesPath = mainDirectory+'/pagesBatches'
    os.chdir('individualIssueBatches')
    newspaperCount = len(os.listdir(os.getcwd()))
    currentPaper = 0
    problemFiles = []
    for i in os.listdir(os.getcwd()):
        #Exclude hidden directories
        if '.' not in i:
            currentPaper += 1
            os.chdir(i)
            if not os.path.exists(mainDirectory+'/pagesBatches/'+i):
                os.makedirs(pagesPath+'/'+i)
            issuelist = os.listdir(os.getcwd())
            issueCount = len(issuelist)
            currentIssue = 0
            for j in issuelist:
                #Only work on .json files
                if j.endswith(".json"):
                    currentIssue += 1
                    file = open(j, 'r')
                    jsonBroken = 0
                    try:
                        data = json.load(file)
                    except:
                        problemFiles.append(j)
                        jsonBroken = 1
                    if jsonBroken == 0:
                        size = len(data['pages'])
                        name = data['url'].replace('http://chroniclingamerica.loc.gov/lccn/', '').replace('/', '_').replace('.json', '')
                        if not os.path.exists(mainDirectory+'/pagesBatches/'+i+'/'+name):
                            os.makedirs(mainDirectory+'/pagesBatches/'+i+'/'+name)
                        count = 0
                        for x in range(0, size):
                            count += 1 
                            pageURL = data['pages'][x]['url']
                            page = pageURL.replace('http://chroniclingamerica.loc.gov/lccn/', '').replace('/', '_')
                            flag = 1
                            while flag is 1:
                                try:
                                    urllib.urlretrieve(pageURL, mainDirectory+'/pagesBatches/'+i+'/'+name+'/'+page)
                                    pass
                                    flag = 0
                                except IOError:
                                    sys.stdout.write("\rConnnection Problem: Retrying Connection...                                               ")
                                    time.sleep(5)
                                    sys.stdout.flush()
                            sys.stdout.write("\rPages: Newspaper "+str(currentPaper)+"/"+str(newspaperCount)+": Issue "+str(currentIssue)+"/"+str(issueCount)+": Page "+str(count)+"/"+str(size)+" in "+name+"      ")
                            sys.stdout.flush()
                        file.close()
                        #break
            os.chdir('..')
    os.chdir('..')
    if len(problemFiles) > 0:
        print("These are files that had problems. Please Check them manually.")
        print(problemFiles)
    else:
        print("")
    return

#What it says on the tin. Retrieves all of the JP2 images based on the previous acquired .json files.
#Also converts the JP2s into jpg, then deletes the JP2 images to conserve space.
def getImages():
    if not os.path.exists(os.getcwd()+'/data/FullPages'):
        os.makedirs('data/FullPages')
    fullPages = os.getcwd()+'/data/FullPages'
    os.chdir('pagesBatches')
    newspaperCount = len(os.listdir(os.getcwd()))
    currentPaper = 0
    problemFiles = []
    problemImages = []
    for i in os.listdir(os.getcwd()):
        #Exclude hidden directories
        if '.' not in i:
            currentPaper += 1
            if not os.path.exists(fullPages+'/'+i):
                os.makedirs(fullPages+'/'+i)
            os.chdir(i)
            currentlist = os.listdir(os.getcwd())
            issueCount = len(currentlist)
            currentIssue = 0
            for j in currentlist:
                #Need just the visible directories
                if not (j.endswith('.json') or '.' in j):
                    currentIssue += 1
                    if not os.path.exists(fullPages+'/'+i+'/'+j):
                        os.makedirs(fullPages+'/'+i+'/'+j)
                    os.chdir(j)
                    pageCount = len(os.listdir(os.getcwd()))
                    currentPage = 0
                    for k in os.listdir(os.getcwd()):
                        #now we can work on .json files
                        if k.endswith('.json'):
                            currentPage += 1
                            file = open(k, 'r')
                            jsonBroken = 0
                            try:
                                data = json.load(file)
                            except:
                                problemFiles.append(k)
                                jsonBroken = 1
                            if jsonBroken is 0:
                                jp2URL = data['jp2']
                                name = jp2URL.replace('http://chroniclingamerica.loc.gov/lccn/', '').replace('/', '_')
                                flag = 1
                                while flag is 1:
                                    try:
                                        urllib.urlretrieve(jp2URL, fullPages+'/'+i+'/'+j+'/'+name)
                                        pass
                                        flag = 0
                                    except IOError:
                                        sys.stdout.write("\rConnnection Problem: Retrying Connection...           ")
                                        time.sleep(5)
                                        sys.stdout.flush()
                                returnDir = os.getcwd()
                                os.chdir(fullPages+'/'+i+'/'+j)
                                try:
                                    im = Image(str(name))
                                    newname = name[:-3]+'jpg'
                                    im.write(str(newname))
                                except:
                                    problemImages.append(name)
                                #remove the jp2 file to conserve space
                                os.remove(name)
                                os.chdir(returnDir)
                                s = name[:-4]
                                sys.stdout.write("\rImages: Newspaper "+str(currentPaper)+"/"+str(newspaperCount)+": Issue "+str(currentIssue)+"/"+str(issueCount)+": Image "+str(currentPage)+"/"+str(pageCount)+" in "+s+"    ")
                                sys.stdout.flush()
                                file.close()
                    os.chdir('..')
                    #break
            os.chdir('..')
    os.chdir('..')
    if len(problemFiles) > 0:
        print("These are json files that had problems. Please Check them manually.")
        print(problemFiles)
    else:
        print("")
    if len(problemImages) > 0:
        print("These are images that could not be converted for some reason. Please check for corruption.")
        print(problemImages)
    else:
        print("")
    return

#This function will copy the page level json files into a different directory and delete them from the "individualIssueBatches" directory.
#This is to facilitate the ability to start "getImages" from and arbitrary point. The function expects the file path string to be given as if you are in the "individualIssueBatches" directory already.
#The endpoint file path is considered inclusive so which ever file is named will be the last file to be copied (makes it easy if the start point for getImages is an issue that has started retrieval yet).
def archivePages(endPoint, remove = ''):
    todayDate = time.strftime("%m_%d_%Y_%X")
    if not os.path.exists(os.getcwd()+'/pagesJsonArchive/'+todayDate):
        os.makedirs('pagesJsonArchive/'+todayDate)
    archivePath = os.getcwd()+'/pagesJsonArchive/'+todayDate
    endPointList = endPoint.split("/")
    newspaper = endPointList[0]
    issue = endPointList[1]
    page = endPointList[2]
    os.chdir('pagesBatches')
    newspapers = os.listdir(os.getcwd())
    for i in newspapers:
        if i != newspaper:
            shutil.copytree(i, archivePath+'/'+i)
            if remove == 'r' or remove == 'R':
                shutil.rmtree(i)
        else:
            os.chdir(i)
            issues = os.listdir(os.getcwd())
            for j in issues:
                if j != issue:
                    shutil.copytree(j, archivePath+'/'+i+'/'+j)
                    if remove == 'r' or remove == 'R':
                        shutil.rmtree(j)
                else:
                    os.chdir(j)
                    os.makedirs(archivePath+'/'+i+'/'+j)
                    pages = os.listdir(os.getcwd())
                    for k in pages:
                        if k != page:
                            shutil.copy(k, archivePath+'/'+i+'/'+j)
                            if remove == 'r' or remove == 'R':
                                os.remove(k)
                        else:
                            shutil.copy(k, archivePath+'/'+i+'/'+j)
                            if remove == 'r' or remove == 'R':
                                os.remove(k)
                            break
                    break
            break
    return
    

if len(sys.argv) is 3:
    if int(sys.argv[2]) < int(sys.argv[1]):
        print("HEY! The start year can't be greater than the end year!!!")
        sys.exit(1)
elif len(sys.argv) is 2:
    print("Proper Usage: Batch_Retrieval.py (Optional: Begin Date) (Optional: End Date)")
    print("You must have either no dates or both dates. NOT JUST ONE")
    sys.exit(2)
elif len(sys.argv) > 3:
    print("Proper Usage: Batch_Retrieval.py (Optional: Begin Date) (Optional: End Date)")
    print("What are you doing putting in so many arguments?")
    sys.exit(3)

#getFullBatches()
#getIndividualBatches()
if len(sys.argv) is 3:
    getIssues(sys.argv[1], sys.argv[2])
else:
    getIssues()
getPages()
getImages()
#archivePages('mimtptc_alpena_ver01/sn83016620_1837-11-22_ed-1/sn83016620_1837-11-22_ed-1_seq-2.json', 'r')
