package step;
import com.codeborne.selenide.SelenideElement;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Selenide.$x;


public class ClavagonkiStep{
    private final SelenideElement closeWindowButton = $x("//input[@value='Закрыть']");
    private final SelenideElement startGameButton = $x("//a[@id='host_start']");
    private final SelenideElement hightLightWord = $x("//span[@id='typefocus']");
    private final SelenideElement inputField = $x("//input[@id='inputtext']");
    private final SelenideElement afterFocusWord = $x("//span[@id='afterfocus']");
    private final SelenideElement resultText =$x("//td[text() ='Это вы']//ancestor-or-self::div//div[@class='stats']//div[2]//span/span");


    public String getCurrentWord(){
         return hightLightWord.getText()
                 .replaceAll("с", "c")
                 .replaceAll("o", "о");
         // "replaceAll" с помощью этого метода меняет англ. буквы на русские
        //  в подсвеченном слове заменяем один символ на другой
    }

    @When("Начинаем игру")
    public void startGame() {
        closeWindowButton.click();
        if (startGameButton.isDisplayed()) {
            startGameButton.click();
            //  "isDisplayed" проверяет видимость элемента на странице
            //  если кнопка "начать игру" присутствует, то мы на нее нажимаем
            //  если кнопки нет, то мы игнорируем
        }
    }

    @And("Ждем начала игры")
    public void waitStartGame() {

        hightLightWord.click();
    }

    @And("Вводим подсвеченное слово")
    public void playGame() {
        while (true){
            String currentWord = getCurrentWord();
            //  получаем текст(текущий) "hightLightWord"-подсвеченный текст
            String afterFocusSymbol = afterFocusWord.getText();
            // получаем следующие слово
            inputField.sendKeys(currentWord);
            //  в строку для ввода, вводим текущие слово
            if (afterFocusSymbol.equals(".")){
                inputField.sendKeys(".");
                /**
                 * afterFocusSymbol  - это то, что идет дальше(после текущего слова)
                 * если после текущего слова будет "точка" -> afterFocusSymbol.equals(".")
                 * тогда мы кроме основного слова, еще ставим "точку" - > inputField.sendKeys(".")
                 * */
                break; // ставим брейк
            }
            inputField.sendKeys(Keys.SPACE);
            // если после текущего слова ничего нет, то ставим "Пробел", чтобы продолжить дальше

        }
    }

    @Then("Фиксируем что игра завершена и символов в минуту больше чем {int}")
    public void endGame (int minValue) {
        String result = resultText.getText();
        // получаем текст из результата в нашей игре(кол-во символовв мин)
        int resultNumber = Integer.parseInt(result);
        // полученный текст из String result = resultText.getText(); преобразуем в
        // в число с помощью Integer.parseInt(result);
        System.out.println("Количество символов в минуту" + resultNumber );
       Assertions.assertTrue
               (resultNumber > minValue, "Актуальный результат был:" + resultNumber);
        // полученный результат "resultNumber" больше, чем "minValue"
        // "minValue" это наше значение из сценария





    }
}