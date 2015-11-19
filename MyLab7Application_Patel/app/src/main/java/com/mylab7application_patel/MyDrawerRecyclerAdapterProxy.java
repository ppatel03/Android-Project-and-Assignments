package com.mylab7application_patel;


import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by prashant on 3/10/2015.
 */
public class MyDrawerRecyclerAdapterProxy implements MyDrawerRecyclerAdapter.OnItemClickListener{
    final ActionBarActivity actionBarActivity;
    MyDrawerRecyclerAdapter myDrawerRecyclerAdapter;
    DrawerData drawerData;
    RecyclerView mDrawerList;
    int containerId;
    private DrawerLayout mDrawerLayout;
    FragmentManager fragmentManager;

    public MyDrawerRecyclerAdapterProxy(ActionBarActivity actionBarActivity, DrawerData drawerData,
                                        RecyclerView mDrawerList, int containerId,FragmentManager fragmentManager,
                                        DrawerLayout mDrawerLayout){
        this.actionBarActivity = actionBarActivity;
        this.drawerData = drawerData;
        this.mDrawerList = mDrawerList;
        this.containerId = containerId;
        this.fragmentManager=fragmentManager;
        this.mDrawerLayout = mDrawerLayout;
    }

    public void setViewAdapter(){
        myDrawerRecyclerAdapter = new MyDrawerRecyclerAdapter(actionBarActivity,drawerData.getDrawersList());
        mDrawerList.setAdapter(myDrawerRecyclerAdapter);
        myDrawerRecyclerAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onMyRecyclerItemClick(View view, int position) {
        switch (position){
            case 1 :
                fragmentManager.beginTransaction()
                        .replace(R.id.container,DemoFragment.newInstance(1))
                        .addToBackStack(null)
                        .commit();
                break;
            case 2 :
                fragmentManager.beginTransaction()
                        .replace(R.id.container,ListViewFragment.newInstance(1))
                        .addToBackStack(null)
                        .commit();
                ListViewFragment.newInstance(1);
                break;
            case 3 :
                fragmentManager.beginTransaction()
                        .replace(R.id.container,GridViewFragment.newInstance(1))
                        .addToBackStack(null)
                        .commit();

                break;
            case 5 :
                fragmentManager.beginTransaction()
                        .replace(R.id.container,AvatarFragment.newInstance(1))
                        .addToBackStack(null)
                        .commit();

                break;
            case 6 :
                fragmentManager.beginTransaction()
                        .replace(R.id.container,TitanicFragment.newInstance(1))
                        .addToBackStack(null)
                        .commit();

                break;
            case 7 :
                fragmentManager.beginTransaction()
                        .replace(R.id.container,AvengersFragment.newInstance(1))
                        .addToBackStack(null)
                        .commit();

                break;
            default:
                fragmentManager.beginTransaction()
                        .replace(R.id.container,DemoFragment.newInstance(1))
                        .addToBackStack(null)
                        .commit();

        }

        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onMyRecyclerItemLongClick(View view, int position) {

    }
}
