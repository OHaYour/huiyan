package com.huiyan.service;

import com.huiyan.pojo.KeyWord;

import java.util.List;

public interface KeyWordService {

    /**
     * 查询出所有的屏蔽关键字，用于评论回复比对
     * @return
     */
    List<KeyWord> queryAllKeyWords();

    void queryKeyWordPage();

    /**
     * 新增关键字
     */
    void addKeyWord(KeyWord keyWord);

    /**
     * 删除关键字
     * @param keywordId
     */
    void delKeyWord(String keywordId);
}
