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

//	Clé API
	private val apiKey: String = "6703d00fef054e638802d64457daf8a5"

//	listeners
	private var listeners = mutableListOf<IPaperNewsModelObservable>()
	init { listeners = ArrayList() }

//	Notre variable Data qui va contenir les données récuperer grâce à la requête
	internal var newsData : SearchData by Delegates.observable(SearchData())
	{
			_, _, newValue -> logger.info("News data change") // oldValue à la place du 2ème "_"

		for (l in listeners)
		{
			l.updateNews(newValue)
			println(l.updateNews(newValue))
		}
	}

//	3 fonctions qui vont traiter les paramètres entrés par l'utilisateur sur l'IHM : qu'il ait entré quelque chose ou non
	private fun convertKeywords(keywords: String?) : String
	{
		return if (keywords == null) "" else "&q=$keywords"
	}

	private fun convertCategory(categorie: String?): String
	{
		return if (categorie == null) "" else "&category=$categorie"
	}

	private fun convertCountry(pays: String?): String
	{
		return if (pays==null) "&country=fr" else "&country=$pays"
	}

//	Récup de data
	private fun findNewsFor(keywords: String?, categorie: String, pays: String)
	{
		"https://newsapi.org/v2/top-headlines?apiKey=${apiKey}${convertCountry(pays)}${convertCategory(categorie)}${convertKeywords(keywords)}"
			.httpGet()
			.responseObject(SearchData.Deserializer()) { _, response, result -> logger.info("StatusCode = ${response.statusCode}")

				val(data, error) = result

				newsData = data?: SearchData().also { logger.warn("Pas de données") }

				if(response.statusCode == 429)
				{
					logger.info("Nombre maximal de requêtes vers l'API atteind")
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

//	Lancer la fonction de récupération en co-routines depuis une autre class
	override fun getNewsFor(keywords: String?, categorie: String?, pays:String?)
	{
		logger.info("findNews : $keywords")
		GlobalScope.launch {
			if (categorie != null)
			{
				if (pays != null)
				{
					findNewsFor(keywords, categorie, pays)
				}
			}
		}
	}
}
