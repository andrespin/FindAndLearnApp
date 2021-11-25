package android.findandlearnapp.utils

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface INetworkStatus {
    fun isOnline(): Observable<Boolean>
    fun isOnlineSingle(): Single<Boolean>
}

/*
Single - исполнится и завершится событием Success или Error.
Observable - будет испускать данные до тех пор, пока не вызовется событие Error или Complete.
Отсюда следует, что для однократного получения данных нужно использовать Single,
а для постоянного получения данных из некоторого источника - Observable.
Последний, например, можно использовать для получения изменённых данных в к-л таблице в БД.
Также надо учитывать механизм Backpressure - он не реализован в Observable и при испускании
источником данных слишком часто он может перестать работать.
Если использовать Flowable - эта проблема будет решена один из нескольких способов.
 */