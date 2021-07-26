package com.domore.justdo.data.mode.repository

import com.domore.justdo.data.vo.Mode
import io.reactivex.rxjava3.core.Single

interface ModeRepository {
    fun getModes(): Single<List<Mode>>
    fun getModeById(id: Long): Single<Mode>
    fun getModeByName(name: String): Single<Mode>
}