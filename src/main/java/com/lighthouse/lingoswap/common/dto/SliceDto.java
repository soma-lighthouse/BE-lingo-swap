package com.lighthouse.lingoswap.common.dto;

import java.util.List;

public record SliceDto<E>(List<E> content, Long nextId) {

}
