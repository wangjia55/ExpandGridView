package jacob.expandgridview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.Log;
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

    public boolean isDeleteMode = false;

    public static final int TYPE_ADMIN = 0;
    public static final int TYPE_MEMBER = 1;

    private int mType;
    private OnMemberOperationListener mListener;

    private ObjectAnimator mAnimatorMember;

    public GroupMemberAdapter(Context context, int type, List<Users> usersList,
                              OnMemberOperationListener memberOperListener) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        mType = type;
        this.usersList = usersList;
        mListener = memberOperListener;

    }

    @Override
    public int getCount() {
        if (usersList.size() > 0) {
            return usersList.size() + 2;
        }
        return 0;
    }

    @Override
    public Users getItem(int position) {
        if (position < usersList.size()) {
            return usersList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            ViewHolder viewHolder = new ViewHolder();

            if (position == getCount() - 1 && !isDeleteMode) {
                // 删除的按钮
                convertView = mLayoutInflater.inflate(R.layout.layout_delete_member, null);
            } else if (position == getCount() - 2 && !isDeleteMode) {
                //添加的按钮
                convertView = mLayoutInflater.inflate(R.layout.layout_add_member, null);
            } else {
                //普通的成员的布局
                convertView = mLayoutInflater.inflate(R.layout.layout_menber_item, null);
                viewHolder.avatarView = (RoundImageView) convertView.findViewById(R.id.avatar_view);
                viewHolder.imageViewDelete = (ImageView) convertView.findViewById(R.id.image_view_delete);
                viewHolder.textViewName = (TextView) convertView.findViewById(R.id.text_view_name);
            }
            convertView.setTag(viewHolder);
            convertView.setOnClickListener(createClickListener(convertView, position));
        }
        initializeViews(position, convertView);
        return convertView;
    }


    private void initializeViews(int position, View convertView) {
        if (isDeleteMode && (position >= usersList.size())) {
            convertView.setVisibility(View.INVISIBLE);
            return;
        } else {
            if (mType == TYPE_ADMIN) {
                convertView.setVisibility(View.VISIBLE);
            } else {
                if ((position == getCount() - 1) && (position > usersList.size())) {
                    convertView.setVisibility(View.INVISIBLE);
                    return;
                } else {
                    convertView.setVisibility(View.VISIBLE);
                }
            }
        }

        Users users = getItem(position);
        if (users == null) return;
        ViewHolder holder = (ViewHolder) convertView.getTag();
        Log.e("TAG", "position:" + position);

        if (holder.avatarView == null) {
            return;
        }
        holder.avatarView.setImageResource(users.getAvatar());
        holder.textViewName.setText(users.getName());
        if (isDeleteMode) {
            if (users.getRole() == Users.Role.Admin) {
                holder.imageViewDelete.setVisibility(View.INVISIBLE);
            } else {
                holder.imageViewDelete.setVisibility(View.VISIBLE);
            }
        } else {
            holder.imageViewDelete.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 根据不同位置设置不同的点击事件
     */
    private View.OnClickListener createClickListener(View view, final int position) {
        final View target = view;
        if ((position == getCount() - 1) && !isDeleteMode) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isDeleteMode = true;
                    notifyDataSetChanged();
                }
            };
        } else if ((position == getCount() - 2) && !isDeleteMode) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isDeleteMode = false;
                    notifyDataSetChanged();
                    if (mListener != null) {
                        mListener.clickAddMember();
                    }
                }
            };
        } else {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isDeleteMode && mListener != null) {
                        PropertyValuesHolder valuesHolderX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 0.1f);
                        PropertyValuesHolder valuesHolderY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 0.1f);
                        mAnimatorMember = ObjectAnimator.ofPropertyValuesHolder(target, valuesHolderX, valuesHolderY).setDuration(200);
                        mAnimatorMember.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                mListener.deleteMember(getItem(position));
                                target.setScaleX(1);
                                target.setScaleY(1);
                            }
                        });
                        mAnimatorMember.start();
                    }
                }
            };
        }
    }


    protected class ViewHolder {
        private RoundImageView avatarView;
        private ImageView imageViewDelete;
        private TextView textViewName;
    }
}
