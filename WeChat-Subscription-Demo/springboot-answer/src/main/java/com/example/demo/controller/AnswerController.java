package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("answer")
@Api("应答程序")
public class AnswerController {

	private static final String TOKEN = "4a338414d3d94768823cc3051d0c3e91";
	/**
	 * 	模仿应答机器人
	 * @param content
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "sendmsg", method = RequestMethod.GET)
	@ApiOperation("应答")
	@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "验证值", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "info", value = "问题", required = true, paramType = "query", dataType = "string")})
	public String sendmsg(
			@RequestParam(name = "token", required = true) String token,
			@RequestParam(name = "info", required = true) String content) {
		if (TOKEN.equals(token)) {
			String respMsg = null;
			System.out.println(content);
			switch (content) {
			case "1":respMsg="星期一";break;
			case "今天天气情况":respMsg="晴朗，有风";break;
			default:
				break;
			}
			if (null != respMsg) {
				return respMsg;
			}
		}
		return "正再思索ing...";
	}
}
