package me.ghostbear.data.waifu.mapper

import me.ghostbear.data.waifu.local.model.Waifu as LocalWaifu
import me.ghostbear.data.waifu.local.model.WaifuCategory as LocalWaifuCategory
import me.ghostbear.data.waifu.local.model.WaifuType as LocalWaifuType
import me.ghostbear.data.waifu.remote.model.WaifuCategory as RemoteWaifuCategory
import me.ghostbear.data.waifu.remote.model.WaifuCollection as RemoteWaifu
import me.ghostbear.data.waifu.remote.model.WaifuType as RemoteWaifuType
import me.ghostbear.domain.waifu.model.Waifu
import me.ghostbear.domain.waifu.model.WaifuCategory
import me.ghostbear.domain.waifu.model.WaifuType

fun RemoteWaifu.toDomain(type: WaifuType, category: WaifuCategory): List<Waifu> {
    return files.map { url ->
        Waifu(
            url = url,
            type = type,
            category = category
        )
    }
}

fun RemoteWaifu.toLocal(type: LocalWaifuType, category: LocalWaifuCategory): List<LocalWaifu> {
    return files.map { url ->
        LocalWaifu(
            url = url,
            type = type,
            category = category
        )
    }
}

fun LocalWaifu.toDomain(): Waifu {
    return Waifu(
        url = url,
        isFavorite,
        updatedAt,
        type.toDomain(),
        category.toDomain()
    )
}

fun Waifu.toLocal(): LocalWaifu {
    return LocalWaifu(
        url = url,
        isFavorite = isFavorite,
        updatedAt = updatedAt,
        type = type.toLocal(),
        category = category.toLocal()
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

fun WaifuCategory.toLocal(): LocalWaifuCategory {
    return LocalWaifuCategory.values()
        .find { it.name == this.name }
        ?: LocalWaifuCategory.WAIFU
}

fun WaifuType.toLocal(): LocalWaifuType {
    return LocalWaifuType.values()
        .find { it.name == this.name }
        ?: LocalWaifuType.SFW
}

fun LocalWaifuCategory.toDomain(): WaifuCategory {
    return WaifuCategory.values()
        .find { it.name == this.name }
        ?: WaifuCategory.WAIFU
}

fun LocalWaifuType.toDomain(): WaifuType {
    return WaifuType.values()
        .find { it.name == this.name }
        ?: WaifuType.SFW
}