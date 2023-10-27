package com.sametersoyoglu.kbworkmanagerkullanimi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sametersoyoglu.kbworkmanagerkullanimi.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonYap.setOnClickListener {
            // Koşullara göre işlem yapabiliriz. -Birçok koşul var Builder() dan sonra . koyarak o koşulları koyabiliriz.
            /*val calismaKosulu = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build() //internet varsa çalışacak koşul bu

            // OneTimeWorkRequestBuilder bir kere çalıştırıcam demek. butona basınca çalışacak
            val istek = OneTimeWorkRequestBuilder<MyWorker>()
                .setInitialDelay(10,TimeUnit.SECONDS) //arkaplanda işlemi 10 saniye sonra çalıştıracak. cevabı 10 saniye sonra getirir.
                .setConstraints(calismaKosulu)
                .build()

            WorkManager.getInstance(this).enqueue(istek)

            // yaptığımız işlemi takip edebiliriz. ne oluyor - hangi aşamada neler oluyor gibi aşağıda kodları.LiveData gibi anlık olarak bize değerleri vericek.
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(istek.id).observe(this){
                val durum = it.state.name
                Log.e("Arkaplan İşlem Durumu",durum)
            }*/

            val istek = PeriodicWorkRequestBuilder<MyWorkerBildirim>(15,TimeUnit.MINUTES) // mininmum tekrar sayısı 15 dakika
                .setInitialDelay(10,TimeUnit.SECONDS)
                .build()

            WorkManager.getInstance(this).enqueue(istek)
        }
    }
}