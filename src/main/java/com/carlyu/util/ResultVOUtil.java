package com.carlyu.util;

import com.carlyu.vo.ResultVO;

/**
 * HTTP 请求返回的最外层对象 ResultVO 工具类
 */
public class ResultVOUtil {
    /**
     * 成功
     *
     * @param object 传入一个对象
     * @return JSON
     */
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    /**
     * 成功
     *
     * @param msg    信息
     * @param object 传入一个对象
     * @return JSON
     */
    public static ResultVO success(String msg, Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg(msg);
        resultVO.setData(object);
        return resultVO;
    }

    /**
     * 成功
     *
     * @param code code
     * @param msg  信息
     * @return JSON
     */
    public static ResultVO success(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    /**
     * 失败
     *
     * @param code code
     * @param msg  信息
     * @return JSON
     */
    public static ResultVO fail(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }
}

