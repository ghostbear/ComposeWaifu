package me.ghostbear.data.waifu.mapper

import me.ghostbear.data.waifu.local.model.Waifu as LocalWaifu
import me.ghostbear.data.waifu.remote.model.WaifuCategory as RemoteWaifuCategory
import me.ghostbear.data.waifu.remote.model.WaifuCollection as RemoteWaifu
import me.ghostbear.data.waifu.remote.model.WaifuType as RemoteWaifuType
import me.ghostbear.domain.waifu.model.Waifu
import me.ghostbear.domain.waifu.model.WaifuCategory
import me.ghostbear.domain.waifu.model.WaifuType

fun RemoteWaifu.toDomain(): List<Waifu> {
    return files.map { url ->
        Waifu(
            url = url
        )
    }
}

fun RemoteWaifu.toLocal(): List<LocalWaifu> {
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

fun WaifuCategory.toRemote(): RemoteWaifuCategory {
    return RemoteWaifuCategory.values()
        .find { it.name == this.name }
        ?: RemoteWaifuCategory.WAIFU
}

fun WaifuType.toRemote(): RemoteWaifuType {
    return RemoteWaifuType.values()
        .find { it.name == this.name }
        ?: RemoteWaifuType.SFW
}