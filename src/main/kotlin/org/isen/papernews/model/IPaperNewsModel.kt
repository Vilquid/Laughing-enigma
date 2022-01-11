package org.isen.papernews.model


interface IPaperNewsModel
{
    fun register(listener:IPaperNewsModelObservable)

    fun unregister(listener: IPaperNewsModelObservable)

    fun getNewsFor(keywords:String?, category:String?, country:String?)
}