import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ReactNativeTests {
  private AppiumDriver driver;

  @BeforeMethod
  public void setUp() throws MalformedURLException {
    DesiredCapabilities desiredCapabilities = DesiredCapabilities.android();
    desiredCapabilities.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") +
        "/src/test/resources/VodQA.apk");
    desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ESPRESSO);
    desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Emulator");
    driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), desiredCapabilities);
  }

  private boolean isElementPresent(By by) {
    return getElement(by) != null;
  }

  private WebElement getElement(By by) {
    WebDriverWait wait;
    wait = new WebDriverWait(driver, 30);
    return wait.until(ExpectedConditions.elementToBeClickable(by));
  }

  @Test
  public void testChainedView() {
    getElement(MobileBy.AndroidViewTag("username")).clear();
    getElement(MobileBy.AndroidViewTag("username")).sendKeys("admin");
    getElement(MobileBy.AndroidViewTag("password")).clear();
    getElement(MobileBy.AndroidViewTag("password")).sendKeys("admin");
    getElement(MobileBy.xpath("//com.facebook.react.views.text.ReactTextView[@text='LOG IN']")).click();

    for (int i = 0; i < 10; i++) {
      System.out.println(isElementPresent(MobileBy.AndroidViewTag("chainedView")));
      getElement(MobileBy.AndroidViewTag("chainedView")).click();
    }
  }

  @AfterMethod
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }
}
