package org.isen.papernews.model.implementation

import org.apache.logging.log4j.kotlin.Logging
import org.isen.papernews.model.IPaperNewsModelObservable
import org.isen.papernews.model.data.SearchData
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class GetNewsForJUnitTest
{
	companion object : Logging

	@Test
	fun testGetNewsFor()
	{
		val model = PaperNewsModel()

		println(model.getNewsFor("macron","general","fr"))
	}

	@Test
	fun testObserverForNews()
	{
		var pass = false

		val myObserver = object : IPaperNewsModelObservable
		{
			override fun updateNews(data: Any)
			{
				logger.info("updateNews")
				pass = true
				assertTrue(data is SearchData)
			}
		}

		val model = PaperNewsModel()
		model.register(myObserver)
		model.newsData = SearchData()

		assertTrue(pass)
	}

	@Test
	fun testGetNews()
	{
		var passObserver = false
		val model = PaperNewsModel()

		val myObserver = object : IPaperNewsModelObservable
		{
			override fun updateNews(data: Any)
			{
				passObserver = true
				logger.info("updatePresse with : $data")
				assertEquals(SearchData::class.java , data::class.java)
			}
		}

		model.register(myObserver)
		model.getNewsFor("macron","general","fr")
		logger.info("Attente ...")
		Thread.sleep(10000)

		assertTrue(passObserver,"Après mise à jour de la propriété Article")
		assertEquals(2,model.newsData.articles.size)
	}
}
