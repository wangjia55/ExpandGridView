package jacob.expandgridview;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {
    private GridView mGridView;
    private GroupMemberAdapter mGroupAdapter;

    private List<Users> mUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView = (GridView) findViewById(R.id.gridView);

        mUserList.add(new Users(R.drawable.ic_avatar1, "Jimmy", Users.Role.Amin));
        mUserList.add(new Users(R.drawable.ic_avatar2, "Kenvin", Users.Role.Member));
        mUserList.add(new Users(R.drawable.ic_avatar6, "Jasper", Users.Role.Member));
        mUserList.add(new Users(R.drawable.ic_avatar4, "Jacob", Users.Role.Member));
        mUserList.add(new Users(R.drawable.ic_avatar5, "Richard", Users.Role.Member));

        mGroupAdapter = new GroupMemberAdapter(this, GroupMemberAdapter.TYPE_ADMIN, mUserList);
        mGridView.setAdapter(mGroupAdapter);

    }
}
