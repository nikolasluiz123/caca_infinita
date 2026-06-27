package br.com.schmittsolucoes.cacasobmedida.data.service.exceptions

sealed class AIModelServiceException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    class EngineNotInitialized : AIModelServiceException("Engine not initialized. Call initialize() first.")
    class ModelFileNotFound(path: String) : AIModelServiceException("Model file not found at: $path")
    class InferenceFailed(cause: Throwable) : AIModelServiceException("Model inference failed.", cause)
    class InitializationFailed(cause: Throwable) : AIModelServiceException("Failed to initialize the model engine.", cause)
}
