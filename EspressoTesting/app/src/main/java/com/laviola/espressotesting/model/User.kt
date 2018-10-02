package com.laviola.espressotesting.model

import com.google.gson.annotations.SerializedName

class User(@SerializedName("name")val name: String,
           @SerializedName("job")val job : String)