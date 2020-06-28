HangulBuilder
=======
HangulBuilder is a library for creating Hangul Syllables.


How do I use it?
=======

__Java__
``` Java
HangulBuilder builder = HangulBuilder.INSTANCE;

builder.compose('ㅎ', 'ㅏ', 'ㄴ');     // -> 한
builder.compose('ㄱ', 'ㅡ', 'ㄹ');     // -> 글
builder.compose('ㄱ', 'ㅗ');           // -> 고
builder.compose('ㄹ', 'ㅐ');           // -> 래

builder.decompose("한글");             // -> 한ㄱㅡㄹ
builder.decompose("한글", ",", true);  // -> ㅎ,ㅏ,ㄴ,ㄱ,ㅡ,ㄹ

builder.clear();
builder.add("ㅎ");
builder.add("ㅏ");
builder.add("ㄴ");
builder.add("ㄱ");
builder.add("ㅡ");
builder.add("ㄹ");   // -> 한글

builder.clear();
builder.add("ㅎ", "ㅏ", "ㄴ", "ㄱ", "ㅡ", "ㄹ");  // -> 한글

builder.clear();
builder.add("한글");
builder.remove();   // -> 한그

```

__Kotlin__
```Kotlin
val builder = HangulBuilder;
builder.compose('ㅎ', 'ㅏ', 'ㄴ')     // -> 한
builder.compose('ㄱ', 'ㅡ', 'ㄹ')     // -> 글
builder.compose('ㄱ', 'ㅗ')           // -> 고
builder.compose('ㄹ', 'ㅐ')           // -> 래

builder.decompose("한글")             // -> 한ㄱㅡㄹ
builder.decompose("한글", ",", true)  // -> ㅎ,ㅏ,ㄴ,ㄱ,ㅡ,ㄹ

builder.clear()
builder.add("ㅎ")
builder.add("ㅏ")
builder.add("ㄴ")
builder.add("ㄱ")
builder.add("ㅡ")
builder.add("ㄹ")    // -> 한글

builder.clear()
builder.add("ㅎ", "ㅏ", "ㄴ", "ㄱ", "ㅡ", "ㄹ")   // -> 한글

builder.clear()
builder.add("한글")
builder.remove()    // -> 한그
```




License
=======
    Copyright 2020 dncks1525

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
