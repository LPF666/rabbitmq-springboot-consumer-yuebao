/** TODO
 * @package  com.bfxy.springboot.service
 * @file    ZhifubaoService.java
 * @author  lipf
 * @Date    2019年8月28日  下午3:10:49
 * @version   V 1.0
 */
package com.bfxy.springboot.service;

import org.springframework.stereotype.Service;

import com.bfxy.springboot.entity.AccountMessage;

/**TODO
 * @package  com.bfxy.springboot.service
 * @file     ZhifubaoService.java
 * @author   lipf
 * @date     2019年8月28日 下午3:10:49
 * @version  V 1.0
 */

public interface YuebaoService {

	/**@author lipf
	 * TODO
	 * @method updateAccont
	 * @param account
	 * @return void
	 * @date 2019年8月28日 下午3:16:20
	 */
	void updateAccont(AccountMessage account);

	/**@author lipf
	 * 监听消息后的处理
	 * @method amountMessageHandler
	 * @param account
	 * @return void
	 * @throws Exception 
	 * @date 2019年8月28日 下午4:56:41
	 */
	void amountMessageHandler(AccountMessage account) throws Exception;
	
}
