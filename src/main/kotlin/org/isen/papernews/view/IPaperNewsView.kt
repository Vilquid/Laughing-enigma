package org.isen.papernews.view

import org.isen.papernews.model.IPaperNewsModelObservable


interface IPaperNewsView : IPaperNewsModelObservable
{
	fun display()

	fun close()

}