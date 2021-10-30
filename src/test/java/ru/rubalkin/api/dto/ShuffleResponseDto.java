package ru.rubalkin.api.dto;

public class ShuffleResponseDto extends BaseResponseDto {
    private Boolean shuffled;

    public Boolean getShuffled() {
        return shuffled;
    }

    public void setShuffled(Boolean shuffled) {
        this.shuffled = shuffled;
    }
}
