package com.gangwonhyuil.gangwonhyuil.data.remote.ai

import com.gangwonhyuil.gangwonhyuil.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel

class AiService {
    val generativeModel = GenerativeModel(
        modelName = "gemini-1.0-pro",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    suspend fun sendMessage(message: String): String {
        return generativeModel.generateContent(message).text.toString()
    }
}