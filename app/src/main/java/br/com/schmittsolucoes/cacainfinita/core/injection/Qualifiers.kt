package br.com.schmittsolucoes.cacainfinita.core.injection

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageProcessor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PDFProcessor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageLanguageProcessor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PDFLanguageProcessor
