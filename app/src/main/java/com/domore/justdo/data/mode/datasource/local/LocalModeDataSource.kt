package com.domore.justdo.data.mode.datasource.local

import com.domore.justdo.data.vo.Mode
import io.reactivex.rxjava3.core.Single

interface LocalModeDataSource {
    fun getModes(): Single<List<Mode>>
    fun getModeById(id: Long): Single<Mode>
}