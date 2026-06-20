package com.brokenkernel.improvtools.buzzer.model

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.brokenkernel.improvtools.buzzer.R

internal data class BuzzerType(
    @StringRes val nameRes: Int,
    @RawRes val soundId: Int,
)

internal object BuzzerDatum {
    internal val buzzers: List<BuzzerType> = listOf(
        BuzzerType(
            nameRes = R.string.tone_gong,
            soundId = R.raw.freesound_community_gong_92707,
        ),
        BuzzerType(
            nameRes = R.string.tone_bell,
            soundId = R.raw.universfield_clear_bell_chime_487898,
        ),
        BuzzerType(
            nameRes = R.string.tone_buzz,
            soundId = R.raw.eritnhut1992_buzzer_or_wrong_answer_20582,
        ),
        BuzzerType(
            nameRes = R.string.tone_beep,
            soundId = R.raw.dragon_studio_censor_beep_1_372459,
        ),
        BuzzerType(
            nameRes = R.string.tone_rimshot,
            soundId = R.raw.freesound_community_rimshot_joke_funny_80325,
        ),
        BuzzerType(
            nameRes = R.string.tone_sadtrombone,
            soundId = R.raw.freesound_community_wah_wah_sad_trombone_6347,
        ),
    )
}
