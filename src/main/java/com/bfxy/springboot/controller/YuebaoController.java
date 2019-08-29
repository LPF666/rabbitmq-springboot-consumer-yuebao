/** TODO
 * @package  com.bfxy.springboot.controller
 * @file    ZhifubaoController.java
 * @author  lipf
 * @Date    2019年8月28日  下午3:04:54
 * @version   V 1.0
 */
package com.bfxy.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bfxy.springboot.entity.AccountMessage;
import com.bfxy.springboot.result.CommonResult;
import com.bfxy.springboot.service.YuebaoService;

import io.swagger.annotations.Api;

/**TODO
 * @package  com.bfxy.springboot.controller
 * @file     ZhifubaoController.java
 * @author   lipf
 * @date     2019年8月28日 下午3:04:54
 * @version  V 1.0
 */
@Api(value = "YuebaoController", description = "余额宝转账服务")
@Controller
@RequestMapping("/yuebao")
public class YuebaoController {
	
	@Autowired
	private YuebaoService  zhifubaoService;
	
}
