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
package com.skt.nugu.sdk.platform.android.ux.template

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.util.TypedValue
import com.skt.nugu.sdk.client.theme.ThemeManager
import com.skt.nugu.sdk.client.theme.ThemeManagerInterface
import org.json.JSONObject
import java.util.*

class TemplateUtils {
    companion object {
        private const val SUPPORT_FOCUSED_ITEM_TOKEN = "supportFocusedItemToken"
        private const val SUPPORT_VISIBLE_TOKEN_LIST = "supportVisibleTokenList"

        fun dpToPixel(context: Context, dp: Float): Float {
            return (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.resources.displayMetrics
            ) + 0.5f)
        }

        fun convertToTime(second: Int): String {
            val milis = second * 1000
            if (milis / (1000 * 60 * 60) > 0) {
                return String.format(
                    Locale.getDefault(), "%02d:%02d:%02d",
                    (milis / (1000 * 60 * 60)),
                    (milis / (1000 * 60) % 60),
                    (milis / 1000 % 60)
                )
            } else {
                return String.format(
                    Locale.getDefault(), "%02d:%02d",
                    (milis / (1000 * 60) % 60),
                    (milis / 1000 % 60)
                )
            }
        }

        fun convertToTimeMs(ms: Int): String {
            if (ms / (1000 * 60 * 60) > 0) {
                return String.format(
                    Locale.getDefault(), "%02d:%02d:%02d",
                    (ms / (1000 * 60 * 60)),
                    (ms / (1000 * 60) % 60),
                    (ms / 1000 % 60)
                )
            } else {
                return String.format(
                    Locale.getDefault(), "%02d:%02d",
                    (ms / (1000 * 60) % 60),
                    (ms / 1000 % 60)
                )
            }
        }

        fun parseColor(color: String?): Int? {
            if (color == null) {
                return null
            }
            return try {
                Color.parseColor(color)
            } catch (e: Throwable) {
                null
            }
        }

        fun getSpannable(text: String?): Spanned {
            if (text == null) {
                return SpannableStringBuilder("")
            }
            var source = text.replace("\n", "<br>")
            source = source.replace("</br>", "<br>")
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(source)
            }
        }

        fun updateLayoutParams(view: android.widget.ToggleButton?, width: Float, height: Float) {
            view?.apply {
                val params = layoutParams
                params.width = dpToPixel(context, width).toInt()
                params.height = dpToPixel(context, height).toInt()
                layoutParams = params
                requestLayout()
            }
        }

        fun isSupportFocusedItemToken(template: String): Boolean =
            runCatching {
                JSONObject(template).getBoolean(SUPPORT_FOCUSED_ITEM_TOKEN)
            }.getOrDefault(false)

        fun isSupportVisibleTokenList(template: String): Boolean =
            runCatching {
                JSONObject(template).getBoolean(SUPPORT_VISIBLE_TOKEN_LIST)
            }.getOrDefault(false)

        fun themeToString(theme: ThemeManagerInterface.THEME, configuration: Configuration): String {
            return when (theme) {
                ThemeManagerInterface.THEME.LIGHT -> "light"
                ThemeManagerInterface.THEME.DARK -> "dark"
                ThemeManagerInterface.THEME.SYSTEM -> {
                    when (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                        Configuration.UI_MODE_NIGHT_NO -> "light"
                        Configuration.UI_MODE_NIGHT_YES -> "dark"
                        else -> {
                            if (ThemeManager.DEFAULT_THEME == ThemeManagerInterface.THEME.LIGHT) "light"
                            else "dark"
                        }
                    }
                }
            }
        }
    }
}