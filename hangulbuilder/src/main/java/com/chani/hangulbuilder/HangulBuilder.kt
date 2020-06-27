package com.chani.hangulbuilder

import java.lang.StringBuilder

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

    private val letterList = mutableListOf<String>()
    private val strBuilder = StringBuilder()

    fun add(letter: String, index: Int = letter.lastIndex) {
        
    }

    fun remove() {

    }

    fun clear() {

    }

    fun assemble(choseong: Char, jungseong: Char, jongseong: Char = '\u0000'): String =
        (BASE_UNICODE
        + (choseongList.indexOf(choseong) * JUNGSEONG_COUNT * JONGSEONG_COUNT)
        + (jungseongList.indexOf(jungseong) * JONGSEONG_COUNT)
        + (jongseongList.indexOf(jongseong))).toString()

    fun disassemble(str: String, isSeparateAll: Boolean = false, separator: String = ""): String {
        if (str.isEmpty()) return str

        strBuilder.clear()
        if (isSeparateAll) {
            str.forEachIndexed { index, c ->
                strBuilder.append(if (c.isHangulLetters()) c.separate(separator) else c)
                if (index != str.lastIndex) strBuilder.append(separator)
            }
        } else {
            val c = str.last()
            strBuilder.append(if (c.isHangulLetters()) c.separate() else c)
        }

        return strBuilder.toString()
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

    private fun Char.isHangulLetters() = toString().matches("^[가-힣]*\$".toRegex())

    private data class Detachable(val origin: Char, val first: Char, val second: Char)
}