package cn.conststar.wall.service;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.mapper.MapperUser;
import cn.conststar.wall.pojo.PojoUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceUser implements MapperUser {

    private MapperUser mapperUser;

    private boolean isEmail(String email) {
        Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }

    public PojoUser login(String email, String password) throws Exception {
        PojoUser user = getUser(email, password);
        updateLastTime(user);
        return user;
    }

    @Override
    public PojoUser getUser(String email, String password) throws Exception {
        PojoUser user = mapperUser.getUser(email, password);
        if (user == null)
            throw new ExceptionMain("请检查账号和密码", ExceptionMain.NOT_LOGIN);
        return user;
    }

    @Override
    public int updateLastTime(PojoUser pojoUser) throws Exception {
        return mapperUser.updateLastTime(pojoUser);
    }

    @Override
    public boolean verifyUser(PojoUser pojoUser) throws Exception {
        if (pojoUser == null)
            throw new ExceptionMain("用户未登录", ExceptionMain.NOT_LOGIN);
        boolean b = mapperUser.verifyUser(pojoUser);
        if (!b)
            throw new ExceptionMain("用户登录已失效", ExceptionMain.NOT_LOGIN);

        return b;
    }

    @Override
    public int addUser(String email, String password, String name) throws Exception {

        if (!isEmail(email))
            throw new ExceptionMain("邮箱格式有误");

        if (password.length() <= 6 || password.length() >= 18)
            throw new ExceptionMain("密码必须大于6个字符串小于18个字符");

        if (name.length() > 6)
            throw new ExceptionMain("名称不得超过6个字符");

        if (name.isEmpty())
            throw new ExceptionMain("名称不能为空");

        int line = mapperUser.addUser(email, password, name);

        if (line != 1) {
            throw new ExceptionMain("数据库操作失败，数据库添加行数为" + line, ExceptionMain.DEADLY); //wait
        }
        return line;
    }
}
