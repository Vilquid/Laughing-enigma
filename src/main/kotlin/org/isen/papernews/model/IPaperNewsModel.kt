package org.isen.papernews.model

import org.isen.papernews.model.data.SearchData
import java.text.DateFormat

interface IPaperNewsModel {

    fun register(listener:IPaperNewsModelObservable)

    fun unregister(listener: IPaperNewsModelObservable)

    fun getNewsFor(keywords:String?, category:String?, country:String?)
}