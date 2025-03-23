import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLHeadingElement
import org.w3c.dom.HTMLParagraphElement

fun main() {
    val header = document.createElement("h1") as HTMLHeadingElement
    header.textContent = "Scream to Enter!"
    document.body?.appendChild(header)

    val text = document.createElement("p") as HTMLParagraphElement
    text.textContent = "Listening..."
    document.body?.appendChild(text)

    window.navigator.asDynamic().mediaDevices.getUserMedia(js("{ audio: true }")).then {
        val recognition = js("new (window.SpeechRecognition || window.webkitSpeechRecognition)()")
        recognition.continuous = true
        recognition.onresult = { event: dynamic ->
            val volume = event.results[0][0].confidence
            if (volume > 0.8) {
                text.textContent = "Welcome! You screamed loud enough."
            } else {
                text.textContent = "Not loud enough! Scream louder!"
            }
        }
        recognition.start()
    }
}
