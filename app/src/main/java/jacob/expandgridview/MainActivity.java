package jacob.expandgridview;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements OnMemberOperationListener {
    private GridView mGridView;
    private GroupMemberAdapter mGroupAdapter;

    private List<Users> mUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView = (GridView) findViewById(R.id.gridView);

        mUserList.add(new Users(R.drawable.ic_avatar1, "Jimmy", Users.Role.Admin));
        mUserList.add(new Users(R.drawable.ic_avatar2, "Kenvin", Users.Role.Member));
        mUserList.add(new Users(R.drawable.ic_avatar6, "Jasper", Users.Role.Member));
        mUserList.add(new Users(R.drawable.ic_avatar4, "Jacob", Users.Role.Member));
        mUserList.add(new Users(R.drawable.ic_avatar5, "Richard", Users.Role.Member));

        mGroupAdapter = new GroupMemberAdapter(this, GroupMemberAdapter.TYPE_ADMIN, mUserList, this);
        mGridView.setAdapter(mGroupAdapter);

        // 设置OnTouchListener
        mGridView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mGroupAdapter.isDeleteMode) {
                            mGroupAdapter.isDeleteMode = false;
                            mGroupAdapter.notifyDataSetChanged();
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void clickAddMember() {
        Toast.makeText(this, "Click Add Member", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteMember(Users users) {
        Toast.makeText(this, "Delete Member:" + users.getName(), Toast.LENGTH_SHORT).show();
        mUserList.remove(users);
        mGroupAdapter.notifyDataSetChanged();
    }

}
