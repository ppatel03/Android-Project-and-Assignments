package com.example.kevin.teachgraphicsanimation;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


public class Activity_DragandDrop extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getFragmentManager().beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(0))
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.draganddrop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_plain) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(0))
                    .commit();
            return true;
        }
        if (id == R.id.action_animation) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(1))
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int choice;
        List<Map<String,?>> moviesList;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
            moviesList = new MovieData().getMoviesList();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            choice = getArguments().getInt(ARG_SECTION_NUMBER);

            View rootView = inflater.inflate(R.layout.fragment_draganddrop_experiment, container, false);

            GridLayout gridLayout = (GridLayout) rootView.findViewById(R.id.gridlayout);
            gridLayout.setUseDefaultMargins(true);

            Point screenSize = new Point();
            getActivity().getWindowManager().getDefaultDisplay().getSize(screenSize);

            // load the first 11 movies.
            for (int i=0; i<15; i++){
                Map<String,?> movie = moviesList.get(i);
                String name = (String) movie.get("name");
                int icon = (Integer) movie.get("image");

                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(icon);
                imageView.setId(i);
                imageView.setOnLongClickListener(new MyLongClickListener());
                imageView.setOnDragListener(new MyDragListener());

                gridLayout.addView(imageView);
            }

            return rootView;
        }

        private final class MyLongClickListener implements View.OnLongClickListener {
            @Override
            public boolean onLongClick(View view){
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //MyDragShadowBuilder shadowBuilder = new MyDragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            }
        }


        private final class MyTouchListener implements View.OnTouchListener {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    //view.setVisibility(View.INVISIBLE);
                    Log.v("onTouch", "ACTION_DOWN");
                    return true;
                } else {
                    return false;
                }
            }
        }

        private final class MyDragListener implements View.OnDragListener
        {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int id = (Integer) v.getId();
                Map<String,?> movie = moviesList.get(id);
                int icon = (Integer) movie.get("image");

                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("onDrag", "DRAG_STARTED");
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("onDrag", "DRAG_ENTERED");
                        Drawable[] layers = new Drawable[2];
                        layers[0] = getResources().getDrawable(icon);
                        layers[1] = getResources().getDrawable(R.drawable.shape_droptarget);
                        LayerDrawable layerDrawable = new LayerDrawable(layers);
                        if (v instanceof ImageView) {
                            ImageView imageView = (ImageView) v;
                            imageView.setImageDrawable(layerDrawable);
                        }
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d("onDrag", "DRAG_EXITED");
                        if (v instanceof ImageView) {
                            ImageView imageView = (ImageView) v;
                            imageView.setImageResource(icon);
                        }
                        break;
                    case DragEvent.ACTION_DROP:
                        String s = Integer.toString(v.getId());
                        if (v instanceof ImageView) {
                            ImageView imageView = (ImageView) v;
                            imageView.setImageResource(icon);
                        }
                        View view = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) view.getParent();

                        if (choice == 0) {
                            // Swap the positions of the two views
                            int indexFrom = owner.indexOfChild(view);
                            int indexTo = owner.indexOfChild(v);
                            owner.removeView(view);
                            owner.addView(view, indexTo);
                            owner.removeView(v);
                            owner.addView(v, indexFrom);
                        } else if (choice ==1) {
                            float x1 = view.getX();
                            float y1 = view.getY();
                            float x2 = v.getX();
                            float y2 = v.getY();
                            v.animate().setDuration(1000)
                                    .x(x1)
                                    .y(y1)
                                    .rotationYBy(720)
                                    .scaleX(1.0F).scaleY(1.0F);

                            view.animate().setDuration(1000)
                                    .x(x2)
                                    .y(y2)
                                    .rotationYBy(720)
                                    .scaleX(1.0F).scaleY(1.0F);
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        break;
                    default:
                        break;
                }
                return true;
            }
        }

        private static class MyDragShadowBuilder extends View.DragShadowBuilder {
            private static Drawable shadow;

            public MyDragShadowBuilder(View v) {
                super(v);
                shadow = new ColorDrawable(Color.RED);
            }

            @Override
            public void onProvideShadowMetrics (Point size, Point touch) {
                int width, height;

                // Sets the width/height of the shadow to half the width of the original View
                width = getView().getWidth() / 2;
                height = getView().getHeight() / 2;
                shadow.setBounds(0, 0, width, height);

                // Sets the size parameter's width and height values. These get back to the system
                // through the size parameter.
                size.set(width, height);

                // Sets the touch point's position
                touch.set(0, 0);
            }

        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
        // from the dimensions passed in onProvideShadowMetrics().
        @Override
            public void onDrawShadow(Canvas canvas) {
                // Draws the ColorDrawable in the Canvas passed in from the system.
                shadow.draw(canvas);
            }
        }
    }
}
