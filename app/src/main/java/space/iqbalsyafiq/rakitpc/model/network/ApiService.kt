package space.iqbalsyafiq.rakitpc.model.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService() {
    private val BASE_URL = "https://sheets.googleapis.com/v4/spreadsheets/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(SheetApi::class.java)

    fun getQuestionList(): Call<DataResponse> {
        return api.getQuestions()
    }

    fun getRuleList(): Call<DataResponse> {
        return api.getRules()
    }
}