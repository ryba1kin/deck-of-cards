package ru.rubalkin.utils;

import ru.rubalkin.api.dto.CardDto;
import ru.rubalkin.api.dto.DrawResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class CardUtils {
    private CardUtils(){}

    public static List<String> extractCardsFromDrawResponse(DrawResponseDto dto) {
        return dto.getCards().stream()
                .map(CardDto::getCode)
                .collect(Collectors.toList());
    }
}
