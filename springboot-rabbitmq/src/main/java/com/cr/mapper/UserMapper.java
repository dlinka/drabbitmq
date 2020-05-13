package com.cr.mapper;

import com.cr.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User getUserByUid(Integer uid);

}
