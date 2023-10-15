package com.chus.clua.presentation.compose

import com.chus.clua.presentation.model.CastList
import com.chus.clua.presentation.model.MovieDetailUi
import com.chus.clua.presentation.model.MovieList
import com.chus.clua.presentation.model.PersonDetailUi
import com.chus.clua.presentation.model.PersonMovieCastList
import com.chus.clua.presentation.model.VideoList

val Movie = MovieList(
    id = 238,
    title = "The Godfather Part II",
    backdropPath = "https://image.tmdb.org/t/p/w780/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
    posterPath = "https://image.tmdb.org/t/p/w342/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
    year = "1972",
    voteAverage = 8.7,
)

val MovieDetail = MovieDetailUi(
    backdropPath = "/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
    genres = listOf("Drama", "Crime"),
    id = 238,
    overview = "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
    posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
    productionCompanies = listOf("Paramount", "Alfran Productions"),
    productionCountries = listOf("United States of America"),
    releaseDate = "1972-03-14",
    tagline = "An offer you can't refuse.",
    title = "The Godfather",
    voteAverage = 8.707
)

val Video = VideoList(
    id = "627e772c006eee3428a4ae9f",
    name = "The Godfather Never Before Seen Footage (Rare Footage 1971)",
    thumbnailUrl = "",
    youtubeUrl = ""
)

val Cast = CastList(
    id = 3084,
    character = "Don Vito Corleone",
    name = "Marlon Brando",
    profilePath = "/eEHCjqKMWSvQU4bmwhLMsg4RtEr.jpg"
)

val PersonDetail = PersonDetailUi(
    biography = "Marlon Brando Jr. (April 3, 1924 – July 1, 2004) was an American actor. Considered one of the most influential actors of the 20th century, he received numerous accolades throughout his career which spanned six decades, including two Academy Awards, two Golden Globe Awards, and three British Academy Film Awards. Brando was also an activist for many causes, notably the civil rights movement and various Native American movements. Having studied with Stella Adler in the 1940s, he is credited with being one of the first actors to bring the Stanislavski system of acting and method acting, derived from the Stanislavski system, to mainstream audiences.\n\nHe initially gained acclaim and his first Academy Award nomination for Best Actor in a Leading Role for reprising the role of Stanley Kowalski in the 1951 film adaptation of Tennessee Williams' play A Streetcar Named Desire, a role that he originated successfully on Broadway. He received further praise, and a first Academy Award and Golden Globe Award, for his performance as Terry Malloy in On the Waterfront, and his portrayal of the rebellious motorcycle gang leader Johnny Strabler in The Wild One proved to be a lasting image in popular culture. Brando received Academy Award nominations for playing Emiliano Zapata in Viva Zapata! (1952); Mark Antony in Joseph L. Mankiewicz's 1953 film adaptation of Shakespeare's Julius Caesar; and Air Force Major Lloyd Gruver in Sayonara (1957), an adaptation of James A. Michener's 1954 novel.\n\nThe 1960s saw Brando's career take a commercial and critical downturn. He directed and starred in the cult western One-Eyed Jacks, a critical and commercial flop, after which he delivered a series of notable box-office failures, beginning with Mutiny on the Bounty (1962). After ten years of underachieving, he agreed to do a screen test as Vito Corleone in Francis Ford Coppola's The Godfather (1972). He got the part and subsequently won his second Academy Award and Golden Globe Award in a performance critics consider among his greatest. He declined the Academy Award due to alleged mistreatment and misportrayal of Native Americans by Hollywood. The Godfather was one of the most commercially successful films of all time, and alongside his Oscar-nominated performance in Last Tango in Paris (1972), Brando reestablished himself in the ranks of top box-office stars.\n\nAfter a hiatus in the early 1970s, Brando was generally content with being a highly paid character actor in supporting roles, such as Jor-El in Superman (1978), as Colonel Kurtz in Apocalypse Now (1979), and Adam Steiffel in The Formula (1980), before taking a nine-year break from film. According to the Guinness Book of World Records, Brando was paid a record $3.7 million ($16 million in inflation-adjusted dollars) and 11.75% of the gross profits for 13 days' work on Superman.\n\nBrando was ranked by the American Film Institute as the fourth-greatest movie star among male movie stars whose screen debuts occurred in or before 1950. He was one of only six actors named in 1999 by Time magazine in its list of the 100 Most Important People of the Century. In this list, Time also designated Brando as the \"Actor of the Century\".",
    homepage = "https://www.marlonbrando.com/",
    id = 1,
    name = "Marlon Brando",
    popularity = 35.248,
    profilePath = "/eEHCjqKMWSvQU4bmwhLMsg4RtEr.jpg"
)

val PersonMovieCast = PersonMovieCastList(
    id = 3084,
    posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
    character = "Don Vito Corleone",
)