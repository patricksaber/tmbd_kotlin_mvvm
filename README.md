# DEMO MOVIE
This is an android application that shows lists of popular movies, popular Tv shows and details of each movie and Tv show with help of [TMDB API](https://developers.themoviedb.org/3/getting-started/introduction).

## Features:
- Discover Latest movies 2022 on TMDB and view movie details like poster, list of genres, release date, rating, overview, original title and original language.
- Allows user to save/remove his favorite movies in his list that is saved on database and check them offline by clicking on favorite tab
- Allows user to login using static username and password mentioned below and using remember me feature
- Allows user to logout and clear all his data from database
- Works offline by caching data into a database.
- Fake API Login where can return network and server error depending on random number

# username and password can be used (Dump data)
- username = "admin"   password = "admin"
- username = "alex"    password = "admin"
- username = "robert"  password = "123"
- username = "patrick" password = "happy"


## Architecture and Tech features
 - Written in [Kotlin](https://kotlinlang.org/) language.
 - Uses Android Architecture Components, specifically ViewModel and LiveData.
 - Uses MVVM architecture pattern.
 - Uses a single activity and three fragments
 - Uses [Mockito](https://github.com/mockito/mockito-kotlin) for behavior verification, instrumentation test
 - Uses [Espresso](https://https://developer.android.com/training/testing/espresso) to write concise, beautiful, and reliable Android UI tests.
 - Uses [Coroutines](https://developer.android.com/kotlin/coroutines?gclid=Cj0KCQiAjJOQBhCkARIsAEKMtO08q2fdRwll0y8F2QChrrPgLbOVxYZAdNIzc4w6Zt494eDB7iI06pYaAiwjEALw_wcB&gclsrc=aw.ds) for asynchronous programming.
 - Uses [Retrofit](https://square.github.io/retrofit/) for making API calls.
 - Uses [Room](https://developer.android.com/jetpack/androidx/releases/room) for managing a local SQLite database
 - Uses [Glide](https://square.github.io/picasso/) for image loading.
 - Uses [Lottie](https://lottiefiles.com/) for animation.
 - Uses [Timber](https://lottiefiles.com/https://github.com/JakeWharton/timber) for logging
 - Uses [Sneaker](https://github.com/Hamadakram/Sneaker) for SnackBar.

### Clone the Repository
As usual, you get started by cloning the project to your local machine:

## Android Version Targeting
This app is currently built to work with Android API 31(Android 12). **However**, minimum SDK
support is 21(Lollipop).


### Configuration
You need to get your own key from [TMDB API](https://developers.themoviedb.org/3/getting-started/introduction).
In order to Change the BASE_URL and API Key, you should open neugelb\local.properties and edit them.

## Author

Patrick Abi Saber

patrickabisaber@gmail.com

Hamburg/Germany


## Screenshots
<img src="https://github.com/patricksaber/tmdb_kotlin_mvvm/blob/master/screenshots/launcher.jpg" width="400" />
<img src="https://github.com/patricksaber/tmdb_kotlin_mvvm/blob/master/screenshots/login_page.jpg" width="400" />
<img src="https://github.com/patricksaber/tmdb_kotlin_mvvm/blob/master/screenshots/home_page.jpg" width="400" />
<img src="https://github.com/patricksaber/tmdb_kotlin_mvvm/blob/master/screenshots/detail_page.jpg" width="400"/>
<img src="https://github.com/patricksaber/tmdb_kotlin_mvvm/blob/master/screenshots/detail_page2.jpg" width="400" />
<img src="https://github.com/patricksaber/tmdb_kotlin_mvvm/blob/master/screenshots/search.jpg" width="400" />
<img src="https://github.com/patricksaber/tmdb_kotlin_mvvm/blob/master/screenshots/favorite_page.jpg" width="400" />
<img src="https://github.com/patricksaber/tmdb_kotlin_mvvm/blob/master/screenshots/setting_page.jpg" width="400" />
<img src="https://github.com/patricksaber/tmdb_kotlin_mvvm/blob/master/screenshots/load_more_error.jpg" width="400" />
<img src="https://github.com/patricksaber/tmdb_kotlin_mvvm/blob/master/screenshots/login_error.jpg" width="400" />
<img src="https://github.com/patricksaber/tmdb_kotlin_mvvm/blob/master/screenshots/login_error2.jpg" width="400" />
<img src="https://github.com/patricksaber/tmdb_kotlin_mvvm/blob/master/screenshots/no_data_database.jpg" width="400" />
<img src="https://github.com/patricksaber/tmdb_kotlin_mvvm/blob/master/screenshots/open_page_error.jpg" width="400" />

