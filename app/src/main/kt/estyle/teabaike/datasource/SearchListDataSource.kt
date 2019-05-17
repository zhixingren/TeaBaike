package estyle.teabaike.datasource

import androidx.paging.DataSource
import estyle.base.BasePageKeyedDataSource
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.api.NetApi
import estyle.teabaike.entity.MainEntity

class SearchListDataSource(private val keyword: String) :
    BasePageKeyedDataSource<MainEntity.DataEntity>() {

    override fun getObservable(page: Int) =
        NetApi.searchService()
            .getSearch(keyword, page)
            .map { it.data }
            .compose(SchedulersTransformer())

    class Factory(val keyword: String) : BasePageKeyedDataSource.Factory<MainEntity.DataEntity>() {
        override fun create(): DataSource<Int, MainEntity.DataEntity> =
            SearchListDataSource(keyword)
    }
}
