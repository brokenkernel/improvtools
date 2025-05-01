package com.brokenkernel.improvtools

/**
 * An easy to use 'tag' for logging in android.
 */
val Any.TAG: String
    get() = this::class.java.simpleName
