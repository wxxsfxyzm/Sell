package com.carlyu.util;

import com.carlyu.vo.ResultVO;

/**
 * HTTP 请求返回的最外层对象 ResultVO 工具类
 */
public class ResultVOUtil {
    /**
     * JSON 数据 成功返回
     */
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO failLogin(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }
}

