# Udacity_PopularMovies
This repository has been created to version control my "popular movies" app intended to be submitted. Project is for Udacity's "Android Developer Nanaodegree by Google" nanodegree program. This project is number 1/8 and also 2/8 (different branches for the two submissions).


# NOTE!!!
My api key has not been included (obviously). Please create a new string resource with the name="api_key", it should look something like this:

```
<string name="api_key">YOUR API KEY </string>
```


Following content is copied from Udacity's project page.

# Project Overview 
Most of us can relate to kicking back on the couch and enjoying a movie with friends and family. In this project, you’ll build an app to allow users to discover the most popular movies playing. We will split the development of this app in two stages. First, let's talk about stage 1. In this stage you’ll build the core experience of your movies app.

You app will:

- Present the user with a grid arrangement of movie posters upon launch.
- Allow your user to change sort order via a setting:
  - The sort order can be by most popular or by highest-rated
- Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
  - original title
  - movie poster image thumbnail
  - A plot synopsis (called overview in the api)
  - user rating (called vote_average in the api)
  - release date

# What Will I learn After Stage 1?

- You will fetch data from the Internet with theMovieDB API.
- You will use adapters and custom list layouts to populate list views.
- You will incorporate libraries to simplify the amount of code you need to write

# Stage 1 Marking Rubric (From Udacity)

### User Interface - Layout  ###
|MEETS SPECIFICATIONS
--------------------------------
|Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.
|UI contains an element (i.e a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest |rated.
|UI contains a screen for displaying the details for a selected movie.
|Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.

### User Interface - Function  ###
|MEETS SPECIFICATIONS
--------------------------------
|When a user changes the sort criteria (“most popular and highest rated”) the main view gets updated correctly.
|When a movie poster thumbnail is selected, the movie details screen is launched.

### Network API Implementation  ###
|MEETS SPECIFICATIONS
--------------------------------
|In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.

### General Project Guidelines 
|MEETS SPECIFICATIONS
--------------------------------
|App conforms to common standards found in the Android Nanodegree General Project Guidelines (NOTE: For Stage 1 of the Popular Movies App, it is okay if the app does not restore the data using onSaveInstanceState/onRestoreInstanceState)

# What Will I Learn After Stage 2?

- You will build a fully featured application that looks and feels natural on the latest Android operating system (Nougat, as of November 2016).

# Stage 2 Marking Rubric (From Udacity)

### User Interface - Layout  ###
|MEETS SPECIFICATIONS
--------------------------------
|UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.
|Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.
|UI contains a screen for displaying the details for a selected movie.
|Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis. 
|Movie Details layout contains a section for displaying trailer videos and user reviews.

### User Interface - Function  ###
|MEETS SPECIFICATIONS
--------------------------------
|When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.
|When a movie poster thumbnail is selected, the movie details screen is launched.
|When a trailer is selected, app uses an Intent to launch the trailer.
|In the movies detail screen, a user can tap a button(for example, a star) to mark it as a Favorite.

### Network API Implementation  ###
|MEETS SPECIFICATIONS
--------------------------------
|In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.
|App requests for related videos for a selected movie via the /movie/{id}/videos endpoint in a background thread and displays those details when the user selects a movie.
|App requests for user reviews for a selected movie via the /movie/{id}/reviews endpoint in a background thread and displays those details when the user selects a movie.

### Data Persistence  ###
|MEETS SPECIFICATIONS
--------------------------------
|App saves a "Favorited" movie to SharedPreferences or a database using the movie’s id. 
|When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie IDs stored in SharedPreferences or a database.
