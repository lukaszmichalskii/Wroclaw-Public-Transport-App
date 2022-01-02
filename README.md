## Wroclaw Public Transport App
The application was created as an equivalent of the vehicle 
location search version made available by 
[MPK Wrocław](https://mpk.wroc.pl/strefa-pasazera/zaplanuj-podroz/mapa-pozycji-pojazdow).
The app provides an additional option, users have the ability to check if the desired vehicle
is in the marked area ([the Heaviside formula](https://planetmath.org/heavisideformula) was used for calculations).
### Features
The main function of the application is the same as on the MPK website, see the desired vehicles (their locations) on the map extended with marker clustering
To do it users have to pick desired transport type, line number and set refresh time
(refresh time define time delta between updating vehicles locations) and simply click <em>Run</em> button. Moreover 
map view provides few options known from Google Maps platform like: street view, searching addresses (put text into search box) and many more.

Usage instruction for checking if vehicle is in marked location: 
1. Set radius - this will set the length of the segment expressed in meters.
2. Apply settings by clicking  <em>Apply</em> button
3. Select desired transport type (Bus / Tram)
4. Pick line number you are interested in
5. Click <em>Scan area</em> button to see information about vehicles in marked area

### App GUI preview
Main view of app with selected: bus line D and tram 33
![plik](https://user-images.githubusercontent.com/76202883/147885693-d193b4b7-e431-4fbd-a506-72821efa47e8.png)

Vehicle scanner mode view, activate by clicking radar icon (next to refresh option):
![plik2](https://user-images.githubusercontent.com/76202883/147885600-4fdd1899-692d-4acb-bb7c-5159e4a72c27.png)
