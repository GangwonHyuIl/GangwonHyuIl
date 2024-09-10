package com.gangwonhyuil.gangwonhyuil.data.interceptor

import com.gangwonhyuil.gangwonhyuil.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import timber.log.Timber
import javax.inject.Inject

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class PostJsonRequest

class SupabaseInterceptor
@Inject
constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder =
            chain
                .request()
                .newBuilder()
                .apply {
                    addHeader("accept", "application/json")
                    addHeader("apikey", "${BuildConfig.SUPABASE_API_KEY}")
                }

        chain.request().tag(Invocation::class.java)?.let { invocation ->
            containedOnInvocation(invocation).forEach { annotation ->
                Timber.d("annotation : $annotation")
                requestBuilder.apply {
                    when (annotation) {
                        is PostJsonRequest -> {
                            addHeader("Content-Type", "application/json")
                        }

                        else -> {
                            Timber.e("unknown annotation")
                        }
                    }
                }
            }
        }
        return chain.proceed(requestBuilder.build())
    }

    private fun containedOnInvocation(invocation: Invocation): Set<Annotation> =
        invocation.method().annotations.toSet()
}
