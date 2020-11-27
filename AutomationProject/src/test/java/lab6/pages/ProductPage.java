package lab6.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;

public class ProductPage extends PageObject {

    //@FindBy(xpath = "/html/body/div[1]/div/div[1]/div/div[2]/div[2]/h2")
    @FindBy(id = "le_title")
    private WebElementFacade resultDisplay;

    @FindBy(xpath = "//*[@id=\"top\"]/div[1]/div/div[1]/div/div[3]/div/div[2]/div[2]/div[1]/a")
    private WebElementFacade wishlistButton;

    @FindBy(xpath = "//*[@id=\"master-icon-settings\"]/div[1]")
    private WebElementFacade menuButton;

    @FindBy(xpath = "//*[@id=\"master-icon-settings\"]/div[2]/div/ul/li[1]/a")
    private WebElementFacade myProfileButton;

    @FindBy(xpath = "//*[@id=\"top\"]/div[1]/div/div[1]/div/div[3]/div/div[2]/div[2]/div[2]/a")
    private WebElementFacade iReadButton;


    public boolean see_search_result(String result) {

        return this.resultDisplay.getText().equalsIgnoreCase(result);
    }

    public void add_to_wishlist() {

        this.wishlistButton.click();
    }

    public void click_menu_button() {

        this.menuButton.click();
    }

    public void click_my_profile_button() {

        this.myProfileButton.click();
    }

    public void go_to_my_profile_page() {

        click_menu_button();
        click_my_profile_button();
    }

    public void add_book_to_my_library() {

        this.iReadButton.click();
    }

}