This is a weather application built in Android Studio, using Kotlin and Jetpack compose
The app fetches weather and forecast from the openweather api (https://home.openweathermap.org/).

The app asks the user permission to access the device's location on  startup, once provided the weather and forecast data for the current location is loaded.

<p align="center">
  <img src="https://github.com/Samhitha-2001/Weather-app-in-Android-using-Kotlin-and-Jetpack-compose/blob/main/screenshots/First%20screen.png" width="250" height="400" />
</p>

Clicking on the see more button opens a dropdown.

<p align="center">
  <img src="https://github.com/Samhitha-2001/Weather-app-in-Android-using-Kotlin-and-Jetpack-compose/blob/main/screenshots/See%20more%20weather.png" width="250" height="400" />
</p>

Scrolling further down in the screen reveals the forecast section, displayd as a scrollable row.

<p align="center">
  <img src="https://github.com/Samhitha-2001/Weather-app-in-Android-using-Kotlin-and-Jetpack-compose/blob/main/screenshots/ForecastRowGif.gif" width="280" height="420" />
</p>

At the top right of the screen, there is a location icon, which is a button which on clicking loads the weather data for the current location of the device.
At the top left of the screen there is a menu button, which on clicking opens a sheet that contains two more buttons.

<p align="center">
  <img src="https://github.com/Samhitha-2001/Weather-app-in-Android-using-Kotlin-and-Jetpack-compose/blob/main/screenshots/Menu.png" width="250" height="400" />
</p>

The search button on the Menu opens a textfield. On entering the city name in the textfield, the weather and forecast data for that particular city is loaded following an alert that asks the user whether he/she wants to add this particular city to the favourite city list.

<p align="center">
  <img src="https://github.com/Samhitha-2001/Weather-app-in-Android-using-Kotlin-and-Jetpack-compose/blob/main/screenshots/Search.png" width="250" height="400" />
  -->
  <img src="https://github.com/Samhitha-2001/Weather-app-in-Android-using-Kotlin-and-Jetpack-compose/blob/main/screenshots/Search%20city.png" width="250" height="400" />
  -->
  <img src="https://github.com/Samhitha-2001/Weather-app-in-Android-using-Kotlin-and-Jetpack-compose/blob/main/screenshots/Add%20to%20favs%20prompt.png" width="250" height="400" />
  -->
  <img src="https://github.com/Samhitha-2001/Weather-app-in-Android-using-Kotlin-and-Jetpack-compose/blob/main/screenshots/LoadFavCity.jpg" width="250" height="400" />
</p>

The other button on the menu was Favourite cities, which navigates to a screen that has a list of all your favourite cities. 
Clicking on a particular city loads data for that city.
Also, room database is used in the project to persist the favourite city list.

<p align="center">
  <img src="https://github.com/Samhitha-2001/Weather-app-in-Android-using-Kotlin-and-Jetpack-compose/blob/main/screenshots/Fav%20cities.png" width="250" height="400" />
</p>

Let us tap on Mangalore.

<p align="center">
  <img src="https://github.com/Samhitha-2001/Weather-app-in-Android-using-Kotlin-and-Jetpack-compose/blob/main/screenshots/Tap%20fav%20city.png" width="250" height="400" />
</p>

