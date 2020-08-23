package io.soda1127.soldierletter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import mu.NamedKLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NaverNewsScheduler {

    companion object : NamedKLogging("SnapShotScheduler") {
        var NewsURL = "https://news.naver.com/main/list.nhn?mode=LSD&mid=sec&sid1="
    }

    var dataReceived = false

    val categories = hashMapOf(
            "정치" to 100,
            "경제" to 101,
            "사회" to 102,
            "생활문화" to 103,
            "IT과학" to 105,
            "연예" to 106,
            "스포츠" to 107
    )

    @Scheduled(cron = "0 0 0 * * ?")
    fun runOn0() {
        dataReceived = false
    }

    @Scheduled(cron = "* * 6 * * ?")
    fun runOn6() {
        logger.info { "로그 테스트" }
        runScrap()
    }

    @Scheduled(cron = "* * 9 * * ?")
    fun runOn9() {
        dataReceived = false
    }

    @Scheduled(cron = "* * 12 * * ?")
    fun runOn12() {
        logger.info { "로그 테스트" }
        runScrap()
    }

    @Scheduled(cron = "* 59 00 ? * *", zone = "Asia/Seoul")
    fun run() {
        logger.info { "로그 테스트" }
        runScrap()
    }

    private fun runScrap() = runBlocking {
        withContext(Dispatchers.IO) {
            if (!dataReceived) {
                dataReceived = true
                val titleContentList = categories.entries.map {
                    val document = Jsoup.connect(NewsURL + it.value).get()
                    parseTitleContent(it.key, it.value, document)
                }
                val titleContent = titleContentList.reduce { acc, s -> "$acc\n$s" }.trimIndent()
                logger.info { titleContent }
            }
        }
    }

    private fun parseTitleContent(key: String, value: Int, document: Document): String {
        val body = document.body()
        val section = body.getElementsByClass("type06_headline")[0]
        val sectionTextList = section.getElementsByTag("li").subList(0, 3).map { elem ->
            val aTag = elem.getElementsByTag("a")
            val aLink = aTag.attr("href")
            val newsLinkDocument = Jsoup.connect(aLink).get()
            val newsContent = parseContent(newsLinkDocument, value)
            """
#${aTag.text()}
${if (newsContent.isNotEmpty()) "@ : $newsContent\n" else "\n"}
""".trimIndent()
        }
        return """
[$key]
${sectionTextList.reduce { acc, s -> "$acc\n$s" }}
"""
    }


    // 105 articeBody
    // 107 newsEndContents
    private fun parseContent(newLinkDocument: Document, value: Int): String {
        val findId = when (value) {
            106 -> "articeBody"
            107 -> "newsEndContents"
            else -> "articleBodyContents"
        }
        val articleBodyContents = newLinkDocument.getElementById(findId)
        val contentText = articleBodyContents?.text()
        contentText?.let {
            val text = if (contentText.contains("▶")) {
                articleBodyContents.text().split("▶")[0]
            } else {
                contentText
            }
            return if (text.length > 10) {
                return if (text.length >= 100) {
                    val shortText = text.substring(0, 100)
                    val textArray = shortText.split(".")
                    shortText.substring(0, shortText.length - textArray[textArray.size - 1].length)
                } else {
                    val textArray = text.split(".")
                    text.substring(0, text.length - textArray[textArray.size - 1].length)
                }
            } else ""
        }
        return contentText ?: ""
    }
}