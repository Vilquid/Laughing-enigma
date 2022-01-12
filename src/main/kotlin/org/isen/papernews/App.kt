package org.isen.papernews

import org.isen.papernews.controller.PaperNewsDefaultController
import org.isen.papernews.model.IPaperNewsModel
import org.isen.papernews.model.implementation.PaperNewsModel
import org.isen.papernews.view.IPaperNewsView
import org.isen.papernews.view.MainView


class App {}

fun main(args: Array<String>)
{
	val newsModel: IPaperNewsModel = PaperNewsModel();
	val newsController: PaperNewsDefaultController = PaperNewsDefaultController(newsModel)
	val newsView: IPaperNewsView = MainView(newsController, " Projet Paper News CIN 3 - Mathis de Gueydon")

	newsController.displayAllView()
}
