package com.syllogismobile.sample_android.backend.converters

import com.squareup.moshi.Moshi
import com.syllogismobile.sample_android.backend.model.Comic
import com.syllogismobile.sample_android.backend.model.ComicJsonAdapter

class StringToComicConverter(private val moshi: Moshi): Converter<String, Comic> {
    private val adapter = ComicJsonAdapter(moshi)

    override fun convert(input: String): Comic? {
        return adapter.fromJson(input)
    }
}