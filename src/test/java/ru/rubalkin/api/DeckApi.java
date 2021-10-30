package ru.rubalkin.api;

import ru.rubalkin.api.dto.DrawResponseDto;
import ru.rubalkin.api.dto.ShuffleResponseDto;

import java.util.List;
import java.util.Set;

@SuppressWarnings("all")
public interface DeckApi {

    List<String> getDrawedCards();
    Integer getCards();

    ShuffleResponseDto newDeck(Boolean jokers_enabled);
    ShuffleResponseDto newDeck(Set<String> cards);
    ShuffleResponseDto shuffle(int deck_count);
    ShuffleResponseDto reShuffle(Boolean remaining);
    DrawResponseDto draw(int count);
    DrawResponseDto drawFromBottom(int count);
}
