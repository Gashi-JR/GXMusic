import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Url

interface ApiService {
    @GET
    fun getData(@Url url: String): Call<ResponseClass>

    @POST
    fun postData(@Url url: String, @Body requestBody: Any): Call<ResponseClass>

    @PUT
    fun putData(@Url url: String, @Body requestBody: Any): Call<ResponseClass>

    @DELETE
    fun deleteData(@Url url: String): Call<ResponseClass>
}

data class ResponseClass(
    val id: Int,
    val name: String,
    val email: String
)

object NetworkUtils {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.example.com/") // 设置基本 URL
        .addConverterFactory(GsonConverterFactory.create()) // 设置 Gson 转换器
        .build()

    suspend fun https(url: String, method: String, requestBody: Any): String? {
        val apiService = retrofit.create(ApiService::class.java)

        return withContext(Dispatchers.IO) {
            val response = when (method) {
                "GET" -> apiService.getData(url).execute()
                "POST" -> apiService.postData(url, requestBody).execute()
                "PUT" -> apiService.putData(url, requestBody).execute()
                "DELETE" -> apiService.deleteData(url).execute()
                else -> null
            }

            if (response?.isSuccessful == true) {
                val gson = Gson()
                gson.toJson(response.body())
            } else {
                null
            }
        }
    }
}
