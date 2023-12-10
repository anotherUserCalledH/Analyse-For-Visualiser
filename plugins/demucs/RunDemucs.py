import demucs.separate
import sys

filePath = sys.argv[1]
outputPath = sys.argv[2]
print(outputPath)
demucs.separate.main([filePath, outputPath])
print("proceed")