package org.isen.papernews.view

import org.isen.papernews.model.IPaperNewsModelObservable

interface IPaperNewsView : IPaperNewsModelObservable{

    abstract fun display()

    abstract fun close()
}