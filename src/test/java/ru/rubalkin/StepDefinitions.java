package ru.rubalkin;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.qameta.allure.Allure;
import ru.rubalkin.api.DeckApi;
import ru.rubalkin.api.DeckApiImpl;
import ru.rubalkin.stash.Stash;

import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.Matchers.*;
import static org.junit.platform.commons.util.CollectionUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.rubalkin.utils.CardUtils.extractCardsFromDrawResponse;

public class StepDefinitions {
    DeckApi api = DeckApiImpl.builder()
            .withBaseUrl("http://deckofcardsapi.com")
            .build();

    @Before
    public void before() {
        Stash.init();
    }

    @After
    public void after() {
        Stash.clear();
    }

    @Given("New deck")
    public void new_deck() {
        api.newDeck(false);
    }

    @Given("Save remaining cards count as {string}")
    public void save_remaining_cards_count_as(String remainingCardsKey) {
        Stash.store(remainingCardsKey, api.getCards());
    }

    @When("I draw {int} card(s) from deck")
    public void i_draw_card_from_deck(int cards) {
        api.draw(cards);
    }

    @When("I draw {int} cards from deck bottom and save cards in {string}")
    public void i_draw_cards_from_deck_bottom_and_save_cards_in(Integer drawedCardsCount, String saveCardsKey) {
        api.drawFromBottom(drawedCardsCount);
        Stash.store(saveCardsKey, api.getDrawedCards());
    }

    @Then("Assert in deck {int} cards")
    public void assert_in_deck_cards(int expectedCards) {
        Allure.addAttachment("assertThat",
                format("expected cards left in the deck [%d], actual [%d]", expectedCards, api.getCards()));
        assertThat(api.getCards(), equalTo(expectedCards));
    }


    @Given("New deck of Aces")
    public void new_deck_of_aces() {
        api.newDeck(toSet(new String[]{"AS", "AC", "AH", "AD"}));
    }

    @Then("Assert all drawed cards Aces")
    public void assert_all_drawed_cards_aces() {
        System.out.println("api.getDrawedCards() = " + api.getDrawedCards());
        Allure.addAttachment("assertThat",
                format("everyItems in %s starts with 'A'(Aces)", api.getDrawedCards()));
        assertThat(api.getDrawedCards(), everyItem(startsWith("A")));
    }

    @Then("Assert that remaining cards lesser than stored in {string}")
    public void assert_that_remaining_cards_lesser_than_stored_in(String cardsKey) {
        assertThat(api.getCards(), lessThan(Stash.get(cardsKey)));
    }

    @Then("Assert that all cards from {string} dont contains in all remaining in deck cards")
    public void assert_that_all_cards_from_dont_contains_in_all_remaining_in_deck_cards(String expectedCardsKey) {
        List<String> expectedCards = Stash.get(expectedCardsKey);
        Allure.addAttachment("expected",
                format("cards from stash %s", expectedCards));
        List<String> actualCardsInDeck = extractCardsFromDrawResponse(api.draw(api.getCards()));
        Allure.addAttachment("actual",
                format("all remaining cards from deck %s", actualCardsInDeck));
        assertThat(actualCardsInDeck, not(contains(expectedCards)));
    }
}
