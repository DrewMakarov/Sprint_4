package edu.praktikum.samokat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import edu.praktikum.samokat.pages.OrderPage;

import java.time.Duration;

@RunWith(Parameterized.class)
public class TestOrder {
    private WebDriver driver;
    OrderPage NewOrder;
    boolean isUp;
    String firstName;
    String lastName;
    String addressName;
    String metro;
    String number;
    int rentTime;
    String yourComment;
    String color;


    public TestOrder(boolean isUp, String firstName, String lastName, String addressName, String metro, String number, int rentTime, String yourComment, String color) {
        this.isUp = isUp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressName = addressName;
        this.metro = metro;
        this.number = number;
        this.rentTime = rentTime;
        this.yourComment = yourComment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[] getInfo() {
        return new Object[][]{
                {true, "Самокат", "Петрович", "Кремль 1", "1", "74957397000", 1, "хочу самокат", "black"},
        };
    }


    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);

        driver.get("https://qa-scooter.praktikum-services.ru/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        NewOrder = new OrderPage(driver);
        NewOrder.clickCookie();
    }

    @Test
    //набор тест данных
    public void checkOrder() {
        NewOrder.clickOrderButton(isUp);
        NewOrder.login(firstName, lastName, addressName, number);
        NewOrder.clickMetroField(metro);
        NewOrder.clickNextButton();
        NewOrder.setDeliveryDateField();
        NewOrder.setRentalPeriodField(rentTime);
        NewOrder.setColorField(color);
        NewOrder.setCommentField(yourComment);
        NewOrder.clickOrderButtonInOrder();
        NewOrder.clickYesButton();
        NewOrder.confirmOrderInfo();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
