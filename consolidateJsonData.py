
import sys, os
from os import walk

DIR = "."
JOSN_FOOTER = ".json"
JPEG_FOOTER = ".jpg"
ROBOT_HEADER = "Robot"
BENCH_HEADER = "benchmarkData"
SCOUT_HEADER = "scoutingData"
FILES_IN_DIR = []
ROBOT_FILES_IN_DIR = []
BENCH_FILES_IN_DIR = []
SCOUT_FILES_IN_DIR = []
BENCH_DICT = {}
SCOUT_DICT = {}
ROBOT_DICT = {}
BENCH_RESULT_FILE = "benchmarkDataAll.csv"
SCOUT_RESULT_FILE = "scoutingDataAll.csv"
ROBOT_RESULT_FILE = "robotPictureIndex.csv"
ROBOT_TEAMNUM_KEY = "teamNumber"
ROBOT_GOOD_KEY = "numberOfGoodFiles"
ROBOT_BAD_KEY = "numberOfBadFiles"

for(dirPath, dirNames, fileNames) in walk(DIR):
  FILES_IN_DIR.extend(fileNames)
  break

for fileName in FILES_IN_DIR:
  if JOSN_FOOTER  in fileName and BENCH_HEADER in fileName:
    BENCH_FILES_IN_DIR.append(fileName)
  elif JOSN_FOOTER  in fileName and SCOUT_HEADER in fileName:
    SCOUT_FILES_IN_DIR.append(fileName)
  elif JPEG_FOOTER in fileName and ROBOT_HEADER in fileName:
    ROBOT_FILES_IN_DIR.append(fileName)
    
def parseJson(_fileName, _resultDict):
  filePath = DIR + "/" + _fileName
  try:
    with open(filePath, "r") as input:
      valuesDict = {}
      for line in input:
        line = line.strip('\t\n\r')
        line = line.split("{\"", 1)[1] # remove first curly
        line = line.rsplit("}", 1)[0] # remove last curly
        line = line.replace(",\",\"", "\",\"")
        entries = line.split(",\"")
        for entry in entries:
          (key, value) = entry.split("\":", 1)
          valuesDict[key] = value
      _resultDict[_fileName] = valuesDict
  except IOError:
    print("File not found: " + filePath)

def retrieveHeaderList(_resultDict):
  allHeadersWithDups = []
  for valuesDict in _resultDict.values():
    for key in valuesDict.keys():
      allHeadersWithDups.append(key)
  return set(allHeadersWithDups)

def convertListToCommaSeparatedString(_list):
  rtnLine = ""
  for value in _list:
    rtnLine = rtnLine + value + ','
  return rtnLine.rsplit(',', 1)[0] # remove last comma before return

def retrieveValueLine(headerList, _valueDict):
  rtnLine = ""
  for header in headerList:
    rtnLine = rtnLine + _valueDict[header] + ','
  return rtnLine.rsplit(',', 1)[0] # remove last comma before return

def writeFile(_fileName, _resultDict, _headerList):
  filePath = DIR + "/" + _fileName
  headerLine = convertListToCommaSeparatedString(_headerList)
  try:
    with open(filePath, "w") as input:
      input.write(headerLine + '\n')
      for valuesDict in _resultDict.values():
        valueLine = retrieveValueLine(_headerList, valuesDict)
        input.write(valueLine + '\n')
  except IOError:
    print("File not found: " + filePath)

def parseJpg(_fileName, _resultDict):
  filePath = DIR + "/" + _fileName
  statinfo = os.stat(filePath)
  result = 0
  if statinfo.st_size > 0:
    result = 1
  _resultDict[_fileName] = result

def consolidateJpg(robotDict):
  resultDict = {}
  for (fileName, goodImage) in robotDict.items():
    teamNumber = fileName.split('_')[1]
    good = 0
    bad = 0
    valuesDict = {}
    if teamNumber in resultDict.keys():
      valuesDict = resultDict[teamNumber]
      good = int(valuesDict[ROBOT_GOOD_KEY])
      bad = int(valuesDict[ROBOT_BAD_KEY])
    
    if goodImage == 0:
      bad += 1
    else:
      good += 1
    valuesDict[ROBOT_TEAMNUM_KEY] = teamNumber
    valuesDict[ROBOT_GOOD_KEY] = str(good)
    valuesDict[ROBOT_BAD_KEY] = str(bad)
    resultDict[teamNumber] = valuesDict
  return resultDict  

def main():
  print("STARTED")

  for fileName in BENCH_FILES_IN_DIR:
    parseJson(fileName, BENCH_DICT)
  headerList = retrieveHeaderList(BENCH_DICT)
  writeFile(BENCH_RESULT_FILE, BENCH_DICT, headerList)

  for fileName in SCOUT_FILES_IN_DIR:
    parseJson(fileName, SCOUT_DICT)
  headerList = retrieveHeaderList(SCOUT_DICT)
  writeFile(SCOUT_RESULT_FILE, SCOUT_DICT, headerList)

  for fileName in ROBOT_FILES_IN_DIR:
    parseJpg(fileName, ROBOT_DICT)
  headerList = [ROBOT_TEAMNUM_KEY, ROBOT_GOOD_KEY, ROBOT_BAD_KEY]
  consolidatedJpgDict = consolidateJpg(ROBOT_DICT)
  writeFile(ROBOT_RESULT_FILE, consolidatedJpgDict, headerList)

  print("DONE")

if __name__ == "__main__":
  main()
