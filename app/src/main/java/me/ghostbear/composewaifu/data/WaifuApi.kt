package me.ghostbear.composewaifu.data

import me.ghostbear.composewaifu.data.model.WaifuCategory
import me.ghostbear.composewaifu.data.model.WaifuCollection
import me.ghostbear.composewaifu.data.model.Waifu
import me.ghostbear.composewaifu.data.model.WaifuType

interface WaifuApi {

    /**
     * Get image
     * Receive one image url from your endpoint of choice.
     */
    suspend fun getImage(type: WaifuType, category: WaifuCategory): Waifu

    /**
     * Get many images
     * Receive 30 unique images from a specific endpoint or category
     */
    suspend fun getImages(type: WaifuType, category: WaifuCategory): WaifuCollection

}