package org.isen.papernews.controller

import org.isen.papernews.model.IPaperNewsModel
import org.isen.papernews.view.IPaperNewsView


class PaperNewsDefaultController  (var model : IPaperNewsModel)
{
	// Déclarations - initialisation des vues
	private var views = ArrayList<IPaperNewsView>()
	init { this.views = ArrayList() }

	// Faire appel à la fonction de récupération d'articles
	fun findNews(keywords : String, category: String, country : String)
	{
		this.model.getNewsFor(keywords,category,country)
	}

	fun displayAllView()
	{
		views.forEach {
			it.display()
		}
	}

	fun closeAllView()
	{
		views.forEach {
			it.close()
		}
	}

	fun registerView(v: IPaperNewsView)
	{
		this.views.add(v)
		this.model.register(v)
	}
}