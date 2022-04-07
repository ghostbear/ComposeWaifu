package me.ghostbear.data.waifu.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.putJsonArray
import me.ghostbear.data.waifu.remote.model.Waifu
import me.ghostbear.data.waifu.remote.model.WaifuCategory
import me.ghostbear.data.waifu.remote.model.WaifuCollection
import me.ghostbear.data.waifu.remote.model.WaifuType

class WaifuApiImpl @Inject constructor(
    private val httpClient: HttpClient
) : WaifuApi {

    override suspend fun getImage(type: WaifuType, category: WaifuCategory): Waifu {
        return httpClient.get("https://api.waifu.pics/${type.name.lowercase()}/${category.name.lowercase()}")
    }

    override suspend fun getImages(type: WaifuType, category: WaifuCategory, excludes: List<String>): WaifuCollection {
        return httpClient.post("https://api.waifu.pics/many/${type.name.lowercase()}/${category.name.lowercase()}") {
            contentType(ContentType.Application.Json)
            body = buildJsonObject {
                putJsonArray("exclude") {
                    excludes.forEach { add(it) }
                }
            }
        }
    }

}