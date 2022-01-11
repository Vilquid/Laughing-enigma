package org.isen.papernews.model.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class SearchData(val status : String = "", val totalResults : Int = 0, val articles : List<ArticleData> = listOf())
{
    //Deserializer à l'aide de l'api fuel pour traiter des données JSON
    class Deserializer : ResponseDeserializable<SearchData>
    {
        override fun deserialize(content: String): SearchData = Gson().fromJson(content,SearchData::class.java)
    }
}