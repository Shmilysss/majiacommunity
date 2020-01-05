package shequ.wqy.community.dto;

import lombok.Data;

/**
 * Author: wanqiangying
 * Date: 2020/1/5 21:57
 * Content:
 */

@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(1000);
        resultDTO.setMessage("success!");
        return resultDTO;
    }
}

