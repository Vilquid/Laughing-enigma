package org.isen.papernews.controller

import org.isen.papernews.view.IPaperNewsView

class PaperNewsDefaultController {
	// déclarations et initialisation de nos vues
	private var views: var = ArrayList<IPaperNewsView>()
	fun init(: var?)
	{
		views = ArrayList()
	}

	// Fonction permettant de faire appel à la fonction de récupération d'articles
	fun findNews(): `fun` {
		this.model.getNewsFor(keywords, category, country)
	}

	fun displayAllView(): `fun` {
		views.forEach
		run { it.display() }
	}

	fun closeAllView(): `fun` {
		views.forEach
		run { it.close() }
	}

	fun registerView(): `fun` {
		views.add(v)
		this.model.register(v)
	}
}