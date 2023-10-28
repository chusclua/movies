package com.chus.clua.data.network.api.fake

import com.chus.clua.data.network.api.PersonApi
import com.chus.clua.data.network.model.PersonDetailApiModel
import com.chus.clua.data.network.model.PersonMovieCastApiModel
import com.chus.clua.data.network.model.PersonMovieCreditsApiModel
import com.chus.clua.data.network.model.PersonMovieCrewApiModel
import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakePersonApiImp @Inject constructor() : BaseFakeApi(), PersonApi {

    private val PersonDetailApiModel = PersonDetailApiModel(
        adult = false,
        alsoKnownAs = listOf(
            "Marlon Brando Jr.",
            "Bud",
            "Марлон Брандо",
            "マーロン・ブランド",
            "말론 브란도",
            "มาร์ลอน แบรนโด",
            "مارلون براندو",
            "马龙·白兰度",
            "მარლონ ბრანდო"
        ),
        biography = "Marlon Brando Jr. (April 3, 1924 – July 1, 2004) was an American actor. Considered one of the most influential actors of the 20th century, he received numerous accolades throughout his career which spanned six decades, including two Academy Awards, two Golden Globe Awards, and three British Academy Film Awards. Brando was also an activist for many causes, notably the civil rights movement and various Native American movements. Having studied with Stella Adler in the 1940s, he is credited with being one of the first actors to bring the Stanislavski system of acting and method acting, derived from the Stanislavski system, to mainstream audiences.\n\nHe initially gained acclaim and his first Academy Award nomination for Best Actor in a Leading Role for reprising the role of Stanley Kowalski in the 1951 film adaptation of Tennessee Williams' play A Streetcar Named Desire, a role that he originated successfully on Broadway. He received further praise, and a first Academy Award and Golden Globe Award, for his performance as Terry Malloy in On the Waterfront, and his portrayal of the rebellious motorcycle gang leader Johnny Strabler in The Wild One proved to be a lasting image in popular culture. Brando received Academy Award nominations for playing Emiliano Zapata in Viva Zapata! (1952); Mark Antony in Joseph L. Mankiewicz's 1953 film adaptation of Shakespeare's Julius Caesar; and Air Force Major Lloyd Gruver in Sayonara (1957), an adaptation of James A. Michener's 1954 novel.\n\nThe 1960s saw Brando's career take a commercial and critical downturn. He directed and starred in the cult western One-Eyed Jacks, a critical and commercial flop, after which he delivered a series of notable box-office failures, beginning with Mutiny on the Bounty (1962). After ten years of underachieving, he agreed to do a screen test as Vito Corleone in Francis Ford Coppola's The Godfather (1972). He got the part and subsequently won his second Academy Award and Golden Globe Award in a performance critics consider among his greatest. He declined the Academy Award due to alleged mistreatment and misportrayal of Native Americans by Hollywood. The Godfather was one of the most commercially successful films of all time, and alongside his Oscar-nominated performance in Last Tango in Paris (1972), Brando reestablished himself in the ranks of top box-office stars.\n\nAfter a hiatus in the early 1970s, Brando was generally content with being a highly paid character actor in supporting roles, such as Jor-El in Superman (1978), as Colonel Kurtz in Apocalypse Now (1979), and Adam Steiffel in The Formula (1980), before taking a nine-year break from film. According to the Guinness Book of World Records, Brando was paid a record $3.7 million ($16 million in inflation-adjusted dollars) and 11.75% of the gross profits for 13 days' work on Superman.\n\nBrando was ranked by the American Film Institute as the fourth-greatest movie star among male movie stars whose screen debuts occurred in or before 1950. He was one of only six actors named in 1999 by Time magazine in its list of the 100 Most Important People of the Century. In this list, Time also designated Brando as the \"Actor of the Century\".",
        birthday = "1924-04-03",
        deathDay = "2004-07-01",
        gender = 2,
        homepage = "http://www.marlonbrando.com/",
        id = 3084,
        imdbId = "nm0000008",
        knownForDepartment = "Acting",
        name = "Marlon Brando",
        placeOfBirth = "Omaha, Nebraska, USA",
        popularity = 13.294,
        profilePath = "/eEHCjqKMWSvQU4bmwhLMsg4RtEr.jpg"
    )

    private val PersonMovieCastApiModel = PersonMovieCastApiModel(
        adult = false,
        backdropPath = "/hUzRPXBUHyJhpyGV0NfBDuadbgp.jpg",
        genreIds = listOf(80, 18),
        id = 654,
        originalLanguage = "en",
        originalTitle = "On the Waterfront",
        overview = "Terry Malloy dreams about being a prize fighter, while tending his pigeons and running errands at the docks for Johnny Friendly, the corrupt boss of the dockers union. Terry witnesses a murder by two of Johnny's thugs, and later meets the dead man's sister and feels responsible for his death. She introduces him to Father Barry, who tries to force him to provide information for the courts that will smash the dock racketeers.",
        popularity = 14.477,
        posterPath = "/fKjLZy9W8VxMOp5OoyWojmLVCQw.jpg",
        releaseDate = "1954-06-22",
        title = "On the Waterfront",
        video = false,
        voteAverage = 7.94,
        voteCount = 1411,
        character = "Terry Malloy",
        creditId = "52fe4265c3a36847f801b1df",
        order = 0
    )

    private val PersonMovieCrewApiModel = PersonMovieCrewApiModel(
        adult = false,
        backdropPath = "/gUIVD0rFWVB8Okk8wU8dQxjOYnZ.jpg",
        genreIds = listOf(18),
        id = 7984,
        originalLanguage = "en",
        originalTitle = "In the Name of the Father",
        overview = "A small time thief from Belfast, Gerry Conlon, is falsely implicated in the IRA bombing of a pub that kills several people while he is in London. He and his four friends are coerced by British police into confessing their guilt. Gerry's father and other relatives in London are also implicated in the crime. He spends fifteen years in prison with his father trying to prove his innocence.",
        popularity = 13.996,
        posterPath = "/5HaQacOMOjA2wX1XnwnDd2VaXCf.jpg",
        releaseDate = "1993-12-12",
        title = "In the Name of the Father",
        video = false,
        voteAverage = 7.898,
        voteCount = 1589,
        creditId = "5762dc22c3a36818c9000047",
        department = "Crew",
        job = "Thanks"
    )

    private val PersonMovieCreditsApiModel = PersonMovieCreditsApiModel(
        id = 3084,
        cast = listOf(PersonMovieCastApiModel),
        crew = listOf(PersonMovieCrewApiModel)
    )

    override suspend fun getPersonDetail(personId: Int) = when (isLeft) {
        false -> Either.Right(PersonDetailApiModel)
        else -> Either.Left(AppError.HttpError(404, "not found"))
    }

    override suspend fun getPersonMovieCredits(personId: Int) = when (isLeft) {
        false -> Either.Right(PersonMovieCreditsApiModel)
        else -> Either.Left(AppError.HttpError(404, "not found"))
    }
}