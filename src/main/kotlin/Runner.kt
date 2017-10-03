import com.automation.remarks.kirk.Browser
import org.openqa.selenium.chrome.ChromeDriver

fun main(args: Array<String>) {
    val chromeDriverLocation = getEnvVarValue("chrome_driver_path")
    System.setProperty("webdriver.chrome.driver", chromeDriverLocation)
    val browser = Browser(ChromeDriver())
    browser.open("https://www.ov-chipkaart.nl/my-ov-chip.htm")

    val username = getEnvVarValue("ov_username")
    val password = getEnvVarValue("ov_password")

    // login into personal account
    browser.element("input[id=\"username\"]").setValue(username)
    browser.element("input[id=\"password\"]").setValue(password)
    browser.element("input[id=\"btn-login\"]").click()

    // open travel history
    browser.open("https://www.ov-chipkaart.nl/my-ov-chip/my-travel-history.htm")
    // select your personal card
    browser.element("form[id=\"cardselector_form\"] ol li:first-child").click()
    // click calendar icon
    browser.scrollTo(browser.element("div[class=\"transaction-filter\"] h2"))
    browser.element("div[class=\"dateFilterRadioOption\"] i[class=\"fa fa-calendar glyphicon glyphicon-calendar\"]").click()
    // click on the latest month
    browser.element("div[class=\"ranges\"] ul li:first-child").click()


    // start operations sequence for some page
    try {
        do {
            val transactions = browser.all("table[class=\"table transaction-overview-table show-declaration\"] tbody tr[class=\"known-transaction\"]")
            for (transaction in transactions) {
                val dateInfo = transaction.element("td:first-child")
                val fareInfo = transaction.element("td:nth-child(3)")
                if ((dateInfo.text.contains("Saturday")
                        || dateInfo.text.contains("Sunday")
                        || fareInfo.text.contains("€ 0.00"))
                        && fareInfo.text.contains("€")) {
                    transaction.element("td:last-child input").click()
                }
            }
            val nextButton = browser.element("button[label=\"Next page\"]")
            if (nextButton.isDisplayed) {
                nextButton.click()
            }
        } while (nextButton.isDisplayed)
    } catch (e: Exception) {
        // swallow the exception :(
        // So looks like it is the time to download report
    }
    browser.scrollTo(browser.element("div[class=\"transaction-filter\"] h2"))
    browser.element("input[id=\"selected-card\"]").click()
    browser.element("button[value=\"PDF\"]").click()
}

fun getEnvVarValue(envVarName: String): String {
    val value = if (System.getenv(envVarName).isNullOrEmpty()) "default" else System.getenv(envVarName)
    println("env var name: $envVarName and value: $value")
    return value
}