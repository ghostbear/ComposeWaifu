package me.ghostbear.domain.waifu.model

val sfw = listOf(
    WaifuCategory.WAIFU,
    WaifuCategory.NEKO,
    WaifuCategory.SHINOBU,
    WaifuCategory.MEGUMIN,
    WaifuCategory.BULLY,
    WaifuCategory.CUDDLE,
    WaifuCategory.CRY,
    WaifuCategory.HUG,
    WaifuCategory.AWOO,
    WaifuCategory.KISS,
    WaifuCategory.LICK,
    WaifuCategory.PAT,
    WaifuCategory.SMUG,
    WaifuCategory.BONK,
    WaifuCategory.YEET,
    WaifuCategory.BLUSH,
    WaifuCategory.SMILE,
    WaifuCategory.WAVE,
    WaifuCategory.HIGHFIVE,
    WaifuCategory.HANDHOLD,
    WaifuCategory.NOM,
    WaifuCategory.BITE,
    WaifuCategory.GLOMP,
    WaifuCategory.SLAP,
    WaifuCategory.KILL,
    WaifuCategory.KICK,
    WaifuCategory.HAPPY,
    WaifuCategory.WINK,
    WaifuCategory.POKE,
    WaifuCategory.DANCE,
    WaifuCategory.CRINGE,
)

val nsfw = listOf(
    WaifuCategory.WAIFU,
    WaifuCategory.NEKO,
    WaifuCategory.TRAP,
    WaifuCategory.BLOWJOB,
)

enum class WaifuType(val categories: List<WaifuCategory>) {
    SFW(sfw),
    NSFW(nsfw)
}