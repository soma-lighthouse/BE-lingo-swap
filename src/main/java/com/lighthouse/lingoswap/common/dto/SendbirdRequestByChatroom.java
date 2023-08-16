package com.lighthouse.lingoswap.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class SendbirdRequestByChatroom {

    private List<Long> members;
}

