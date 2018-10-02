package laviola.pucminas.espressoapp

import laviola.pucminas.espressoapp.app.MyApplication
import io.appflate.restmock.RESTMockServer

class TestApplication : MyApplication() {

    override fun getApiUrl(): String {
        return RESTMockServer.getUrl()
    }
}