package space.iqbalsyafiq.rakitpc.model.network

import retrofit2.Call
import retrofit2.http.GET
import space.iqbalsyafiq.rakitpc.BuildConfig

interface SheetApi {
    @GET("124kAz7Tiq1mFkLHrPCrw9TzK9yGVUFXriUTQKHoQu2Y/values/Pertanyaan?key=${BuildConfig.API_KEY}")
    fun getQuestions(): Call<DataResponse>

    @GET("17MWu5vKOHK0hARpFSeWzGWxj5KNKI26fJNFKIzKsFOI/values/Rules?key=${BuildConfig.API_KEY}")
    fun getRules(): Call<DataResponse>
}