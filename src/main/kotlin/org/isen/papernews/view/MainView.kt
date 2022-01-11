package org.isen.papernews.view

import org.apache.logging.log4j.kotlin.Logging
import org.isen.papernews.controller.PaperNewsDefaultController
import org.isen.papernews.model.data.ArticleData
import org.isen.papernews.model.data.SearchData
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.net.URL
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.JComboBox

class MainView : IPaperNewsView, ActionListener {

    companion object : Logging

    // Listes pour JCombobox
    private val lcategory : Array<String>
    private val lcountry : Array<String>

    // Frame principale et controller
    private val frame: JFrame
    private val controller: PaperNewsDefaultController

    // Entrées utilisateur
    private val fieldKeyword : JTextField
    private val listCategory: JComboBox<String>
    private val listCountry: JComboBox<String>

    // Labels pour accueillir les données des articles
    private val labelTitle : JLabel
    private val labelAuthor : JLabel
    private val labelDescription : JLabel

    // Panel infos
    private val articleInfo : JPanel

    constructor(ctrl:PaperNewsDefaultController, title : String) {

        this.fieldKeyword = JTextField()

        // données pour listes déroulantes category et country
        this.lcategory = arrayOf("general","business","entertainment","health","science","sports","technology")
        this.lcountry = arrayOf("fr","us","ae","ar","at","au","be","bg","br","ca","ch","cn","co","cu","cz","de","eg","gb","gr","hk","hu","id","ie","il","in","it","jp","kr","lt","lv","ma","mx","my","ng","nl","no","nz","ph","pl","pt","ro","rs","ru","sa","se","sg","si","sk","th","tr","tw","ua","ve","za")

        this.listCategory = JComboBox(lcategory)
        this.listCountry =  JComboBox(lcountry)

        this.labelTitle = JLabel()
        this.labelAuthor = JLabel()
        this.labelDescription = JLabel()

        this.articleInfo = JPanel(BorderLayout())

        // Initialisation Fenêtre avec paramètres prédéfinis
        this.frame = JFrame()
        this.frame.contentPane = makeUI()
        this.frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        this.frame.preferredSize = Dimension(1200,900)
        this.frame.title = title

        // Initialisation
        this.controller = ctrl
        this.controller.registerView(this)
        this.frame.pack()
    }


    //-----------------FONCTIONS GESTION DES PANELS-----------------//

    // Panel maitre de l'interface

    private fun makeUI() : JPanel {
        val contentPane = JPanel()

        contentPane.layout = BorderLayout()
        contentPane.add(createUIforEnterElements(), BorderLayout.NORTH)
        contentPane.add(createUI_ArticleContent(),BorderLayout.CENTER)

        return contentPane
    }

    // Panel gestion des entrées utilisateurs

    private fun createUIforEnterElements() : JPanel {

        val panelSearchBar = JPanel()
        val panelDataEnter = JPanel()
        val panelButton = JPanel()
        panelDataEnter.layout = GridLayout(2,5)
        panelSearchBar.layout = BorderLayout()
        panelButton.layout = GridLayout(1,7)

        panelDataEnter.add(JLabel("Enter keyword :"))
        panelDataEnter.add(JLabel(""))
        panelDataEnter.add(JLabel("Enter category :"))
        panelDataEnter.add(JLabel(""))
        panelDataEnter.add(JLabel("Enter country :"))
        panelDataEnter.add(fieldKeyword)
        panelDataEnter.add(JLabel(""))
        panelDataEnter.add(listCategory)
        panelDataEnter.add(JLabel(""))
        panelDataEnter.add(listCountry)

        panelSearchBar.add(panelDataEnter,BorderLayout.CENTER)

        val button = JButton("Search")
        button.addActionListener(this)

        panelButton.add(JLabel(""))
        panelButton.add(JLabel(""))
        panelButton.add(JLabel(""))
        panelButton.add(button)
        panelButton.add(JLabel(""))
        panelButton.add(JLabel(""))
        panelButton.add(JLabel(""))

        panelSearchBar.add(panelButton,BorderLayout.SOUTH)

        panelSearchBar.add(JLabel(" "), BorderLayout.NORTH)

        return panelSearchBar
    }

    // Panel gestion affichage des données reçues

    private fun createUI_aritcle(data: ArticleData): JPanel
    {
        val articlePanel = JPanel(BorderLayout())
        articlePanel.preferredSize = Dimension(frame.width,100)

        // Creation de la zone du panel dediee a l'image de l'article et délimitation des articles
        val imageURL = URL("${data.urlToImage}")
        val imageLabel = JLabel(ImageIcon(ImageIO.read(imageURL)))
        articlePanel.setBorder(BorderFactory.createMatteBorder(2,0,2,0, Color.black))
        imageLabel.preferredSize = Dimension(200,200)
        articlePanel.add(imageLabel,BorderLayout.WEST)

        // Creation de la zone du panel dédiée aux infos de l'article
        val gridArticleInfo = JPanel(GridLayout(4,1))

        // Gestion de l'affichage des données sur le panel
        gridArticleInfo.add(JLabel("    Title : ${data.title}"))
        if (data.author == null) {
            gridArticleInfo.add(JLabel("    Author : Unknown"))
        } else {
            gridArticleInfo.add(JLabel("    Author : ${data.author}"))

        }
        gridArticleInfo.add(JLabel("    Description : ${data.description}"))


        // Création d'un bouton permettant de visualiser l'article dans un navigateur
        val urlButton = JButton("Open in WebBrowser")
        urlButton.addActionListener {
            val runtime = Runtime.getRuntime()
            val urlArticle = "${data.url}"
            runtime.exec("rundll32 url.dll,FileProtocolHandler $urlArticle")
        }

        articlePanel.add(urlButton, BorderLayout.EAST)
        articlePanel.add(gridArticleInfo, BorderLayout.CENTER)
        return articlePanel
    }

    // Panel Informations

    private fun createUI_ArticleContent() : JPanel {
        return articleInfo
    }


    // Getter pour listes déroulantes

    fun getLCategory(): JComboBox<String> {
        return listCategory
    }

    fun getLCountry(): JComboBox<String> {
        return listCountry
    }




    //-----------------OVERRIDE-----------------//

    override fun display() {
        this.frame.isVisible = true
    }

    override fun close() {
        this.frame.isVisible = false
    }

    // Fonction mise à jour des données après retour de la requete

    private fun updateArticle(data: SearchData)
    {
        articleInfo.removeAll()
        val panelForAllArticle = JPanel(GridLayout(data.articles.size,1))
        val scrollPanelOfArticles = JScrollPane(panelForAllArticle)

        for (item: ArticleData in data.articles)
        {
            logger.debug("New articles: $item")
            panelForAllArticle.add(createUI_aritcle(item))

        }

        articleInfo.add(scrollPanelOfArticles, BorderLayout.EAST)
        articleInfo.revalidate()
    }

    //Fonction action du bouton search, lançant la requête vers le controller

    override fun actionPerformed(p0: ActionEvent?) {
        controller.findNews(fieldKeyword.text, this.getLCategory().selectedItem as String,
            this.getLCountry().selectedItem as String
        )
    }

    // Fonction vérification du type de données avant la mise à jour des données

    override fun updateNews(data: Any)
    {
        if (data is SearchData)
        {
            updateArticle(data)
            logger.info("articles : $data")
        }
    }
}