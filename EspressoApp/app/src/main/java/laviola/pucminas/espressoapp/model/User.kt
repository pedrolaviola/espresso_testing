package laviola.pucminas.espressoapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(@SerializedName("name")val name: String,
           @SerializedName("job")val job : String) :Parcelable