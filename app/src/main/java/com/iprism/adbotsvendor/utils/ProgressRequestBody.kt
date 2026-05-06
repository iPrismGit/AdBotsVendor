package com.iprism.adbotsvendor.utils

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.buffer
import java.io.IOException

class ProgressRequestBody(
    private val delegate: RequestBody,
    private val onProgress: (progress: Int) -> Unit
) : RequestBody() {

    override fun contentType(): MediaType? = delegate.contentType()

    override fun contentLength(): Long {
        return try {
            delegate.contentLength()
        } catch (_: IOException) {
            -1
        }
    }

    override fun writeTo(sink: BufferedSink) {
        val totalBytes = contentLength()
        var uploadedBytes = 0L

        val countingSink = object : okio.ForwardingSink(sink) {
            override fun write(source: okio.Buffer, byteCount: Long) {
                super.write(source, byteCount)
                uploadedBytes += byteCount
                if (totalBytes > 0) {
                    val progress = ((uploadedBytes.toDouble() / totalBytes.toDouble()) * 100).toInt()
                    onProgress(progress)
                }
            }
        }

        val bufferedSink = countingSink.buffer()
        delegate.writeTo(bufferedSink)
        bufferedSink.flush()
    }
}
