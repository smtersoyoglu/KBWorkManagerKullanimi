package com.sametersoyoglu.kbworkmanagerkullanimi

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorkerBildirim (appContext: Context, workerParameters: WorkerParameters) : Worker(appContext,workerParameters) {
    override fun doWork(): Result {
        bildirimOlustur()
        return Result.success()
    }

    fun bildirimOlustur() { //bildirimle çalışırken izin almamız gerekiyor AndroidManifes te tanımlamamız lazım önce.
        // bildirim kodlaması yaparken versiyon kontrolü ne göre yapmalıyıaz Oreo sürümünden önce ve sonrası farklı olduğu için versiyon kontrolü yaparak ikisine de uygun hale getiririz.

        val builder: NotificationCompat.Builder
        val bildirimYoneticisi = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(applicationContext,MainActivity::class.java)
        val gidilecekIntent = PendingIntent.getActivity(applicationContext,1,intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ // bizim SDK mız Oreo dan büyükse veya eşitse bu çalışsın yoksa else de ki kod çalışsın dedik
            // Kanal yapısı bize güncel kodlamalı bildirimlerin gruplandırılmasını sağlıyor. örneğin instagramdan gelen beğeniler yorumlar farklı içerikler gruplanmasını sağlıyor. gruplanmayı kanalId= "kanalId" sağlar.
            val kanalId= "kanalId"
            val kanalAd= "kanalAd"
            val kanalAciklama= "kanalAciklama"
            val kanalOnceligi= NotificationManager.IMPORTANCE_HIGH

            var kanal : NotificationChannel? = bildirimYoneticisi.getNotificationChannel(kanalId)

            if (kanal == null){
                kanal = NotificationChannel(kanalId,kanalAd,kanalOnceligi)
                kanal.description = kanalAciklama
                bildirimYoneticisi.createNotificationChannel(kanal)
            }

            builder = NotificationCompat.Builder(applicationContext,kanalId)

            builder.setContentTitle("Başlık")
                .setContentText("İçerik")
                .setSmallIcon(R.drawable.resim)
                .setContentIntent(gidilecekIntent)
                .setAutoCancel(true) // bildirimi seçtiğimiz zaman kendisini yukarıdan kaldıracak demek.

        }else{
            builder = NotificationCompat.Builder(applicationContext)

            builder.setContentTitle("Başlık")
                .setContentText("İçerik")
                .setSmallIcon(R.drawable.resim)
                .setContentIntent(gidilecekIntent)
                .setAutoCancel(true) // bildirimi seçtiğimiz zaman kendisini yukarıdan kaldıracak demek.
                .priority = Notification.PRIORITY_HIGH // öncelik - bildirimimize sistemde öncelik vericek.
        }

        bildirimYoneticisi.notify(1,builder.build()) //bildirimi gösterme
    }
}