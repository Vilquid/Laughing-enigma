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


class PaperNewsModel : IPaperNewsModel
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
		_, _, nouvelle_valeur -> logger.info("Changements des données") // oldValue à la place du 2ème "_"

		for (l in listeners)
		{
			l.updateNews(nouvelle_valeur)
			println(l.updateNews(nouvelle_valeur))
		}
	}

//	3 fonctions qui vont traiter les paramètres entrés par l'utilisateur sur l'IHM : qu'il ait entré quelque chose ou non
//	Si l'utilisateur ne rentre rien, alors il obtiendra des articles de foot en français
	private fun retourner_keywords(keywords: String?) : String
	{
		if (keywords == null)
		{
			return "foot"
		}

		else
		{
			return "&q=$keywords"
		}
	}

	private fun retourner_categorie(categorie: String?) : String
	{
		if (categorie == null)
		{
			return "sports"
		}

		else
		{
			return "&category=$categorie"
		}
	}

	private fun retourner_pays(pays: String?) : String
	{
		if (pays==null)
		{
			return "&country=fr"
		}

		else
		{
			return "&country=$pays"
		}
	}

//	Récup de data
	private fun findNewsFor(keywords: String?, categorie: String, pays: String)
	{
		"https://newsapi.org/v2/top-headlines?apiKey=${apiKey}${retourner_pays(pays)}${retourner_categorie(categorie)}${retourner_keywords(keywords)}"
			.httpGet()
			.responseObject(SearchData.Deserializer()) { _, reponse, resultat -> logger.info("StatusCode = ${reponse.statusCode}")

				val(data, _) = resultat

				newsData = data?: SearchData().also { logger.warn("Pas de données") }

				if(reponse.statusCode == 429)
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
	override fun getNewsFor(keywords: String?, categorie: String?, pays: String?)
	{
		logger.info("findNews : $keywords")
		GlobalScope.launch {
			if (categorie != null && pays != null)
			{
				findNewsFor(keywords, categorie, pays)
			}
		}
	}
}
