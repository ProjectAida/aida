import urllib
import urllib2
import os
import sys
from pgmagick import Image

#This function creates a manifest containing the web urls of every jp2 image of a newspaper page.
#It does this by first navigating to the html page that links to each of the batch folders, then scrubs
#through the html source code to aquire the names of each batch folder. Once aquired the function naviagtes
#to each batch folder and loads up the manifest for that batch, retrieves the urls for the images from the manifest
#and appends them to the full manifest file.
def buildFullManifest():
    print "Getting batch urls"
    batchesURL = "http://chroniclingamerica.loc.gov/data/batches/"
    sock = urllib.urlopen(batchesURL)
    batchesHTML = sock.readlines()
    sock.close()
    htmlSize = len(batchesHTML)
    listOfBatchURLS = []
    print "finding names"
    for i in batchesHTML:
        if 'batch_' in i:
            s = i.split('href="')
            p = s[1].split('"')
            listOfBatchURLS.append(batchesURL+p[0])

    length = len(listOfBatchURLS)
    count = 0
    print "starting processing"
    for j in listOfBatchURLS:
        count += 1
        sys.stdout.write("\rProcessing "+str(count)+"/"+str(length)+" batch manifests")
        sys.stdout.flush()
        try:
            socket = urllib2.urlopen(j+"manifest-sha1.txt")
        except urllib2.HTTPError, err:
            if err.code == 404:
                try:
                    socket = urllib2.urlopen(j+"manifest-md5.txt")
                except urllib2.HTTPError, e:
                    print "Error HTTP: "+e.code
                    print "This is bad the manifest for batch "+j+" could not be acquired!!!"
            else:
                raise
        manifest = socket.readlines()
        socket.close()
        fullDataPaths = []
        for k in manifest:
            partialDataPath = k.split()
            if partialDataPath[1].endswith('.jp2') and partialDataPath[1].count('/') == 4:
                fullDataPaths.append(j+partialDataPath[1]+'\n')
                fullDataPaths.sort()
        openf = open("Master_Manifest.txt", "a")
        openf.writelines(fullDataPaths)
        openf.close()
    print ""

#This function, when given a begin year and an end year, will search through the full Manifest file
#and download all images within a year range. For one year, the same year is used for both parameters.
#The images are downloaded to the folowing directory structure: data/FullPages/BatchLevel/IssueLevel/PageLevel
#This function also uses wget (or curl for macs) in order to download images, this is due to complications on the
#Library of Congress's server and urllib being unable to handle requests from them.
def getImages(startYear = 1836, endYear = 2015):
    Error404 = []
    imageCount = 0
    with open("Master_Manifest.txt", "r") as masterManifest:
        for line in masterManifest:
            lineList = line.split('/')
            imageYear = int(lineList[9][:4])
            if imageYear >= startYear and imageYear <= endYear:
                imageCount += 1

    with open("Master_Manifest.txt", "r") as masterManifest:
        previousLine = ""
        pageCount = 1
        fullCount = 0
        for line in masterManifest:
            lineList = line.split('/')
            
            #used in contruction of the image's filename.
            #ensures consistent naming since each issue of a newspaper shares the same base name
            if lineList[9] == previousLine:
                pageCount += 1
            else:
                pageCount = 1
            
            imageYear = int(lineList[9][:4])
            if imageYear >= startYear and imageYear <= endYear:
                fullCount += 1
                #Remove endline character
                imageURL = line[:-1]
                
                #construct file and directory names for sorting purposes
                batchName = lineList[5][6:]
                snNumber = lineList[7]
                date = lineList[9][:4]+"-"+lineList[9][4:6]+"-"+lineList[9][6:8]
                edition = lineList[9][-1:]
                issueName = snNumber+"_"+date+"_ed-"+edition
                imageName = issueName+"_seq-"+str(pageCount)+".jp2"

                if not os.path.exists("data/FullPages/"+batchName):
                    os.makedirs("data/FullPages/"+batchName)
                if not os.path.exists("data/FullPages/"+batchName+"/"+issueName):
                    os.makedirs("data/FullPages/"+batchName+"/"+issueName)
                os.chdir("data/FullPages/"+batchName+"/"+issueName)
                
                Flag404 = 0
                try:
                    #make a check to see if image is available/found
                    socket = urllib2.urlopen(imageURL)
                except urllib2.HTTPError, err:
                    if err.code == 404:
                        Flag404 = 1
                        print "404 Error"
                        Error404.append(imageURL)
                    else:
                        raise

                if Flag404 == 0:
                    IOFlag = 1
                    while IOFlag == 1:
                        try:
                            #urllib.urlretrieve(imageURL, imageName)
                            #response = urllib2.urlopen(imageURL)
                            #with open(imageName, "w") as imageFile:
                            #    imageFile.write(response.read())
                            
                            #call the os' download function
                            os.system('wget -q -O %s %s' %(imageName, imageURL))
                            IOFlag = 0
                        except IOError:
                            sys.stdout.write("\rConnection Error: Retrying Connection...            ")
                            sys.stdout.flush()
                            time.sleep(5)

                #Status update on the number of images downloaded
                sys.stdout.write("\rProcessed Image "+str(fullCount)+"/"+str(imageCount)+"           ")
                sys.stdout.flush()
                os.chdir("../../../../")
                
                        
            
            previousLine = lineList[9]
    if len(Error404) > 0:
        print "This files returned a 404 error when trying to download the images."
        print Error404
    else:
        print "All files downloaded with no errors"

#This function searches through the directory structure created in the getImages function
#and converts all jp2 images to the jpg format. If image can't be converted the function adds
#the filename to a list of broken images and is presented at the end of the process.
def convertToJpg():
    problemImages = []
    os.chdir("data/FullPages")
    batchLevel = os.listdir(os.getcwd())
    totalBatches = len(batchLevel)
    currentBatch = 0
    for i in batchLevel:
        currentBatch += 1
        os.chdir(i)
        issueLevel = os.listdir(os.getcwd())
        totalIssues = len(issueLevel)
        currentIssue = 0
        for j in issueLevel:
            currentIssue += 1
            os.chdir(j)
            jp2Images = os.listdir(os.getcwd())
            totalImages = len(jp2Images)
            currentImage = 0
            for k in jp2Images:
                currentImage += 1
                try:
                    im = Image(str(k))
                    newName = k[:-3]+'jpg'
                    im.write(str(newName))
                    
                    #Status update on how many images have been processed
                    sys.stdout.write("\rConverted Batch: "+str(currentBatch)+"/"+str(totalBatches)+" Issue: "+str(currentIssue)+"/"+str(totalIssues)+" Image: "+str(currentImage)+"/"+str(totalImages)+"           ")
                    sys.stdout.flush()
                    #remove old jp2 image to conserve space, also only remove if conversion was successfull
                    os.remove(k)
                except:
                    problemImages.append(str(k))
            os.chdir('..')
        os.chdir('..')
    os.chdir('../..')

    #end of process message
    if len(problemImages) > 0:
        print("These are images that could not be converted to jpg for some reason. Please check for corruption/ proper download.")
        print(problemImages)
    else:
        print("All images converted successfully")


if sys.argv[1] == 1:
    buildFullManifest()
    getImages(sys.argv[2], sys.argv[3])
    convertToJpg()
elif sys.argv[1] == 2:
    getImages(sys.argv[2], sys.argv[3])
    convertToJpg()
elif sys.argv[1] == 3:
    buildFullManifest()
