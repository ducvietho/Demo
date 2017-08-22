package com.tuhocandroid.navdrawerandtablayout.Process;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tuhocandroid.navdrawerandtablayout.fragments.Playlist;
import com.tuhocandroid.navdrawerandtablayout.fragments.SingerFragment;
import com.tuhocandroid.navdrawerandtablayout.fragments.AlbumFragment;
import com.tuhocandroid.navdrawerandtablayout.fragments.SingerOfflineFragment;
import com.tuhocandroid.navdrawerandtablayout.fragments.SongFragment;

/**
 * Created by minhpq on 3/29/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {


    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new Playlist();
                break;
            case 1:
                frag=new SongFragment();
                break;
            case 2:
                frag=new AlbumFragment();
                break;
            case 3:
                frag=new SingerOfflineFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 4;
    }



    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title="PLAYLIST";
                break;
            case 1:
                title="BÀI HÁT";
                break;
            case 2:
                title="ALBUM";
                break;
            case 3:
                title="NGHỆ SĨ";
                break;
        }

        return title;
    }


}
