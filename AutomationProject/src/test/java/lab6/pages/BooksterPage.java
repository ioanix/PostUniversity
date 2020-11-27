package lab6.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;

@DefaultUrl("https://www.bookster.ro/landing/")
public class BooksterPage extends PageObject {

    @FindBy(css = "a[href=\"https://accounts.bookster.ro/login\"]")
    private WebElementFacade enterAccountButton;

    @FindBy(id = "create-account")
    private WebElementFacade createAccountButton;

    @FindBy(id = "username")
    private WebElementFacade textName;

    @FindBy(id = "password")
    private WebElementFacade textPassword;

    @FindBy(xpath = "/html/body/div[1]/div[2]/div/div/div[1]/div/form/div[3]/div/input")
    private WebElementFacade loginButton;

    @FindBy(xpath = "/html/body/div[1]/div[2]/div/div/div[1]/div/form/div[3]/div/div")
    private WebElementFacade message;


    public void enter_account() {

        enterAccountButton.click();
    }

    public void enter_username(String name) {

        this.textName.type(name);
    }

    public void enter_password(String password) {

        this.textPassword.type(password);
    }

    public void click_login() {

        this.loginButton.click();
    }

    public boolean see_message_for_invalid_login(String errorMessage) {

        return message.getText().equalsIgnoreCase(errorMessage);
    }

}