package com.example.mystoredemo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Test_Usuarios {
    private WebDriver driver;
    static SoftAssert softAssert = new SoftAssert();

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Juliana.Sala\\IdeaProjects\\WebDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://automationpractice.com/index.php");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();

    }

    @Test(dataProvider = "data")
    public void login(String mail,String pass, String name) {
//      Ingreso pagina logueo/Login
        WebElement webElement = driver.findElement(By.cssSelector(".login"));
        softAssert.assertEquals(webElement.getAttribute("innerText"), "Sign in");
        webElement.click();
        driver.findElement(By.cssSelector("#email")).sendKeys(mail);
        driver.findElement(By.cssSelector("#passwd")).sendKeys(pass);
        driver.findElement(By.cssSelector("#passwd")).sendKeys(Keys.ENTER);


//      Verificaciones de logueo Satisfactorio
        try {
            webElement = driver.findElement(By.cssSelector(".alert-danger li"));
            Assert.assertFalse(webElement.isDisplayed(), "Error en autentificacion\n Pag Error:" + webElement.getText());
        } catch (Exception e){
            System.out.println("No hay problemas de autentificacion,\nno such element: Unable to locate element: {\"method\":\"css selector\",\"selector\":\".alert-danger li\"} ");

        }

        webElement = driver.findElement(By.cssSelector(".logout"));
        softAssert.assertTrue(webElement.isDisplayed(),"Usuario no logueado");
        softAssert.assertEquals(driver.findElement(By.cssSelector(".account")).getText(),name,"Usuario Logueado, pero no es el correspondiente");

//       Desloguearse/Log out
        webElement.click();
    }

    @DataProvider (name="data")
    public Object[][] getData() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(new File("data.json").getAbsolutePath());
        //Read JSON file
        JSONArray jsonA = (JSONArray) jsonParser.parse(reader);

        Object[][] datos = new Object[jsonA.size()][3];
        for (int i=0; i<jsonA.size();i++) {
            JSONObject json =(JSONObject)jsonA.get(i);
            JSONObject usuario = (JSONObject) json.get("usuario");
            datos[i][0]=usuario.get("mail");
            datos[i][1]=usuario.get("pass");
            datos[i][2]=usuario.get("name");
        }
        return datos;
    }


}
