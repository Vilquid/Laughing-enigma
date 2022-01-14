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

		println(model.getNewsFor("foot","sports","fr"))
	}

	@Test
	fun testObserverForNews()
	{
		var OK = false

		val observer = object : IPaperNewsModelObservable
		{
			override fun updateNews(data: Any)
			{
				logger.info("Fonction updateNews")
				OK = true
				assertTrue(data is SearchData)
			}
		}

		val model = PaperNewsModel()

		model.register(observer)
		model.newsData = SearchData()

		assertTrue(OK)
	}

	@Test
	fun test_GetNews()
	{
		var OK_observer = false
		val model = PaperNewsModel()

		val observer = object : IPaperNewsModelObservable
		{
			override fun updateNews(data: Any)
			{
				OK_observer = true
				logger.info("updatePresse with : $data")
				assertEquals(SearchData::class.java , data::class.java)
			}
		}

		model.register(observer)
		model.getNewsFor("foot","sports","fr")
		logger.info("Attente ...")
		Thread.sleep(5000)

		assertTrue(OK_observer,"Après mise à jour de la propriété Article")
		assertEquals(2, model.newsData.articles.size)
	}
}
