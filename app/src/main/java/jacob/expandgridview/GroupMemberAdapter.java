package jacob.expandgridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 类似微信这样的群组人员管理主要有以下2个特点：
 * 1.如果是管理员查看群信息：人员最后会多出2个按钮，一个是添加，一个删除（也就是说管理员是有删除权限的，普通人没有），
 * 当点击删除的时候，处理自己(管理员)之外其他人头像都会显示一个删除的按钮，并且此时多出的按钮也会消失
 * <p/>
 * 2.如果是普通人查看群信息，人员最后只有一个添加的按钮(普通人是没有删除权限的)
 */
public class GroupMemberAdapter extends BaseAdapter {

    private List<Users> usersList = new ArrayList<Users>();

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    public static final int TYPE_ADMIN = 0;
    public static final int TYPE_MEMBER = 1;

    private int mType;

    public GroupMemberAdapter(Context context, int type, List<Users> usersList) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        mType = type;
        this.usersList = usersList;
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Users getItem(int position) {
        return usersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_menber_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.avatarView = (RoundImageView) convertView.findViewById(R.id.avatar_view);
            viewHolder.imageViewDelete = (ImageView) convertView.findViewById(R.id.image_view_delete);
            viewHolder.textViewName = (TextView) convertView.findViewById(R.id.text_view_name);

            convertView.setTag(viewHolder);
        }
        initializeViews((Users) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Users users, ViewHolder holder) {
        holder.avatarView.setImageResource(users.getAvatar());
        holder.textViewName.setText(users.getName());

    }

    protected class ViewHolder {
        private RoundImageView avatarView;
        private ImageView imageViewDelete;
        private TextView textViewName;
    }
}
