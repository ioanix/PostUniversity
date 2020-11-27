package lab6.features.search;

import lab6.steps.serenity.BooksterSteps;
import lab6.steps.serenity.UserSteps;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;


@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("src/test/resources/features/search/searchData.csv")
public class BooksterStoryCsvData {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public BooksterSteps reader;

    @Steps
    public UserSteps userReader;

    String word;

    @Issue("search book")
    @Test
    public void search_for() {

        reader.is_the_home_page();
        reader.enter_your_account();

        reader.fill_data_and_login(System.getProperty("username"), System.getProperty("password"));

        userReader.search(word);

        userReader.should_see_results(word);
    }
}