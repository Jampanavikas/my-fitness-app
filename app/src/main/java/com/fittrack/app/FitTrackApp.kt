package com.fittrack.app

import android.app.Application
import com.fittrack.app.data.FitRepository

class FitTrackApp : Application() {
    val repository: FitRepository by lazy { FitRepository(this) }
}
