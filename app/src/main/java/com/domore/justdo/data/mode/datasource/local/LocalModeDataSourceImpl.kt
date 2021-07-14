package com.domore.justdo.data.mode.datasource.local

import com.domore.justdo.data.vo.Mode
import com.domore.justdo.storage.JustDoDatabase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalModeDataSourceImpl
@Inject constructor(
    justDoDb: JustDoDatabase
) : LocalModeDataSource {
    private val modeDao = justDoDb.modeDao()

    override fun getModes(): Single<List<Mode>> =
        modeDao.getModes()


    override fun getModeById(id: Long): Single<Mode> =
        modeDao.getModesById(id)

}