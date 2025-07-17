package com.example.data.network.util

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION

/**
 * Annotation to indicate that a network request does not require authentication.
 */
@Target(FUNCTION)
@Retention(RUNTIME)
annotation class NoAuth