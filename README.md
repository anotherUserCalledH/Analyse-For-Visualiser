# Analyse-For-Visualiser
Integrates music information retrieval algorithms into a simple, easy to understand GUI. The GUI was designed primarily to be used with a pitch detection algorithm I proposed in my dissertation, but for demonstration purposes, it currently uses the algorithms included in the TarsosDSP library.

### Plugins
To add an external algorithm to the GUI, create a class which extends the PitchDetectionAlgorithm interface. Package the algorithm into a .jar file. Make sure that in the MANIFEST.mf, the extending class is set as the main class. Create a sub-directory called "pitch" in the plugins directory. Place the algorithm .jar file in the pitch directory. Run the application as normal.

### Credits
https://github.com/JorenSix/TarsosDSP

https://github.com/adefossez/demucs
