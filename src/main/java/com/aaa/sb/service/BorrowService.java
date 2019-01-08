package com.aaa.sb.service;

import java.util.List;
import java.util.Map;

/**
 * className:BorrowService
 * discription:
 * author:zhangran
 * createTime:2018-12-26 15:33
 */

public interface BorrowService {

    int toBorrow(Map map);

    Map toGetCheck(Map map);
}
