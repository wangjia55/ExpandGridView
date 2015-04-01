package jacob.expandgridview;

/**
 * Package : jacob.expandgridview
 * Author : jacob
 * Date : 15-4-1
 * Description : 这个类是封装用户的信息
 */
public class Users {

    private int avatar;

    private String name;

    private Role role;

    public Users(int avatar, String name, Role role) {
        this.avatar = avatar;
        this.name = name;
        this.role = role;
    }

    public int getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    /**
     * 权限，管理员和普通人员
     */
    enum Role {
        Amin,
        Member
    }
}


