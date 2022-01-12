package org.isen.papernews.controller

import org.isen.papernews.model.IPaperNewsModel
import org.isen.papernews.view.IPaperNewsView


class PaperNewsDefaultController  (var model : IPaperNewsModel)
{
//	Déclarations - initialisation des vues
	private var views = ArrayList<IPaperNewsView>()
	init { this.views = ArrayList() }

//	Faire appel à la fonction de récupération d'articles
	fun findNews(keywords : String, categorie: String, pays: String)
	{
		this.model.getNewsFor(keywords, categorie, pays)
	}

	fun displayAllView()
	{
		views.forEach {
			it.display()
		}
	}

	fun registerView(view: IPaperNewsView)
	{
		this.views.add(view)
		this.model.register(view)
	}
}

//	fun closeAllView()
//	{
//		views.forEach {
//			it.close()
//		}
//	}