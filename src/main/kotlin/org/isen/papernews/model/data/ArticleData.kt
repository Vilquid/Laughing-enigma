package org.isen.papernews.model.data

import java.util.*


data class ArticleData(val source: SourceData?, val author: String?, val title: String?, val description: String?, val url: String?, val urlToImage: String?, val publishedAt: Date?, val content: String?)
