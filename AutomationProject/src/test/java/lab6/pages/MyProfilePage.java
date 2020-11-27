package lab6.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;

import java.util.Arrays;
import java.util.List;

public class MyProfilePage extends PageObject {

    @FindBy(xpath = "//*[@id=\"top\"]/div[1]/div/div/div[1]/div[2]/div/ul[1]/li[2]/a")
    private WebElementFacade wishlist;

    @FindBy(xpath = "//*[@id=\"top\"]/div[1]/div/div/div[2]/div[3]")
    private WebElementFacade listOfBooks;

    @FindBy(xpath = "//*[@id=\"top\"]/div[1]/div/div/div[1]/div[2]/div/ul[1]/li[3]/a")
    private WebElementFacade myLibraryButton;

    @FindBy(xpath = "//*[@id=\"top\"]/div[1]/div/div/div[2]/div[3]/div[1]")
    private WebElementFacade listOfBooksInLibrary;


    public void go_to_wishlist() {

        this.wishlist.click();
    }

    public List<String> get_wishlist_books() {

        return Arrays.asList(this.listOfBooks.getText().split("\n"));

    }

    public void click_my_library_button() {

        this.myLibraryButton.click();
    }

    public List<String> get_books_in_my_library() {

        return Arrays.asList(this.listOfBooksInLibrary.getText().split("\n"));
    }

}