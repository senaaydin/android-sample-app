package com.root.security.sample

import android.app.Activity
import com.root.security.AdessoSecurityProvider
import com.root.security.PublicKeyPinner
import com.root.security.SocketProvider
import com.root.security.TrustStore
import com.root.security.utility.CertificateUtility
import okhttp3.OkHttpClient

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 21.06.2021
 */
class SampleUsage : Activity() {

    fun blabla() {

        val playSecurity = AdessoSecurityProvider.getDefaultSecurityProvider(this)
        val trustStore = AdessoSecurityProvider.getTrustStore()
        val socketProvider = AdessoSecurityProvider.getSocketProvider()
        val publicKeyPinner = AdessoSecurityProvider.getOkHttpPublicKeyPinner()

        playSecurity.update()
        okHttpCertificatePinning(trustStore, socketProvider)
        okHttpPublicKeyPinning(publicKeyPinner)
    }

    private fun okHttpPublicKeyPinning(publicKeyPinner: PublicKeyPinner) {
        publicKeyPinner
            .add(
                "*.adesso.com",
                CertificateUtility.fromAssets(this)[0],
                PublicKeyPinner.Algorithm.SHA_1
            )
            .add("**.adesso.com", CertificateUtility.fromAssets(this)[0])
            .add("www.adesso.com", CertificateUtility.fromAssets(this)[0])
            .pin()
    }

    private fun okHttpCertificatePinning(trustStore: TrustStore, socketProvider: SocketProvider) {
        trustStore.trust(
            "alias",
            CertificateUtility.fromFile(
                filesDir.path,
                "app_certificate",
                CertificateUtility.Extension.DER
            )
        )
        val builder = OkHttpClient.Builder()
        AdessoSecurityProvider
            .getOkHttpCertPinner()
            .pin(builder, socketProvider.getFactory(), trustStore.getTrustManagers()[0])
    }
}