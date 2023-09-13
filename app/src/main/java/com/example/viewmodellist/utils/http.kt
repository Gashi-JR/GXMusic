import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url


interface ApiService {
    @GET
    fun getData(@Url url: String): Call<ResponseBody>

    @POST
    fun postData(@Url url: String, @Body requestBody: Any): Call<ResponseBody>

    @PUT
    fun putData(@Url url: String, @Body requestBody: Any): Call<ResponseBody>

    @DELETE
    fun deleteData(@Url url: String): Call<ResponseBody>
}

var cookieJar: CookieJar = object : CookieJar {
    private val cookieStore = HashMap<String, List<Cookie>>()
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore[url.host] = cookies
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookies = cookieStore[url.host]
        return cookies ?: ArrayList()
    }
}


object NetworkUtils {
    private val client = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://26hpfk.natappfree.cc") // 设置基本 URL
        .addConverterFactory(GsonConverterFactory.create()) // 设置 Gson 转换器
        .client(client)
        .build()

    suspend fun https(url: String, method: String, requestBody: Any = ""): String? {
        val apiService = retrofit.create(ApiService::class.java)

        return withContext(Dispatchers.IO) {
            val response = when (method) {
                "GET" -> apiService.getData(url).execute()
                "POST" -> apiService.postData(url, requestBody).execute()
                "PUT" -> apiService.putData(url, requestBody).execute()
                "DELETE" -> apiService.deleteData(url).execute()
                else -> null
            }
            if (response != null && response.code() == 403) {
                val responseBody = "{\"code\":403}"
                val res = responseBody
                Log.d("responseBody", "responseBody: $responseBody")
                res // 返回响应体的字符串内容
            } else if (response != null && response.isSuccessful) {
                val responseBody = response.body()
                val res = responseBody?.string()
                Log.d("getData", "getData: $res")
                responseBody?.close() // 关闭响应体，以便后续调用 response.body()?.string() 返回结果
                res // 返回响应体的字符串内容
            } else {
                "httpError"
            }
        }
    }
}
