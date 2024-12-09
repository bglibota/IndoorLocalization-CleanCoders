package com.example.indoorlocalizationcleancoders.data.api

import android.content.Context
import android.util.Log
import com.example.indoorlocalizationcleancoders.R
import okhttp3.OkHttpClient
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object SafeOkHttpClient {

    fun getSafeOkHttpClient(context: Context): OkHttpClient {
        Log.d("SafeOkHttpClient", "Start initializing OkHttpClient")

        val certificateInputStream: InputStream = context.resources.openRawResource(R.raw.localhost)
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val certificate = certificateFactory.generateCertificate(certificateInputStream) as X509Certificate

        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null, null)
        keyStore.setCertificateEntry("my_cert", certificate)

        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)

        val trustManagers = trustManagerFactory.trustManagers
        val trustManager = trustManagers[0] as X509TrustManager

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf(trustManager), java.security.SecureRandom())

        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .hostnameVerifier { _, _ -> true }
            .build()
    }
}