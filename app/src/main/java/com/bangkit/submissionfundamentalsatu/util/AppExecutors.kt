package com.bangkit.submissionfundamentalsatu.util

import android.os.Looper
import android.os.Handler
import java.util.concurrent.Executor

class AppExecutors {
    private class MainThreadExecutor : Executor {
        private val mainThreadExecutor = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadExecutor.post(command)
        }

    }
}