import demucs.separate
import sys

noArguments = len(sys.argv) - 1
arrayOfArguments = sys.argv[-noArguments:]
print(arrayOfArguments)

demucs.separate.main(arrayOfArguments)
print("proceed")