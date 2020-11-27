package lab6.steps.serenity;

import lab6.pages.BooksterPage;
import net.thucydides.core.annotations.Step;

import static org.hamcrest.MatcherAssert.assertThat;

public class BooksterSteps {

    BooksterPage booksterPage;


    @Step
    public void is_the_home_page() {

        booksterPage.open();
    }

    @Step
    public void enter_your_account() {

        booksterPage.enter_account();
    }

    @Step
    public void fill_data_and_login(String username, String password) {

        booksterPage.enter_username(username);
        booksterPage.enter_password(password);
        booksterPage.click_login();
    }


    @Step
    public void should_see_error_message(String message) {

        assertThat(message, booksterPage.see_message_for_invalid_login(message));
    }

}