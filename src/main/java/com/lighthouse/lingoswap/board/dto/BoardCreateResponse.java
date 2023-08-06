package com.lighthouse.lingoswap.board.dto;

import java.util.ArrayList;
import java.util.List;

public class BoardCreateResponse {

    private List<BoardResponse> results = new ArrayList<>();

    public void add(BoardResponse boardResponse) {
        results.add(boardResponse);
    }
}
