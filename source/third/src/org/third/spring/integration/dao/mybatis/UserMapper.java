
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.third.testdata.user.domain.UserDM;

/**
 * <pre>
 *
 * </pre>
 */
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{userId}")
    UserDM getUser(@Param("userId") long id);
}