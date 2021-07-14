package com.domore.justdo.data.mode.repository

import com.domore.justdo.data.mode.datasource.local.LocalModeDataSource
import com.domore.justdo.data.vo.Mode
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ModeRepositoryImpl
@Inject constructor(
    private val localModeDataSource: LocalModeDataSource
) : ModeRepository {
    override fun getModes(): Single<List<Mode>> = localModeDataSource.getModes()

    override fun getModeById(id: Long): Single<Mode> = localModeDataSource.getModeById(id)
}