# Analyse-For-Visualiser
Integrates music information retrieval algorithms into a simple, easy to understand GUI. The GUI was designed primarily to be used with a pitch detection algorithm I proposed in my dissertation, but for demonstration purposes, it currently uses the algorithms included in the TarsosDSP library.

### Issues
After the run source separation button is clicked, the GUI will freeze and stop responding for around 10 minutes. If this happens, the program is still running, and the GUI will resume once the source separation is finished. This will be resolved shortly by properly implementing multiple threads.

### Credits
https://github.com/JorenSix/TarsosDSP

https://github.com/adefossez/demucs
