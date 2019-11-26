package shequ.wqy.community.mapper;

import org.apache.ibatis.annotations.*;
import shequ.wqy.community.model.Question;
import shequ.wqy.community.model.User;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer creator);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name},account_id = #{accountId},token = #{token},gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where id = #{id}")
    void update(User user);
}
