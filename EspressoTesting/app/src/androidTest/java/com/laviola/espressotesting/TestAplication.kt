package com.laviola.espressotesting

import com.laviola.espressotesting.app.MyApplication
import io.appflate.restmock.RESTMockServer

class TestApplication : MyApplication() {

    override fun getApiUrl(): String {
        return RESTMockServer.getUrl()
    }
}