package estyle.teabaike.datasource.net

import com.estyle.httpmock.annotation.HttpMock
import estyle.teabaike.config.Url
import estyle.teabaike.entity.HeadlineEntity
import estyle.teabaike.entity.MainEntity
import estyle.teabaike.entity.VersionEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {

    @HttpMock(fileName = "list.json")
    @GET(Url.HEADLINE_URL)
    fun getHeadlineList(@Query(Url.PAGE) page: Int): Observable<MainEntity>

    @HttpMock(fileName = "list.json")
    @GET(Url.CHANNEL_URL)
    fun getList(@Query(Url.TYPE) type: String, @Query(Url.PAGE) page: Int): Observable<MainEntity>

    @HttpMock(fileName = "headline.json")
    @GET(Url.HEADLINE_HEADER_URL)
    fun getHeadline(): Observable<HeadlineEntity>

    @HttpMock(fileName = "version.json")
    @GET(Url.CHECK_VERSION_URL)
    fun getLatestVersion(): Observable<VersionEntity>
}