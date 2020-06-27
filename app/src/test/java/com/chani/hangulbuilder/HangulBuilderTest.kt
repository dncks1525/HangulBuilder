package com.chani.hangulbuilder

import org.junit.Test
import com.google.common.truth.Truth.*

class HangulBuilderTest {
    val builder = HangulBuilder

    @Test
    fun assembleTest() {
        with(builder.assemble('ㄱ', 'ㅏ')) {
            assertThat(this).isEqualTo("가")
        }

        with(builder.assemble('ㄱ', 'ㅏ', 'ㄳ')) {
            assertThat(this).isEqualTo("갃")
        }
    }

    @Test
    fun disassembleTest() {
        with(builder.disassemble("가나다라")) {
            assertThat(this).isEqualTo("ㄹㅏ")
        }

        with(builder.disassemble("가")) {
            assertThat(this).isEqualTo("ㄱㅏ")
        }

        with(builder.disassemble("가나다라", true)) {
            assertThat(this).isEqualTo("ㄱㅏㄴㅏㄷㅏㄹㅏ")
        }

        with(builder.disassemble("가나다라", true, ",")) {
            assertThat(this).isEqualTo("ㄱ,ㅏ,ㄴ,ㅏ,ㄷ,ㅏ,ㄹ,ㅏ")
        }

        with(builder.disassemble(" ")) {
            assertThat(this).isEqualTo(" ")
        }

        with(builder.disassemble("가나다ABC")) {
            assertThat(this).isEqualTo("C")
        }
    }
}