package ru.rubalkin.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.ResponseSpecification;
import ru.rubalkin.api.dto.BaseResponseDto;
import ru.rubalkin.api.dto.DrawResponseDto;
import ru.rubalkin.api.dto.ShuffleResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static java.lang.String.join;
import static java.util.Collections.singletonMap;
import static java.util.Objects.isNull;
import static org.hamcrest.Matchers.is;
import static ru.rubalkin.utils.CardUtils.extractCardsFromDrawResponse;

public class DeckApiImpl implements DeckApi {
    private static final String BASE_PATH = "/api/deck";
    private static final String JOKERS_ENABLED = "jokers_enabled";
    private static final String NEW = "/new";
    private static final String NEW_SHUFFLE = "/new/shuffle";
    private static final String CARDS = "cards";
    private static final String DECK_COUNT = "deck_count";
    private static final String TEMPLATE_DECK_SHUFFLE = "/{deck_id}/shuffle";
    private static final String REMAINING = "remaining";
    private static final String TEMPLATE_DECK_DRAW = "/{deck_id}/draw";
    private static final String TEMPLATE_DECK_DRAW_BOTTOM = "/{deck_id}/draw/bottom";
    private static final String COUNT = "count";
    private static final ResponseSpecification responseSpec = new ResponseSpecBuilder()
        .expectStatusCode(200)
        .expectBody("success", is(true))
        .build();
    private static final RestAssuredConfig config = RestAssured.config()
        .objectMapperConfig(new ObjectMapperConfig()
            .jackson2ObjectMapperFactory((cls, charset) -> new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)));
    private String baseUrl;
    private String deckId;
    private Integer remaining;
    private List<String> drawedCards;


    static {
        RestAssured
            .filters(
                new AllureRestAssured(),
                new RequestLoggingFilter(LogDetail.ALL),
                new ResponseLoggingFilter(LogDetail.ALL)
            );
    }

    private DeckApiImpl() {
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final DeckApiImpl deckApi;

        private Builder() {
            this.deckApi = new DeckApiImpl();
        }

        public DeckApiImpl build() {
            return this.deckApi;
        }

        public Builder withBaseUrl(String baseUrl) {
            this.deckApi.baseUrl = baseUrl;
            return this;
        }
    }


    @Override
    public List<String> getDrawedCards() {
        return this.drawedCards;
    }

    @Override
    public Integer getCards() {
        return this.remaining;
    }

    @Override
    public ShuffleResponseDto newDeck(Boolean jokers_enabled) {
        this.drawedCards = new ArrayList<>();
        return setDeckAndReturn(given()
            .config(config)
            .baseUri(baseUrl)
            .basePath(BASE_PATH)
            .param(JOKERS_ENABLED, jokers_enabled)
            .get(NEW)
            .then()
            .spec(responseSpec)
            .extract().body()
            .as(ShuffleResponseDto.class));
    }

    @Override
    public ShuffleResponseDto newDeck(Set<String> cards) {
        this.drawedCards = new ArrayList<>();
        return setDeckAndReturn(given()
            .config(config)
            .baseUri(baseUrl)
            .basePath(BASE_PATH)
            .params(cards.isEmpty() ? null : singletonMap(CARDS, join(",", cards)))
            .get(NEW_SHUFFLE)
            .then()
            .spec(responseSpec)
            .extract().body()
            .as(ShuffleResponseDto.class));
    }

    @Override
    public ShuffleResponseDto shuffle(int deck_count) {
        this.drawedCards = new ArrayList<>();
        return setDeckAndReturn(given()
            .config(config)
            .baseUri(baseUrl)
            .basePath(BASE_PATH)
            .param(DECK_COUNT, deck_count)
            .get(NEW_SHUFFLE)
            .then()
            .spec(responseSpec)
            .extract().body()
            .as(ShuffleResponseDto.class));
    }

    @Override
    public ShuffleResponseDto reShuffle(Boolean remaining) {
        return setDeckAndReturn(given()
            .config(config)
            .baseUri(baseUrl)
            .basePath(BASE_PATH)
            .params(isNull(remaining) ? null : singletonMap(REMAINING, remaining))
            .get(TEMPLATE_DECK_SHUFFLE, this.deckId)
            .then()
            .spec(responseSpec)
            .extract().body()
            .as(ShuffleResponseDto.class));
    }

    @Override
    public DrawResponseDto draw(int count) {
        DrawResponseDto dto = given()
            .config(config)
            .baseUri(baseUrl)
            .basePath(BASE_PATH)
            .param(COUNT, count)
            .get(TEMPLATE_DECK_DRAW, this.deckId)
            .then()
            .spec(responseSpec)
            .extract().body()
            .as(DrawResponseDto.class);
        this.drawedCards.addAll(extractCardsFromDrawResponse(dto));
        return setDeckAndReturn(dto);
    }

    @Override
    public DrawResponseDto drawFromBottom(int count) {
        DrawResponseDto dto = given()
            .config(config)
            .baseUri(baseUrl)
            .basePath(BASE_PATH)
            .param(COUNT, count)
            .get(TEMPLATE_DECK_DRAW_BOTTOM, this.deckId)
            .then()
            .spec(responseSpec)
            .extract().body()
            .as(DrawResponseDto.class);
        this.drawedCards.addAll(extractCardsFromDrawResponse(dto));
        return setDeckAndReturn(dto);
    }

    private <T extends BaseResponseDto> T setDeckAndReturn(T dto) {
        this.deckId = dto.getDeck_id();
        this.remaining = dto.getRemaining();
        return dto;
    }
}
