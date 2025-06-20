package com.doublesymmetry.kotlinaudio.players

import android.net.Uri
import com.google.android.exoplayer2.extractor.Extractor
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.ext.ffmpeg.FfmpegExtractor
import com.google.android.exoplayer2.ext.ffmpeg.FfmpegLibrary
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import timber.log.Timber
import java.util.Collections

class CustomExtractorsFactory : ExtractorsFactory {
    private val defaultExtractorsFactory = DefaultExtractorsFactory().apply {
        setConstantBitrateSeekingEnabled(true)
        setConstantBitrateSeekingAlwaysEnabled(true)
    }

    override fun createExtractors(): Array<Extractor> {
        return createExtractors(Uri.EMPTY, emptyMap())
    }

    override fun createExtractors(
        uri: Uri,
        responseHeaders: Map<String, List<String>>
    ): Array<Extractor> {
        val extractors = ArrayList<Extractor>()

        try {
            if (FfmpegLibrary.isAvailable()) {
              extractors.add(FfmpegExtractor())
            } else {
                Timber.w("FfmpegLibrary not available, FfmpegExtractor not added.")
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to add FfmpegExtractor to CustomExtractorsFactory")
        }

        Collections.addAll(extractors, *defaultExtractorsFactory.createExtractors(uri, responseHeaders))
        
        return extractors.toTypedArray()
    }
}
