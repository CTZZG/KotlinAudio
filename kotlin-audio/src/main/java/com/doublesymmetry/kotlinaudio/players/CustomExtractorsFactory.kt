package com.doublesymmetry.kotlinaudio.players

import android.net.Uri
import com.google.android.exoplayer2.extractor.Extractor
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.ext.ffmpeg.FfmpegExtractor
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import java.util.Collections

class CustomExtractorsFactory : ExtractorsFactory {
    private val defaultExtractorsFactory = DefaultExtractorsFactory()

    override fun createExtractors(): Array<Extractor> {
        return createExtractors(Uri.EMPTY, emptyMap())
    }

    override fun createExtractors(
        uri: Uri,
        responseHeaders: Map<String, List<String>>
    ): Array<Extractor> {
        val extractors = ArrayList<Extractor>()

        try {
            if (com.google.android.exoplayer2.ext.ffmpeg.FfmpegLibrary.isAvailable()) {
              extractors.add(FfmpegExtractor())
            }
        } catch (e: Exception) {
            android.util.Log.e("CustomExtractorsFactory", "Failed to add FfmpegExtractor", e)
        }

        Collections.addAll(extractors, *defaultExtractorsFactory.createExtractors(uri, responseHeaders))
        
        return extractors.toTypedArray()
    }
}
