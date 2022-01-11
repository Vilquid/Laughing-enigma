package org.isen.papernews.model.implementation

import org.apache.logging.log4j.kotlin.Logging
import org.apache.logging.log4j.kotlin.logger
import org.isen.papernews.model.IPaperNewsModelObservable
import org.isen.papernews.model.data.SearchData
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetNewsForJUnitTest {
    companion object : Logging

    @Test
    fun testGetNewsFor() {
        val model: PaperNewsModel = PaperNewsModel();


        println(model.getNewsFor("foot","","fr"))
    }

    @Test
    fun testObserverForNews() {
        var pass = false

        val myObserver = object : IPaperNewsModelObservable {
            override fun updateNews(data: Any) {
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

    @Test fun testGetNews(){
        var passObserver:Boolean = false;
        val model:PaperNewsModel = PaperNewsModel();

        val myObserver = object : IPaperNewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                logger.info("updatePresse with : $data")
                assertEquals(SearchData::class.java , data::class.java)
            }
        }
        model.register(myObserver)
        model.getNewsFor("foot","","fr")
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(passObserver,"after update Article property")
        assertEquals(2,model.newsData.articles.size)
    }
}
