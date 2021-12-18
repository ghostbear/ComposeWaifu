package me.ghostbear.composewaifu.data.mapper

import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.local.model.Waifu as LocalWaifu
import me.ghostbear.composewaifu.remote.model.WaifuCollection as RemoteWaifu

fun RemoteWaifu.toDomain(): List<Waifu> {
    return files.map { url ->
        Waifu(
            url = url
        )
    }
}

fun RemoteWaifu.toData(): List<LocalWaifu> {
    return files.map { url ->
        LocalWaifu(
            url = url
        )
    }
}

fun LocalWaifu.toDomain(): Waifu {
    return Waifu(
        url = url
    )
}

fun Waifu.toLocal(): LocalWaifu {
    return LocalWaifu(
        url = url
    )
}