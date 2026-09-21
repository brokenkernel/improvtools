package com.brokenkernel.improvtools.coreinfra

/**
 * An easy to use 'tag' for logging in android.
 */
public val Any.TAG: String
    get() = this::class.java.simpleName
