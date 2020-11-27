package lab6.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;

public class UserPage extends PageObject {

    @FindBy(xpath = "/html/body/nav/div/div/div[1]/div[2]/div[2]/div[1]/a")
    private WebElementFacade user;

    @FindBy(xpath = "//*[@id=\"frmSearch\"]/div/input")
    private WebElementFacade searchInput;

    @FindBy(xpath = "/html/body/nav/div/div/div[3]/div/form/div[2]/ul/li[1]/a/div[2]/span[1]")
    private WebElementFacade searchButton;

    @FindBy(xpath = "//*[@id=\"master-icon-settings\"]/div")
    private WebElementFacade menuButton;

    @FindBy(xpath = "//*[@id=\"master-menu-settings\"]/div/ul/li[1]/a")
    private WebElementFacade myProfileButton;


    public boolean see_the_user(String name) {

        return user.getText().equalsIgnoreCase(name);
    }

    public void enter_text_to_search_for(String words) {

        this.searchInput.type(words);
    }

    public void click_search() {

        this.searchButton.click();
    }

    public void search_for(String words) {

        enter_text_to_search_for(words);
        click_search();
    }

    public void click_menu_button() {

        this.menuButton.click();
    }

    public void my_profile_button_click() {

        this.myProfileButton.click();
    }

    public void go_to_my_profile() {

        click_menu_button();
        my_profile_button_click();
    }

}