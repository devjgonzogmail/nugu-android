/**
 * Copyright (c) 2020 SK Telecom Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.skt.nugu.sdk.platform.android.ux.template.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.skt.nugu.sdk.agent.display.DisplayAgent

@Keep
enum class LyricsType {
    @SerializedName("NONE")
    NONE,

    @SerializedName("SYNC")
    SYNC,

    @SerializedName("NON_SYNC")
    NON_SYNC
}

@Keep
enum class Repeat {
    @SerializedName("ALL")
    ALL,

    @SerializedName("ONE")
    ONE,

    @SerializedName("NONE")
    NONE
}

@Keep
class AudioPlayer(
    @SerializedName("title") val title: AudioPlayerTitle?,
    @SerializedName("content") val content: AudioPlayerContent?
)

@Keep
class AudioPlayerUpdate(
    @SerializedName("template") val player: AudioPlayer,
)

@Keep
data class AudioPlayerTitle(
    @SerializedName("iconUrl") val iconUrl: String?,
    @SerializedName("text") val text: String?,
)

@Keep
data class AudioPlayerContent(
    @SerializedName("title") val title: String?,
    @SerializedName("subtitle1") val subtitle1: String?,
    @SerializedName("subtitle2") val subtitle2: String?,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("durationSec") val durationSec: String?,
    @SerializedName("backgroundImageUrl") val backgroundImageUrl: String?,
    @SerializedName("backgroundColor") val backgroundColor: String?,
    @SerializedName("badgeMessage") val badgeMessage: String?,
    @SerializedName("badgeImageUrl") val badgeImageUrl: String?,
    @SerializedName("lyrics") val lyrics: Lyrics?,
    @SerializedName("settings") val settings: Settings?,
)

@Keep
data class Playlist(
    val type: String = "",
    val title: TextFormat? = null,
    val subTitle: TextFormat? = null,
    val token: String = "",
    val edit: TextFormat? = null,
    val button: ButtonObject? = null,
    val currentToken: String? = null,
    val list: ListObject? = null
) {
    @Keep
    data class TextFormat(val text: TextObject? = null, val imageUrl: String? = null)

    @Keep
    data class ListObject(
        val replaceType: String? = null,
        val items: List<PlayListItem> = emptyList()
    )

    @Keep
    data class PlayListItem(
        val text: TextObject = TextObject(),
        val subText: TextObject? = null,
        val imageUrl: String? = null,
        val badgeUrl: String? = null,
        val badgeMessage: String? = null,
        val available: Boolean = true, // default value is true
        val token: String = "",
        var favorite: Favorite? = null,
        var postback: Any? = null,
    ) {
        @Keep
        data class Favorite(
            val text: TextObject? = null,
            val imageUrl: String? = null,
            val status: Boolean? = null,
            val token: String = "",
            val postback: Any? = null
        )
    }
}

@Keep
data class ButtonObject(
    val type: String? = null,
    val image: ImageObject? = null,
    val text: String? = null,
    val token: String = "",
    val eventType: String? = null,
    val textInput: TextInput? = null,
    val postback: Any? = null,
    val autoTrigger: AutoTrigger? = null,
    val closeTemplateAfter: Boolean = false
) {
    @Keep
    data class ImageObject(
        val contentDescription: String? = null, val list: List<ImageSource>? = null
    ) {
        @Keep
        data class ImageSource(
            val url: String = "", val size: String? = null, val widthPixels: Long? = null, val heightPixels: Long? = null
        )
    }

    @Keep
    data class TextInput(
        val text: String = "", val playServiceId: String? = null
    )

    @Keep
    data class AutoTrigger(
        val delayInMilliseconds: Long = 0L, val showTimer: Boolean = false
    )
}

@Keep
data class TextObject(
    val text: String = "", val maxLine: Int? = null
)

@Keep
data class Settings(
    @SerializedName("favorite") val favorite: Boolean?,
    @SerializedName("repeat") val repeat: Repeat?,
    @SerializedName("shuffle") val shuffle: Boolean?,
)

@Keep
data class Lyrics(
    @SerializedName("title") val title: String?,
    @SerializedName("lyricsType") val lyricsType: LyricsType?,
    @SerializedName("lyricsInfoList") val lyricsInfoList: List<LyricsInfo>?,
    @SerializedName("showButton") val showButton: ShowButtonText?,
)

@Keep
data class ShowButtonText(
    @SerializedName("text") val text: String,
)

@Keep
data class LyricsInfo(
    @SerializedName("time") val time: Int?,
    @SerializedName("text") val text: String?,
)

@Keep
data class ClientInfo(
    var theme: String = "",
    var buttonColor: String = "",
    val displayInterfaceVersion: String = DisplayAgent.VERSION.toString(),
    var disableCloseButton: Boolean = false,
)