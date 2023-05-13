package com.vaibhavmojidra.androidkotlindemoparalleldecompositionusingcoroutinesasyncawait

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.vaibhavmojidra.androidkotlindemoparalleldecompositionusingcoroutinesasyncawait.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        CoroutineScope(Dispatchers.Main).launch {

            //This is without Parallel Decomposition (Without Parallel Decomposition)

//            val startTime = Instant.now()
//            val bytesDownloaded1=download1()
//            val bytesDownloaded2=download2()
//            val totalBytesDownloaded=bytesDownloaded1+bytesDownloaded2
//            val endTime = Instant.now()
//            val timeDifferenceSeconds = Duration.between(startTime, endTime).seconds
//            binding.totalDownloadsTextView.text="All Downloads Completed : Total $totalBytesDownloaded bytes downloaded"
//            binding.timeTakenTextview.text="Total time taken to complete downloads : $timeDifferenceSeconds seconds"

            //This is with Parallel Decomposition (With Parallel Decomposition)

            val startTime = Instant.now()
            val bytesDownloaded1=async {  download1() }
            val bytesDownloaded2=async {  download2() }
            val totalBytesDownloaded=bytesDownloaded1.await()+bytesDownloaded2.await()
            val endTime = Instant.now()
            val timeDifferenceSeconds = Duration.between(startTime, endTime).seconds
            binding.totalDownloadsTextView.text="All Downloads Completed : Total $totalBytesDownloaded bytes downloaded"
            binding.timeTakenTextview.text="Total time taken to complete downloads : $timeDifferenceSeconds seconds"
        }
    }

    private suspend fun download1():Int{
        for (i in 1..25){
            delay(1000)
        }
        binding.download1TextView.text="Download 1 Completed : 25000 bytes downloaded"
        return 25000
    }

    private suspend fun download2():Int{
        for (i in 1..10){
            delay(1000)
        }
        binding.download2TextView.text="Download 2 Completed : 10000 bytes downloaded"
        return 10000
    }
}