package com.emergency.button.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.*

/**
 * Manager para manejar la funcionalidad de Text-to-Speech
 * Permite leer mensajes de emergencia durante las llamadas
 */
class TTSManager(private val context: Context) : TextToSpeech.OnInitListener {
    
    private var textToSpeech: TextToSpeech? = null
    private var isInitialized = false
    private var isSpeaking = false
    
    companion object {
        private const val TAG = "TTSManager"
        private const val UTTERANCE_ID = "emergency_message"
    }
    
    /**
     * Inicializa el motor de Text-to-Speech
     */
    fun initialize() {
        if (textToSpeech == null) {
            textToSpeech = TextToSpeech(context, this)
        }
    }
    
    /**
     * Callback cuando TTS se inicializa
     */
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale.getDefault())
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "Language not supported")
            } else {
                isInitialized = true
                Log.d(TAG, "TTS initialized successfully")
                
                // Configurar listener para monitorear el progreso
                @Suppress("DEPRECATION")
                textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        Log.d(TAG, "TTS started speaking")
                        isSpeaking = true
                    }
                    
                    override fun onDone(utteranceId: String?) {
                        Log.d(TAG, "TTS finished speaking")
                        isSpeaking = false
                    }
                    
                    override fun onError(utteranceId: String?) {
                        Log.e(TAG, "TTS error occurred")
                        isSpeaking = false
                    }
                })
            }
        } else {
            Log.e(TAG, "TTS initialization failed")
        }
    }
    
    /**
     * Lee un mensaje de emergencia
     * @param message El mensaje a leer
     * @param callback Callback para notificar cuando termine de hablar
     */
    fun speakEmergencyMessage(message: String, callback: (() -> Unit)? = null) {
        if (!isInitialized) {
            Log.w(TAG, "TTS not initialized, cannot speak")
            callback?.invoke()
            return
        }
        
        if (isSpeaking) {
            Log.w(TAG, "TTS is already speaking, stopping current speech")
            stopSpeaking()
        }
        
        // Configurar parámetros de voz
        textToSpeech?.setSpeechRate(0.8f) // Velocidad ligeramente más lenta para emergencias
        textToSpeech?.setPitch(1.0f) // Tono normal
        
        // Crear bundle con parámetros
        val params = android.os.Bundle()
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, UTTERANCE_ID)
        
        // Configurar callback para cuando termine de hablar
        if (callback != null) {
            @Suppress("DEPRECATION")
            textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    Log.d(TAG, "TTS started speaking emergency message")
                    isSpeaking = true
                }
                
                override fun onDone(utteranceId: String?) {
                    Log.d(TAG, "TTS finished speaking emergency message")
                    isSpeaking = false
                    callback.invoke()
                }
                
                override fun onError(utteranceId: String?) {
                    Log.e(TAG, "TTS error while speaking emergency message")
                    isSpeaking = false
                    callback.invoke()
                }
            })
        }
        
        // Leer el mensaje
        val result = textToSpeech?.speak(message, TextToSpeech.QUEUE_FLUSH, params, UTTERANCE_ID)
        if (result == TextToSpeech.ERROR) {
            Log.e(TAG, "Failed to speak emergency message")
            callback?.invoke()
        }
    }
    
    /**
     * Detiene el habla actual
     */
    fun stopSpeaking() {
        textToSpeech?.stop()
        isSpeaking = false
        Log.d(TAG, "TTS stopped speaking")
    }
    
    /**
     * Verifica si TTS está hablando
     */
    fun isCurrentlySpeaking(): Boolean {
        return isSpeaking
    }
    
    /**
     * Verifica si TTS está inicializado
     */
    fun isReady(): Boolean {
        return isInitialized
    }
    
    /**
     * Libera los recursos de TTS
     */
    fun shutdown() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
        isInitialized = false
        isSpeaking = false
        Log.d(TAG, "TTS shutdown")
    }
}
