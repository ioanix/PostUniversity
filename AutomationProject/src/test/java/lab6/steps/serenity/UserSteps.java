package lab6.steps.serenity;

import lab6.pages.MyProfilePage;
import lab6.pages.ProductPage;
import lab6.pages.UserPage;
import net.thucydides.core.annotations.Step;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

public class UserSteps {

    UserPage userPage;
    ProductPage productPage;
    MyProfilePage myProfilePage;

    @Step
    public void should_see_user(String userName) {

        assertThat(userName, userPage.see_the_user(userName));
    }

    @Step
    public void search(String words) {

        userPage.search_for(words);
    }

    @Step
    public void should_see_results(String result) {

        assertThat(result, productPage.see_search_result(result));
    }

    @Step
    public void click_wishlist() {

        productPage.add_to_wishlist();
    }

    @Step
    public void go_to_profile() {

        productPage.go_to_my_profile_page();
    }

    @Step
    public void go_to_my_profile_page() {

        userPage.go_to_my_profile();
    }

    @Step
    public void go_to_wishlist() {

        myProfilePage.go_to_wishlist();
    }

    @Step
    public void should_see_book_name_in_wishlist(String bookTitle) {

        assertThat(myProfilePage.get_wishlist_books(), hasItem(containsString(bookTitle)));
    }

    @Step
    public void add_book_to_my_library() {

        productPage.add_book_to_my_library();
    }

    @Step
    public void go_to_my_library() {

        myProfilePage.click_my_library_button();
    }

    @Step
    public void should_see_book_name_in_my_library(String bookTitle) {

        assertThat(myProfilePage.get_books_in_my_library(), hasItem(containsString(bookTitle)));
    }

}

