package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(int userId,int offset,int limit);//userId默认0，仅当查看自己帖子时候才赋值,offset 起始行行号，limit每行多少数据

    //当动态的拼一个条件时，并且该方法有且只有一个条件，参数之前必须取别名
    //即只有一个参数并且在<if>中使用，则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);
}
