package com.morekolodimotsumi.root.u_dj;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAccessAdapter extends FragmentPagerAdapter
{


    public TabsAccessAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0://MyPlayListFragment
                MyPlayListFragment myPlayListFragment = new MyPlayListFragment();
                return  myPlayListFragment;

            case 1://contactsFragment
                ContactsFragment contactsFragment = new ContactsFragment();
                return contactsFragment;//contactsFragment
            case 2://contactsFragment
                FollowingFragment followingFragment = new FollowingFragment();
                return followingFragment;//contactsFragment

                default:
                    return  null;
        }
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {


        switch (position){

            case 0:
                return  "PlayList";

            case 1:
                return  "Friends";
            case 2:
                return  "Following";

            default:
                return  null;
        }

    }

    @Override
    public int getCount() {

        return 3;
    }
}
