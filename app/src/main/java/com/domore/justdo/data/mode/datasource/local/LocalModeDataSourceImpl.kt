package com.domore.justdo.data.mode.datasource.local

import com.domore.justdo.data.vo.Mode
import com.domore.justdo.data.vo.ModeType
import com.domore.justdo.storage.JustDoDatabase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalModeDataSourceImpl
@Inject constructor(
    justDoDb: JustDoDatabase
) : LocalModeDataSource {
    private val modeDao = justDoDb.modeDao()

    override fun getModes(): Single<List<Mode>> =
        modeDao
            .getModes()
            .flatMap(::populateDbIfRequired)


    override fun getModeById(id: Long): Single<Mode> =
        modeDao.getModesById(id)

    override fun getModeByName(name: String): Single<Mode> =
        modeDao
            .getModeByName(name)
            .onErrorResumeNext {
                modeDao
                    .retain(get())
                    .andThen(getModeByName(name))
            }

    private fun populateDbIfRequired(modes: List<Mode>): Single<List<Mode>> =
        if (modes.isEmpty()) {
            Single.just(get())
                .flatMap(::retain)
        } else {
            Single.just(modes)
        }

    fun retain(modes: List<Mode>): Single<List<Mode>> =
        modeDao
            .retain(modes)
            .andThen(getModes())

    fun retain(mode: Mode): Single<Mode> =
        modeDao
            .retain(mode)
            .flatMap { getModeById(it) }

    companion object PredefinedCategories {
        fun get() = mutableListOf(
            Mode(1, ModeType.INTERVAL),
            Mode(2, ModeType.TIMER),
            Mode(3, ModeType.PRECISE_TIME),
        )
    }

}