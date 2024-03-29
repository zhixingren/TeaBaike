package estyle.teabaike.viewmodel

import android.app.Application
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import estyle.base.BaseViewModel
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.DbDataSource
import estyle.teabaike.entity.ContentEntity
import io.reactivex.Observable

class CollectionViewModel(application: Application) : BaseViewModel(application) {

//    @Inject
//    lateinit var dataSource: CollectionDataSource

//    init {
//        InjectUtil.dataSourceComponent()
//            .inject(this)
//    }

    private var collectionListBuilder: RxPagedListBuilder<Int, ContentEntity.DataEntity>? = null

    fun refresh(): Observable<PagedList<ContentEntity.DataEntity>> {
        if (collectionListBuilder == null) {
            collectionListBuilder = RxPagedListBuilder(
                DbDataSource.collectionDao()
                    .queryAll(),
                PagedList.Config.Builder()
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()
            )
        }
        return collectionListBuilder!!.buildObservable()
    }

    fun deleteItems(items: List<ContentEntity.DataEntity>) = DbDataSource.collectionDao()
        .delete(items)
        .toObservable()
        .compose(SchedulersTransformer())
}