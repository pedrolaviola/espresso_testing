package laviola.pucminas.espressoapp

import android.app.Application
import android.content.Context
import io.appflate.restmock.android.RESTMockTestRunner

class CustomTestRunner : RESTMockTestRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader,
                                className: String,
                                context: Context): Application {

        //I'm changing the application class only for test purposes. there I'll instantiate AppModule with RESTMock's url.
        return super.newApplication(cl, TestApplication::class.java.canonicalName, context)
    }
}