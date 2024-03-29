package estyle.teabaike.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import estyle.base.entity.NetEntity
import estyle.teabaike.config.Db

data class ContentEntity(val data: DataEntity) : NetEntity() {

    @Entity(tableName = Db.TABLE_COLLECTION, indices = [Index(value = ["id"], unique = true)])
    data class DataEntity(
        @PrimaryKey(autoGenerate = true) var _id: Long = 0,
        var id: String? = null,
        var title: String? = null,
        var source: String? = null,
        var create_time: String? = null,
        var author: String? = null,
        @Ignore var weiboUrl: String? = null,
        @Ignore var wap_content: String? = null,
        @Ignore var isChecked: Boolean = false
    )
}
