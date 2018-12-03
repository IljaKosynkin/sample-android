package com.syllogismobile.sample_android.backend.database

import com.syllogismobile.sample_android.backend.logger.Logger
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmModel

class RealmDatabase(private val logger: Logger): Database<RealmModel> {
    override fun <T : RealmModel> get(id: String, clazz: Class<T>): Single<T> {
        val realm = Realm.getDefaultInstance()
        val obj: T? = realm.where(clazz).equalTo("id", id).findFirst()

        if (obj == null) {
            logger.log(Logger.Level.ERROR, "Object of $clazz for $id not found")
            return Single.error(Throwable("Object of $clazz for $id not found"))
        }

        return Single.just(obj)
    }

    override fun <T : RealmModel> save(obj: T) {
        Realm.getDefaultInstance().executeTransaction {
            it.insertOrUpdate(obj)
        }
    }
}