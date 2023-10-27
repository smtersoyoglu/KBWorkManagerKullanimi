package com.sametersoyoglu.kbworkmanagerkullanimi

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker (appContext: Context, workerParameters: WorkerParameters) : Worker(appContext,workerParameters) {
    override fun doWork(): Result {
        // Arkaplan işlemlerini burda yapıyoruz.
        val toplam = 10 + 20
        Log.e("Arkaplan İşlemi Sonucu :",toplam.toString())
        return Result.success() // işlem başarılı demek
    }
}