package org.isen.papernews.model.implementation

import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.logging.log4j.kotlin.Logging
import org.isen.papernews.model.IPaperNewsModel
import org.isen.papernews.model.IPaperNewsModelObservable
import org.isen.papernews.model.data.SearchData
import kotlin.properties.Delegates
import kotlin.system.exitProcess


class PaperNewsModel: IPaperNewsModel
{
	companion object : Logging

	// Clé API
	private val apiKey: String = "6703d00fef054e638802d64457daf8a5"

	// listeners
	private var listeners = mutableListOf<IPaperNewsModelObservable>()
	init { listeners = ArrayList() }

	// Notre variable Data qui va contenir les données récuperer grâce à la requête
	internal var newsData : SearchData by Delegates.observable(SearchData())
	{
		_, oldValue, newValue -> logger.info("News data change")

		for (l in listeners)
		{
			l.updateNews(newValue)
			println(l.updateNews(newValue))
		}
	}

	// 3 fonctions qui vont traiter les paramètres entrés par l'utilisateur sur l'IHM : qu'il ait entré quelque chose ou non
	private fun convertKeywords(keywords: String?): String
	{
		return if (keywords == null) "" else "&q=$keywords"
	}

	private fun convertCategory(category: String?): String
	{
		return if (category == null) "" else "&category=$category"
	}

	private fun convertCountry(country: String?): String
	{
		return if (country==null) "&country=fr" else "&country=$country"
	}

	// Récup de data
	private fun findNewsFor(keywords:String?, category:String?, country:String?)
	{
		"https://newsapi.org/v2/top-headlines?apiKey=${apiKey}${convertCountry(country)}${convertCategory(category)}${convertKeywords(keywords)}"
			.httpGet()
			.responseObject(SearchData.Deserializer()){_,response,result -> logger.info("StatusCode = ${response.statusCode}")

				val(data,err) = result
				newsData = data ?: SearchData().also { logger.warn("data is void") }

				if(response.statusCode == 429)
				{
					logger.info("Limit API request reached")
					exitProcess(0)
				}
			}
	}

	override fun register(listener: IPaperNewsModelObservable)
	{
		listeners.add(listener)
	}

	override fun unregister(listener: IPaperNewsModelObservable)
	{
		listeners.remove(listener)
	}

	// Lancer la fonction de récupération en co-routines depuis une autre class
	override fun getNewsFor(keywords:String?, category:String?, country:String?)
	{
		logger.info("findNews : $keywords")
		GlobalScope.launch { findNewsFor(keywords,category,country) }
	}
}
