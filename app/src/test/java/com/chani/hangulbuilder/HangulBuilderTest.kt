package com.chani.hangulbuilder

import org.junit.Test
import com.google.common.truth.Truth.*

class HangulBuilderTest {
    val builder = HangulBuilder

    @Test
    fun assembleTest() {
        builder.compose('ㄱ', 'ㅏ').also { assertThat(it).isEqualTo("가") }

        builder.compose('ㄱ', 'ㅏ', 'ㄳ').also { assertThat(it).isEqualTo("갃") }
    }

    @Test
    fun disassembleTest() {
        builder.decompose("가나다라").also { assertThat(it).isEqualTo("ㄹㅏ") }

        builder.decompose("가").also { assertThat(it).isEqualTo("ㄱㅏ") }

        builder.decompose("가나다라", isSeparateAll = true).also {
            assertThat(it).isEqualTo("ㄱㅏㄴㅏㄷㅏㄹㅏ")
        }

        builder.decompose("가나다라", ",", true).also {
            assertThat(it).isEqualTo("ㄱ,ㅏ,ㄴ,ㅏ,ㄷ,ㅏ,ㄹ,ㅏ")
        }

        builder.decompose(" ").also { assertThat(it).isEqualTo(" ") }

        builder.decompose("가나다ABC").also { assertThat(it).isEqualTo("C") }

        builder.decompose("가나다ABC나다라").also { assertThat(it).isEqualTo("ㄹㅏ") }
    }

    @Test
    fun addTest() {
        builder.clear()
        builder.add("가나다라", "ㄱ").also { assertThat(it).isEqualTo("가나다락") }

        builder.clear()
        builder.add("ㄱ").also { assertThat(it).isEqualTo("ㄱ") }

        builder.clear()
        builder.add("ㄱ", "ㅏ").also { assertThat(it).isEqualTo("가") }

        builder.clear()
        builder.add("ㅗ", "ㅐ").also { assertThat(it).isEqualTo("ㅙ") }

        builder.clear()
        builder.add("ㅐ", "ㅗ").also { assertThat(it).isEqualTo("ㅐㅗ") }

        builder.clear()
        builder.add("ㄱ", "ㄱ", "ㄱ", "ㅗ").also { assertThat(it).isEqualTo("ㄱㄱ고") }

        builder.clear()
        builder.add("갉", "ㅏ").also { assertThat(it).isEqualTo("갈가") }

        builder.clear()
        builder.add("갈", "ㅏ").also { assertThat(it).isEqualTo("가라") }

        builder.clear()
        builder.add("갈", "ㄱ").also { assertThat(it).isEqualTo("갉") }

        builder.clear()
        builder.add("고", "ㅐ").also { assertThat(it).isEqualTo("괘") }

        builder.clear()
        builder.add("고", "ㄱ").also { assertThat(it).isEqualTo("곡") }
    }

    @Test
    fun removeTest() {
        builder.clear()
        builder.add("갉")
        builder.remove().also { assertThat(it).isEqualTo("갈") }

        builder.clear()
        builder.add("갈")
        builder.remove().also { assertThat(it).isEqualTo("가") }

        builder.clear()
        builder.add("쇄")
        builder.remove().also { assertThat(it).isEqualTo("소") }

        builder.clear()
        builder.add("갈가")
        builder.remove().also { assertThat(it).isEqualTo("갉") }

        builder.clear()
        builder.add("ㅙ")
        builder.remove().also { assertThat(it).isEqualTo("ㅗ") }
    }
}