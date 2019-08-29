/** TODO
 * @package  com.bfxy.springboot.dao
 * @file    ZhifubaoMapper.java
 * @author  lipf
 * @Date    2019年8月28日  下午2:49:20
 * @version   V 1.0
 */
package com.bfxy.springboot.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bfxy.springboot.entity.AccountMessage;

/**TODO
 * @package  com.bfxy.springboot.dao
 * @file     ZhifubaoMapper.java
 * @author   lipf
 * @date     2019年8月28日 下午2:49:20
 * @version  V 1.0
 */
@Mapper
public interface ZhifubaoMapper {
	@Update("update t_account set account=account+#{account.account},update_time=now() where user_id=#{account.userId}")
	int updateAccount(@Param("account") AccountMessage account);
	
	
	@Insert("insert into t_message(user_id,message_id,account,create_time) values(#{account.userId},#{account.messageId},#{account.account},now())")
	int insertMessage(@Param("account") AccountMessage account);

	/**@author lipf
	 * TODO
	 * @method queryMessageBymid
	 * @param messageId
	 * @return
	 * @return int
	 * @date 2019年8月28日 下午5:03:40
	 */
	@Select("select count(1) from t_message where message_id=#{messageId}")
	int queryMessageBymid(@Param("messageId")String messageId);
}
