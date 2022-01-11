package com.bignerdranchguide.astromify.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.bignerdranchguide.astromify.extensions.log

class LifecycleEventObserver: LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        log(this, event.toString())
    }
}