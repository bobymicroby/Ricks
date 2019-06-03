package dev.boby.ricks.util.pure

import dev.boby.elmo.pure.Update
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface Update<Model, Message> : Update<Model, Message> {
    override val updateScheduler: Scheduler
        get() = AndroidSchedulers.mainThread()


}