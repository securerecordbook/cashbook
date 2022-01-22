package com.securerecordbook.cashbook;

import static com.securerecordbook.cashbook.MainActivity.mEditor;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PromotionFragment extends Fragment {

    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    ViewPager2 viewPager;
    Drawable list[];
    TextView[] dots;

    Button getStartBtn;

    public PromotionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_promotion, container, false);
        //((MainActivity)getActivity()).customLoadingScreen.hideCustomLoadingScreen();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dotsLayout = view.findViewById(R.id.dots_container);
        viewPager = view.findViewById(R.id.view_pager);
        getStartBtn = view.findViewById(R.id.get_start_btn);

        list = new Drawable[3];
        list[0] = ContextCompat.getDrawable(getContext(), R.drawable.one);
        list[1] = ContextCompat.getDrawable(getContext(), R.drawable.two);
        list[2] = ContextCompat.getDrawable(getContext(), R.drawable.three);

        sliderAdapter = new SliderAdapter(list);
        viewPager.setAdapter(sliderAdapter);

        dots = new TextView[3];
        dotsIndicator();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                selectedIndicator(position);
                super.onPageSelected(position);
            }
        });


        getStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).replaceFragment(new AuthFragment());
                mEditor.putBoolean("FirstStart", false);
                mEditor.commit();
            }
        });
    }

    private void selectedIndicator(int position) {
        for (int i=0;i<dots.length;i++) {
            if (i == position) {
                dots[i].setTextColor(getResources().getColor(R.color.colorPrimaryVariant));
            }else{
                dots[i].setTextColor(getResources().getColor(R.color.grey));
            }
        }
    }

    private void dotsIndicator() {
        for (int i=0;i<dots.length;i++) {
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#9679;"));
            dots[i].setTextSize(14);
            dotsLayout.addView(dots[i]);
        }
    }
}