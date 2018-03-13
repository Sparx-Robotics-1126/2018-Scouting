#!/apps/ThirdParty/sfw/python/2.7.9/bin/python

import sys, os
from os import walk

DIR = "."
JOSN_FOOTER = ".json"
BENCH_HEADER = "benchmarkData"
SCOUT_HEADER = "scoutingData"
FILES_IN_DIR = []
BENCH_FILES_IN_DIR = []
SCOUT_FILES_IN_DIR = []
BENCH_DICT = {}
SCOUT_DICT = {}
BENCH_RESULT_FILE = "benchmarkDataAll.csv"
SCOUT_RESULT_FILE = "scoutingDataAll.csv"

for(dirPath, dirNames, fileNames) in walk(DIR):
  FILES_IN_DIR.extend(fileNames)
  break

for fileName in FILES_IN_DIR:
  if JOSN_FOOTER  in fileName and BENCH_HEADER in fileName:
    BENCH_FILES_IN_DIR.append(fileName)
  elif JOSN_FOOTER  in fileName and SCOUT_HEADER in fileName:
    SCOUT_FILES_IN_DIR.append(fileName)

def parseJson(_fileName, _resultDict):
  filePath = DIR + "/" + _fileName
  try:
    with open(filePath, "r") as input:
      valuesDict = {}
      for line in input:
        line = line.strip('\t\n\r')
        line = line.split('{', 1)[1] # remove first curly
        line = line.rsplit('}', 1)[0] # remove last curly
        entries = line.split(',')
        for entry in entries:
          (key, value) = entry.split(':', 1)
          valuesDict[key] = value
      _resultDict[_fileName] = valuesDict
  except IOError:
    print "File not found: " + filePath

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

def writeFile(_fileName, _resultDict):
  filePath = DIR + "/" + _fileName
  headerList = retrieveHeaderList(_resultDict)
  headerLine = convertListToCommaSeparatedString(headerList)
  try:
    with open(filePath, "w") as input:
      input.write(headerLine + '\n')
      for valuesDict in _resultDict.values():
        valueLine = retrieveValueLine(headerList, valuesDict)
        input.write(valueLine + '\n')
  except IOError:
    print "File not found: " + filePath

def main():
  print "STARTED"

  for fileName in BENCH_FILES_IN_DIR:
    parseJson(fileName, BENCH_DICT)
  writeFile(BENCH_RESULT_FILE, BENCH_DICT)

  for fileName in SCOUT_FILES_IN_DIR:
    parseJson(fileName, SCOUT_DICT)
  writeFile(SCOUT_RESULT_FILE, SCOUT_DICT)

  print "DONE"

if __name__ == "__main__":
  main()
