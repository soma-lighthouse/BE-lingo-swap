package com.lighthouse.lingoswap.board.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardCreateResponse {

    private List<BoardResponse> results = new ArrayList<>();

    public BoardCreateResponse(List<BoardResponse> results) {
        this.results = results;
    }

    public void add(BoardResponse boardResponse) {
        results.add(boardResponse);
    }
}

