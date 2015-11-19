package com.example.kevin.teachgraphicsanimation;

/**
 * Created by kevin on 6/23/2014.
 */

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment_Drawable extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment_Drawable newInstance(int sectionNumber) {
        Fragment_Drawable fragment = new Fragment_Drawable();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_Drawable() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int choice = getArguments().getInt(ARG_SECTION_NUMBER);

        switch (choice) {
            case 0:
            case 1:
            case 2:
            case 3:
                return experiment_9patch(choice, inflater, container);
            case 4:
            case 5:
                return experiment_layerlist_drawable(choice, inflater, container);
            case 6:
                return experiment_levellist_drawable(choice, inflater, container);
            case 7:
                return experiment_transitionlist_drawable(choice, inflater, container);
            case 8:
                return experiment_clip_drawable(choice, inflater, container);
            case 9:
                return experiment_shape_drawable(choice, inflater, container);
            case 10:
                return experiment_animation_drawable(choice, inflater, container);
            case 11:
                return experiment_bitmap_drawable(choice, inflater, container);
            default:
                return experiment_9patch(choice, inflater, container);
        }
    }

    View experiment_9patch(int choice, LayoutInflater inflater, ViewGroup container){
        View rootView = inflater.inflate(R.layout.fragment_drawable_experiment, container, false);
        final TextView textView = (TextView) rootView.findViewById(R.id.textView);
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);


        switch (choice) {
            case 0:
                imageView.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView.setBackgroundResource(R.drawable.fantasy_frame_s);
                break;
            case 1:
                imageView.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView.setBackgroundResource(R.drawable.fantasy_frame_s_not9patch);
                break;
            case 2:
                imageView.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView.setBackgroundResource(R.drawable.goldframe);
                break;
            case 3:
                imageView.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView.setBackgroundResource(R.drawable.goldframe_not9);
                break;
            default:
                break;
        }

        SeekBar widthSeekBar = (SeekBar) rootView.findViewById(R.id.seekBar1);
        widthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ViewGroup.LayoutParams params = textView.getLayoutParams();
                params.width = progress * 15;
                textView.setLayoutParams(params);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar heightSeekBar = (SeekBar) rootView.findViewById(R.id.seekBar2);
        heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ViewGroup.LayoutParams params = textView.getLayoutParams();
                //params.width = progress * 5;
                params.height = progress * 15;
                textView.setLayoutParams(params);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return rootView;
    }

    int left=0, bottom=0;
    View experiment_layerlist_drawable(int choice, LayoutInflater inflater, ViewGroup container){
        View rootView = inflater.inflate(R.layout.fragment_drawable_experiment, container, false);
        rootView.findViewById(R.id.textView).setVisibility(View.INVISIBLE);
        //rootView.findViewById(R.id.seekBar1).setVisibility(View.INVISIBLE);
        //rootView.findViewById(R.id.seekBar2).setVisibility(View.INVISIBLE);

        final ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);

        if (choice == 4) {
            rootView.findViewById(R.id.seekBar1).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.seekBar2).setVisibility(View.INVISIBLE);
            imageView.setImageResource(R.drawable.layers);
            return rootView;
        }

        imageView.setImageResource(R.drawable.shrek2);

        SeekBar leftInsetSeekBar = (SeekBar) rootView.findViewById(R.id.seekBar1);
        leftInsetSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                left = progress*3;
                Drawable[] layers = new Drawable[2];
                layers[0] = getResources().getDrawable(R.drawable.shrek2);
                layers[1] = getResources().getDrawable(R.drawable.annotation);
                final LayerDrawable layerDrawable = new LayerDrawable(layers);
                layerDrawable.setLayerInset(1, left, 0, 0, bottom);
                imageView.setImageDrawable(layerDrawable);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar bottomInsetSeekBar = (SeekBar) rootView.findViewById(R.id.seekBar2);
        bottomInsetSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bottom = progress*3;
                Drawable[] layers = new Drawable[2];
                layers[0] = getResources().getDrawable(R.drawable.shrek2);
                layers[1] = getResources().getDrawable(R.drawable.annotation);
                final LayerDrawable layerDrawable = new LayerDrawable(layers);
                layerDrawable.setLayerInset(1, left, 0, 0, bottom);
                imageView.setImageDrawable(layerDrawable);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return rootView;
    }

    View experiment_levellist_drawable(int choice, LayoutInflater inflater, ViewGroup container){
        View rootView = inflater.inflate(R.layout.fragment_drawable_experiment, container, false);
        rootView.findViewById(R.id.seekBar2).setVisibility(View.INVISIBLE);

        final TextView textView = (TextView) rootView.findViewById(R.id.textView);
        textView.setText("Default Level Value: 0");
        textView.setTextSize(30);
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.levels);

        SeekBar levelSeekBar = (SeekBar) rootView.findViewById(R.id.seekBar1);
        levelSeekBar.setMax(200);
        levelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText("Level: " + Integer.toString(progress/100));
                imageView.setImageLevel(progress/100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return rootView;
    }


    View experiment_transitionlist_drawable(int choice, LayoutInflater inflater, ViewGroup container){
        View rootView = inflater.inflate(R.layout.fragment_drawable_experiment, container, false);
        ViewGroup parent = (ViewGroup) rootView.findViewById(R.id.linearLayout);
        parent.removeView(rootView.findViewById(R.id.textView));
        parent.removeView(rootView.findViewById(R.id.seekBar1));
        parent.removeView(rootView.findViewById(R.id.seekBar2));

        final ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.transitions);

        Button button = new Button(getActivity());
        button.setText("Start Transition");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionDrawable drawable = (TransitionDrawable) imageView.getDrawable();
                drawable.startTransition(500);
            }
        });
        parent.addView(button);

        return rootView;
    }

    View experiment_clip_drawable(int choice, LayoutInflater inflater, ViewGroup container){
        View rootView = inflater.inflate(R.layout.fragment_drawable_experiment, container, false);
        rootView.findViewById(R.id.seekBar2).setVisibility(View.INVISIBLE);

        final TextView textView = (TextView) rootView.findViewById(R.id.textView);
        textView.setText("Clipping Value: 0");
        textView.setTextSize(30);
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.clip);

        SeekBar levelSeekBar = (SeekBar) rootView.findViewById(R.id.seekBar1);
        levelSeekBar.setMax(10000);
        levelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ClipDrawable drawable = (ClipDrawable) imageView.getDrawable();
                drawable.setLevel(progress);
                textView.setText("Clipping Value: " + Integer.toString(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return rootView;
    }

    View experiment_shape_drawable(int choice, LayoutInflater inflater, ViewGroup container){
        View rootView = inflater.inflate(R.layout.fragment_drawable_experiment, container, false);
        ViewGroup parent = (ViewGroup) rootView.findViewById(R.id.linearLayout);
        parent.removeView(rootView.findViewById(R.id.textView));
        parent.removeView(rootView.findViewById(R.id.seekBar1));
        parent.removeView(rootView.findViewById(R.id.seekBar2));
        parent.removeView(rootView.findViewById(R.id.imageView));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 50, 0, 0);

        TextView textView1 = (TextView) new TextView(getActivity());
        textView1.setText("TextView with a light green background and a dark green boarder");
        textView1.setTextSize(30);
        textView1.setBackgroundResource(R.drawable.shape_green_rectangle);
        textView1.setLayoutParams(lp);
        parent.addView(textView1);

        TextView textView2 = (TextView) new TextView(getActivity());
        textView2.setText("TextView with a rounded blue rectangle");
        textView2.setTextSize(30);
        textView2.setBackgroundResource(R.drawable.shape_rounded_blue_rectangle);
        textView2.setLayoutParams(lp);
        parent.addView(textView2);

        TextView textView3 = (TextView) new TextView(getActivity());
        textView3.setText("TextView with an oval shape");
        textView3.setTextSize(25);
        textView3.setBackgroundResource(R.drawable.shape_oval);
        textView3.setLayoutParams(lp);
        parent.addView(textView3);

        return rootView;
    }


    View experiment_animation_drawable(int choice, LayoutInflater inflater, ViewGroup container){
        View rootView = inflater.inflate(R.layout.fragment_drawable_experiment, container, false);
        ViewGroup parent = (ViewGroup) rootView.findViewById(R.id.linearLayout);

        parent.removeView(rootView.findViewById(R.id.textView));
        parent.removeView(rootView.findViewById(R.id.seekBar1));
        parent.removeView(rootView.findViewById(R.id.seekBar2));


        final ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.animation);

        Button button = new Button(getActivity());
        button.setText("Start Animation");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationDrawable frameAnimation = (AnimationDrawable) imageView.getDrawable();
                frameAnimation.start();
            }
        });
        parent.addView(button);

        return rootView;
    }

    View experiment_bitmap_drawable(int choice, LayoutInflater inflater, ViewGroup container){
        View rootView = inflater.inflate(R.layout.fragment_drawable_experiment, container, false);
        ViewGroup parent = (ViewGroup) rootView.findViewById(R.id.linearLayout);
        rootView.findViewById(R.id.textView).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.seekBar1).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.seekBar2).setVisibility(View.INVISIBLE);

        final ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);

        Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(150, 150, 100, paint);

        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(150, 150, 100, paint);


        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(4);
        paint.setTextSize(60);
        canvas.drawText("4.5", 110, 165, paint);

        imageView.setImageBitmap(bitmap);



        return rootView;
    }
}

