package com.ssdkj.bbs.modular.model;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ParseResult implements Serializable {

    private Long articleId;

    private Integer isParse;//1 点赞  0取消点赞

    private Integer parseCount;
}
