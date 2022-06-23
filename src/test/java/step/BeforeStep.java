package step;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.en.Given;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.asynchttpclient.util.Assertions;


public class BeforeStep {
    @Given("Открываем сайт {string}")

    public void openWebSite(String get) {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920х1080";
        Configuration.timeout = 6000;
        Selenide.open(get);


        // если элемента нет, то ждем 60 сек, если он не появляется, то тест подает
    }

    }




