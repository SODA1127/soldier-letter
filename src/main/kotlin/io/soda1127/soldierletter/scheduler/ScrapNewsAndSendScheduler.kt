package io.soda1127.soldierletter.scheduler

import io.soda1127.soldierletter.service.ClientService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import mu.NamedKLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*


@Component("ScrapNewsAndSendScheduler")
class ScrapNewsAndSendScheduler {

    companion object : NamedKLogging("ScrapNewsAndSendScheduler") {
        var NewsURL = "https://news.naver.com/main/list.nhn?mode=LSD&mid=sec&sid1="
    }

    @Autowired
    private lateinit var clientService: ClientService

    var dataReceived = false

    val categories = hashMapOf(
        "정치" to 100,
        "경제" to 101,
        "사회" to 102,
        //"생활문화" to 103,
        //"IT과학" to 105,
        "연예" to 106,
        "스포츠" to 107
    )

    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    fun runOn0() {
        dataReceived = false
    }

    @Scheduled(cron = "* * 6 * * ?", zone = "Asia/Seoul")
    fun runOn6() {
        logger.info { "로그 테스트" }
        runScrap()
    }

    @Scheduled(cron = "* * 9 * * ?", zone = "Asia/Seoul")
    fun runOn9() {
        dataReceived = false
    }

    @Scheduled(cron = "* * 12 * * ?", zone = "Asia/Seoul")
    fun runOn12() {
        logger.info { "로그 테스트" }
        runScrap()
    }

    @Scheduled(cron = "* 00 2 ? * *", zone = "Asia/Seoul")
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
                val titleContent = titleContentList.reduce { acc, s -> "$acc</br>$s" }.trimIndent()
                runSend(titleContent)
            }
        }
   }

    private suspend fun runSend(titleContent: String) {
        try {
            with(clientService) {
                login("dlrlwjd1127@naver.com", "@Dlrlwjd1148")
                fetchSoldiers(
                    io.soda1127.soldierletter.model.Soldier(
                        missSoldierClassCdNm = io.soda1127.soldierletter.model.SoldierClass.예비군인_훈련병,
                        grpCdNm = io.soda1127.soldierletter.model.SoldierGroup.육군,
                        traineeNm = "김지환",
                        birth = "19930729",
                        regDate = "20201112",
                        soldierUnitCdNm = io.soda1127.soldierletter.model.SoldierUnit.육군훈련소
                    )
                )
                val format = SimpleDateFormat("MM월 dd일")
                val calendar = Calendar.getInstance()
                val formatTime: String = format.format(calendar.time)
                logger.info { "$formatTime 최신 소식</br>$titleContent" }
                val isSended = sendMessage("23연대 1중대 149번 김지환", "$formatTime 최신 소식</br>$titleContent")
                logger.info {
                    if (isSended) "성공적으로 보내짐" else "성공적으로 못보냄"
                }
            }
        } catch (e: Exception) {
            dataReceived = false
            runSend(titleContent)
        }
    }

    private fun parseTitleContent(key: String, value: Int, document: Document): String {
        val body = document.body()
        val section = body.getElementsByClass("type06_headline")[0]
        val sectionTextList = section.getElementsByTag("li").subList(0, 5).map { elem ->
            val aTag = elem.getElementsByTag("a")
            val aLink = aTag.attr("href")
            val newsLinkDocument = Jsoup.connect(aLink).get()
            val newsContent = parseContent(newsLinkDocument, value)
            """
#${aTag.text()}</br>
${if (newsContent.isNotEmpty()) "@ : $newsContent" else ""}
""".trimIndent()
        }
        return """
[$key]
${sectionTextList.reduce { acc, s -> "$acc</br>$s" }}
"""
    }

    // 106 articeBody
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