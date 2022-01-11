package org.isen.papernews.model.data

import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import kotlin.test.*


class SearchDataJUnitTest
{
    @Test
    fun recupData()
    {
        val (request, response, result) = "https://newsapi.org/v2/top-headlines?apiKey=6703d00fef054e638802d64457daf8a5&country=fr".httpGet().responseObject(SearchData.Deserializer())

        assertTrue(response.isSuccessful)

        result.component1().also { it ->
            assertNotNull(it).also { println(it) }
        }
            ?.let {
            val data: SearchData = it
            println(data)
        }
    }
}
