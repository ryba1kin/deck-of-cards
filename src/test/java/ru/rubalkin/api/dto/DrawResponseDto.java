package ru.rubalkin.api.dto;

import java.util.List;

public class DrawResponseDto extends BaseResponseDto {
    private List<CardDto> cards;

    public List<CardDto> getCards() {
        return cards;
    }

    public void setCards(List<CardDto> cards) {
        this.cards = cards;
    }
}
