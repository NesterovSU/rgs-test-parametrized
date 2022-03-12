package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Thread.sleep;

/**
 * @author Sergey Nesterov
 */
public class DMSPage {
    private final By
            FOR_COMPANIES = By.xpath("//a[@href='/for-companies']"),
            HEALTH = By.xpath("//span[text()='Здоровье' and @class='padding']"),
            TRANSPORT = By.xpath("//span[text()='Транспорт/Перевозки' and @class='padding']"),
            DMS = By.xpath("//a[text()='Добровольное медицинское страхование']"),
            TITLE = By.xpath("//h2[contains(text(),'Оперативно перезвоним')]"),
            COOKIE_BUTTON = By.xpath("//div[@class = 'cookie block--cookie']/button[contains(text(),'Хорошо')]"),
            USER_NAME = By.xpath("//input[@name='userName']"),
            USER_TEL = By.xpath("//input[@name='userTel']"),
            USER_EMAIL = By.xpath("//input[@name='userEmail']"),
            USER_ADDRESS = By.xpath("//div[@field='InputRegion']//input"),
            USER_ADDRESS_MENU = By.xpath("//div[@field='InputRegion']//div[contains(@class,'suggestions')]"),
            POLICY_AGREEMENT_STATUS = By.xpath("//div[@class='policy-agreement text--basic']//input[@type='checkbox']"),
            POLICY_AGREEMENT_CLICK = By.xpath("//div[@class='policy-agreement text--basic']//input[@type='checkbox']/following::label[1]"),
            SUBMIT = By.xpath("//button[@type='submit' and contains(text(), 'Свяжитесь')]"),
            FRAME = By.xpath("//iframe[contains(@class, 'flocktory')]"),
            FRAME_CLOSE_BUTTON = By.xpath("//iframe[contains(@class, 'flocktory')]//button[@title='Закрыть']"),
            EMAIL_ERROR = By.xpath("//div[@formKey='email']//span[contains(text(),'Введите корректный адрес электронной почты') and contains(@class,'input__error')]");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public DMSPage(WebDriver driver){
        this.driver = driver;
        driver.get("http://www.rgs.ru");
        wait = new WebDriverWait(driver, 20);
    }

    public DMSPage typeUserName(String name){
        typeInForm(USER_NAME, name);
        return this;
    }
    public String getUserNameForm(){
        return driver.findElement(USER_NAME).getAttribute("value");
    }

    public DMSPage typeUserTelephone(String telephone){
        typeInForm(USER_TEL, telephone);
        return this;
    }
    public String getUserTelephoneForm(){
        return driver.findElement(USER_TEL).getAttribute("value")
                .replaceAll("[+()\\-\\s]", "")
                .replaceFirst("7", "");
    }

    public DMSPage typeUserEmail(String email){
        typeInForm(USER_EMAIL, email);
        return this;
    }
    public String getUserEmailForm() {
        return driver.findElement(USER_EMAIL).getAttribute("value");
    }

    public DMSPage typeUserAddress(String address){
        typeInForm(USER_ADDRESS, address);
        wait.until(ExpectedConditions.presenceOfElementLocated(USER_ADDRESS_MENU));
        try {
            sleep(1000);
        } catch (InterruptedException e) { e.printStackTrace(); }
        driver.findElement(USER_ADDRESS).sendKeys(Keys.DOWN);
        driver.findElement(USER_ADDRESS).sendKeys(Keys.ENTER);

        return this;
    }
    public String getUserAddressForm(){
        return driver.findElement(USER_ADDRESS).getAttribute("value");
    }

    public DMSPage agreePolicy(){
        checkRadio(POLICY_AGREEMENT_CLICK,POLICY_AGREEMENT_STATUS);
        return this;
    }
    public boolean getPolicyCheckBox(){
        return driver.findElement(POLICY_AGREEMENT_STATUS).isSelected();
    }

    public DMSPage clickForCompanies(){
        driver.findElement(FOR_COMPANIES).click();
        return this;
    }

    public DMSPage clickHealth(){
        driver.findElement(TRANSPORT);
        driver.findElement(HEALTH).click();
        return this;
    }

    public DMSPage clickDMS(){
        driver.findElement(DMS).click();
        return this;
    }


    public DMSPage submit() {
        tryClickBy(SUBMIT);
        return this;
    }

    public boolean isTitleOnPage(){
        try {
            wait.withMessage("Отсутствует заголовок на странице")
                    .until(ExpectedConditions.presenceOfElementLocated(TITLE));
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    public boolean isEmailError() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(EMAIL_ERROR)).isDisplayed();
            return true;
        }catch (Exception ex){
            return false;
        }
    }


    private void typeInForm(By by, String str) {
        tryClickBy(by);
        WebElement we = driver.findElement(by);
        we.clear();
        we.sendKeys(str);
    }

    private void checkRadio(By click,By status) {
        if(!driver.findElement(status).isSelected()) tryClickBy(click);
    }

    public void tryClickBy(By by){
        int timeOut = 50;//*0.1sec
        while (timeOut > 0){
            try {
                driver.findElement(by).click();
                break;
            } catch (ElementNotInteractableException e) {
//                System.out.println("Мой Эксэпшн");
            }

            try {
                sleep(100);
            } catch (InterruptedException e) { break; }
            timeOut--;
        }
    }

//    public void scrollBy(By by){
//        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
//        jsDriver.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(by));
//    }

}
