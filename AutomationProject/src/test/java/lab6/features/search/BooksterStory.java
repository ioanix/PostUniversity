package lab6.features.search;

import lab6.steps.serenity.BooksterSteps;
import lab6.steps.serenity.UserSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;


@RunWith(SerenityRunner.class)
public class BooksterStory {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public BooksterSteps reader;

    @Steps
    public UserSteps userReader;

    @Issue("valid login")
    @Test
    public void go_to_home_page_and_login() {

        reader.is_the_home_page();
        reader.enter_your_account();

        reader.fill_data_and_login(System.getProperty("username"), System.getProperty("password"));

        userReader.should_see_user(System.getProperty("user"));
    }

    @Issue("non valid login")
    @Test
    public void non_valid_login() {

        reader.is_the_home_page();
        reader.enter_your_account();

        reader.fill_data_and_login(System.getProperty("username"), "pass");

        reader.should_see_error_message("Email sau parola incorecte!");
    }

    @Issue("add to wishlist")
    @Test
    public void add_a_book_to_wishlist() {

        reader.is_the_home_page();
        reader.enter_your_account();

        reader.fill_data_and_login(System.getProperty("username"), System.getProperty("password"));

        userReader.search("Ferma animalelor");
        userReader.should_see_results("Ferma animalelor");

        userReader.click_wishlist();
        userReader.go_to_profile();

        userReader.go_to_wishlist();
        userReader.should_see_book_name_in_wishlist("Ferma Animalelor");
    }

    @Issue("add book to library")
    @Test
    public void add_a_book_to_library() {

        reader.is_the_home_page();
        reader.enter_your_account();

        reader.fill_data_and_login(System.getProperty("username"), System.getProperty("password"));
        userReader.search("The Rosie Project");

        userReader.should_see_results("The Rosie Project (Don Tillman #1)");
        userReader.add_book_to_my_library();

        userReader.go_to_profile();
        userReader.go_to_my_library();
        userReader.should_see_book_name_in_my_library("The Rosie Project");

    }
}