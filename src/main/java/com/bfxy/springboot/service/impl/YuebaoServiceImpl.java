/** TODO
 * @package  com.bfxy.springboot.service.impl
 * @file    ZhifubaoServiceImpl.java
 * @author  lipf
 * @Date    2019年8月28日  下午3:11:40
 * @version   V 1.0
 */
package com.bfxy.springboot.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bfxy.springboot.dao.ZhifubaoMapper;
import com.bfxy.springboot.entity.AccountMessage;
import com.bfxy.springboot.service.YuebaoService;
import com.bfxy.springboot.utils.HttpRequestUtil;

/**TODO
 * @package  com.bfxy.springboot.service.impl
 * @file     ZhifubaoServiceImpl.java
 * @author   lipf
 * @date     2019年8月28日 下午3:11:40
 * @version  V 1.0
 */
@Service
public class YuebaoServiceImpl implements YuebaoService{
	@Autowired
    private ZhifubaoMapper yuebaoMapper;
	@Value("${path.zhifubao.confirm}")
	private String urlPath;
	
	/** <p>Title: updateAccont</p>
	 * <p>Description: </p>
	 * @param account
	 * @throws Exception 
	 * @see com.bfxy.springboot.service.YuebaoService#updateAccont(com.bfxy.springboot.entity.AccountMessage)  
	 */
	@Override
	public void updateAccont(AccountMessage account) {
		//更新账户
		int updateAccount = yuebaoMapper.updateAccount(account);
		
		if (updateAccount==1) {
			try {
				account.setMessageId(UUID.randomUUID()+""+System.currentTimeMillis());
				int message = yuebaoMapper.insertMessage(account);
                if (message==1) {
					//如果操作成功则发送消息到队列
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/** <p>Title: amountMessageHandler</p>
	 * <p>Description: </p>
	 * @param account
	 * @throws Exception 
	 * @see com.bfxy.springboot.service.YuebaoService#amountMessageHandler(com.bfxy.springboot.entity.AccountMessage)  
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void amountMessageHandler(AccountMessage account) throws Exception {
		// 首先要查询监听到的消息是够已经处理，防止消息重复处理（重复转入金额）
		
		int resultMessage = queryMessageBymid(account.getMessageId());
		if (resultMessage>0) {
			throw new Exception("error=0消息处理已经被处理，请勿重复发送");
		}
		try {
			//更新余额宝账户信息并且保存消息处理记录
			int updateAccount = yuebaoMapper.updateAccount(account);
			yuebaoMapper.insertMessage(account);
			StringBuffer bf = new StringBuffer("");
			bf.append(urlPath).append("?mid=").append(account.getMessageId());
			System.out.println(bf);
			//调用支付宝消息回调确认接口，防止解析的丢失
			String doJsonPost = HttpRequestUtil.doformUrlencodedPost(bf.toString());
		} catch (Exception e) {
			throw new Exception("error-->>数据库操作异常"+e.toString());
		}
	}

	/**@author lipf
	 * 在消息表中查询是否处理过该消息
	 * @method queryMessage
	 * @param messageId
	 * @return void
	 * @date 2019年8月28日 下午4:58:22
	 */
	private int queryMessageBymid(String messageId) {
		if (!StringUtils.isEmpty(messageId)) {
			int result =yuebaoMapper.queryMessageBymid(messageId);
			return result;
		}
		return 0;
	}
	
	public static void main(String[] args) {
		String doJsonPost = HttpRequestUtil.doformUrlencodedPost("http://localhost:8881/zhifubao/callbackConfirmMessage?mid=514efe3a-938e-4da7-952f-048a08b398931567039168052");
//		Map params = new HashMap<>();
//		params.put("mid", "514efe3a-938e-4da7-952f-048a08b398931567039168052");
//		String doJsonPost = HttpRequestUtil.doPost("http://localhost:8881/zhifubao/callbackConfirmMessage",params);
//		HttpRequestUtil.
	}
}
