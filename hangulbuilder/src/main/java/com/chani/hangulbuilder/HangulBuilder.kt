package com.chani.hangulbuilder

import java.lang.StringBuilder

/**
 * Copyright (C) 2020 dncks1525
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

object HangulBuilder {
    private const val BASE_UNICODE = '가'
    private const val JUNGSEONG_COUNT = 21
    private const val JONGSEONG_COUNT = 28

    private val choseongList = listOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ',
        'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ',
        'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )
    private val jungseongList = listOf(
        'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ',
        'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ',
        'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'
    )
    private val jongseongList = listOf(
        '\u0000',  'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ',
        'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ',
        'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ',
        'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )
    private val detachableConsonants = listOf(
        Detachable('ㄳ', 'ㄱ', 'ㅅ'),
        Detachable('ㄵ', 'ㄴ', 'ㅈ'),
        Detachable('ㄶ', 'ㄴ', 'ㅎ'),
        Detachable('ㄺ', 'ㄹ', 'ㄱ'),
        Detachable('ㄻ', 'ㄹ', 'ㅁ'),
        Detachable('ㄼ', 'ㄹ', 'ㅂ'),
        Detachable('ㄽ', 'ㄹ', 'ㅅ'),
        Detachable('ㄾ', 'ㄹ', 'ㅌ'),
        Detachable('ㄿ', 'ㄹ', 'ㅍ'),
        Detachable('ㅄ', 'ㅂ', 'ㅅ')
    )
    private val detachableVowels = listOf(
        Detachable('ㅘ', 'ㅗ', 'ㅏ'),
        Detachable('ㅙ', 'ㅗ', 'ㅐ'),
        Detachable('ㅚ', 'ㅗ', 'ㅣ'),
        Detachable('ㅝ', 'ㅜ', 'ㅓ'),
        Detachable('ㅞ', 'ㅜ', 'ㅔ'),
        Detachable('ㅟ', 'ㅜ', 'ㅣ'),
        Detachable('ㅢ', 'ㅡ', 'ㅣ')
    )

    private val decomposeBuilder = StringBuilder()
    private val hangulBuilder = StringBuilder()

    fun add(vararg str: String): String {
        for (l in str) add(l)
        return hangulBuilder.toString()
    }

    private fun add(str: String) {
        if (str.isNotEmpty()) {
            if (hangulBuilder.isEmpty()) {
                hangulBuilder.append(str)
            } else {
                hangulBuilder.append(str)
                val size = hangulBuilder.length
                val last = hangulBuilder[size-2]
                val target = hangulBuilder[size-1]

                when {
                    last.isConsonant() -> {
                        if (target.isVowel()) {
                            // ex) ㄱ + ㅏ -> 가
                            hangulBuilder.delete(size-2, size)
                            hangulBuilder.append(compose(last, target))
                        }
                    }
                    last.isVowel() -> {
                        if (target.isVowel()) {
                            // ex) ㅗ + ㅐ -> ㅙ
                            detachableVowels.find { it.first == last && it.second == target }?.let {
                                hangulBuilder.delete(size-2, size)
                                hangulBuilder.append(it.origin)
                            }
                        }
                    }
                    last.isHangulLetter() -> {
                        decompose(last.toString()).apply {
                            if (this.length > 2) {
                                if (target.isVowel()) {
                                    hangulBuilder.delete(size - 2, size)

                                    val consonants =
                                        detachableConsonants.find { it.origin == this[2] }
                                    if (consonants != null) {
                                        // ex) 갉 + ㅏ -> 갈가
                                        hangulBuilder.append(compose(this[0], this[1], consonants.first))
                                        hangulBuilder.append(compose(consonants.second, target))
                                    } else {
                                        // ex) 갈 + ㅏ -> 가라
                                        hangulBuilder.append(compose(this[0], this[1]))
                                        hangulBuilder.append(compose(this[2], target))
                                    }
                                } else {
                                    // ex) 갈 + ㄱ -> 갉
                                    detachableConsonants.find {
                                        it.first == this[2] && it.second == target
                                    }?.let {
                                        hangulBuilder.delete(size - 2, size)
                                        hangulBuilder.append(compose(this[0], this[1], it.origin))
                                    }
                                }
                            } else {
                                when {
                                    target.isVowel() -> {
                                        // ex) 고 + ㅐ -> 괘
                                        detachableVowels.find {
                                            it.first == this[1] && it.second == target
                                        }?.let {
                                            hangulBuilder.delete(size - 2, size)
                                            hangulBuilder.append(compose(this[0], it.origin))
                                        }
                                    }
                                    target.isConsonant() -> {
                                        // ex) 고 + ㄱ -> 곡
                                        if (jongseongList.contains(target)) {
                                            hangulBuilder.delete(size - 2, size)
                                            hangulBuilder.append(compose(this[0], this[1], target))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun remove(): String {
        if (hangulBuilder.isNotEmpty()) {
            val last = hangulBuilder.last()
            val size = hangulBuilder.length

            when {
                last.isHangulLetter() -> {
                    hangulBuilder.delete(size-1, size)

                    decompose(last.toString()).apply {
                        if (this.length > 2) {
                            val consonants = detachableConsonants.find { it.origin == this[2] }
                            hangulBuilder.append(
                                if (consonants != null) {
                                    // ex) 갉 -> 갈
                                    compose(this[0], this[1], consonants.first)
                                } else {
                                    // ex) 갈 -> 가
                                    compose(this[0], this[1])
                                }
                            )
                        } else if (this.length > 1) {
                            val vowels = detachableVowels.find { it.origin == this[1] }
                            if (vowels != null) {
                                // ex) 쇄 -> 소
                                hangulBuilder.append(compose(this[0], vowels.first))
                            } else {
                                // ex) 갈가 -> 갉
                                add(this[0].toString())
                            }
                        }
                    }
                }
                last.isVowel() -> {
                    // ex) ㅙ -> ㅗ
                    hangulBuilder.delete(size-1, size)

                    detachableVowels.find { it.origin == last }?.let {
                        hangulBuilder.append(it.first)
                    }
                }
                else -> {
                    hangulBuilder.delete(size-1, size)
                }
            }
        }

        return hangulBuilder.toString()
    }

    fun clear() {
        hangulBuilder.clear()
    }

    @JvmOverloads
    fun compose(choseong: Char, jungseong: Char, jongseong: Char = '\u0000'): String =
        (BASE_UNICODE
        + (choseongList.indexOf(choseong) * JUNGSEONG_COUNT * JONGSEONG_COUNT)
        + (jungseongList.indexOf(jungseong) * JONGSEONG_COUNT)
        + (jongseongList.indexOf(jongseong))).toString()

    @JvmOverloads
    fun decompose(str: String, separator: String = "", isSeparateAll: Boolean = false): String {
        if (str.isEmpty()) return str

        decomposeBuilder.clear()
        if (isSeparateAll) {
            str.forEachIndexed { index, c ->
                decomposeBuilder.append(if (c.isHangulLetter()) c.separate(separator) else c)
                if (index != str.lastIndex) decomposeBuilder.append(separator)
            }
        } else {
            val c = str.last()
            decomposeBuilder.append(if (c.isHangulLetter()) c.separate() else c)
        }

        return decomposeBuilder.toString()
    }

    private fun Char.separate(separator: String = ""): String {
        val base = this - BASE_UNICODE
        val jongseongIdx = base % JONGSEONG_COUNT
        val jungseongIdx = ((base - jongseongIdx) / JONGSEONG_COUNT) % JUNGSEONG_COUNT
        val choseongIdx = (((base - jongseongIdx) / JONGSEONG_COUNT) - jungseongIdx) / JUNGSEONG_COUNT

        return if (jongseongIdx > 0) {
            "%c%s%c%s%c".format(
                choseongList[choseongIdx],
                separator,
                jungseongList[jungseongIdx],
                separator,
                jongseongList[jongseongIdx]
            )
        } else {
            "%c%s%c".format(choseongList[choseongIdx], separator, jungseongList[jungseongIdx])
        }
    }

    private fun Char.isConsonant() = toString().matches(".*[ㄱ-ㅎ]+.*".toRegex())

    private fun Char.isVowel() = toString().matches(".*[ㅏ-ㅣ]+.*".toRegex())

    private fun Char.isHangulLetter() = toString().matches("^[가-힣]*\$".toRegex())

    private data class Detachable(val origin: Char, val first: Char, val second: Char)
}