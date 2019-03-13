package com.beautifulsoup.chengfeng.web;

import com.beautifulsoup.chengfeng.constant.RedisConstant;
import com.beautifulsoup.chengfeng.dao.UserMapper;
import com.beautifulsoup.chengfeng.pojo.User;
import com.beautifulsoup.chengfeng.utils.JsonSerializableUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChengfengInit implements InitializingBean {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<User> users=userMapper.selectAllUsers();
        users.stream().parallel()
                .forEach(user->{
                    stringRedisTemplate.opsForHash().put(RedisConstant.USERINFOS,user.getNickname(), JsonSerializableUtil.obj2String(user));
                });
    }


}
