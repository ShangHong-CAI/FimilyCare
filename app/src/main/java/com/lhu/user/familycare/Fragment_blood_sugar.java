package com.lhu.user.familycare;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lhu.user.familycare.tool.IMGtool;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_blood_sugar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_blood_sugar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Fragment_blood_sugar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_blood_sugar.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_blood_sugar newInstance(String param1, String param2) {
        Fragment_blood_sugar fragment = new Fragment_blood_sugar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_fragment_blood_sugar, container, false);
        // Inflate the layout for this fragment
        Context context =v.getContext();
        ImageView IMG_btn_before_dinner = (ImageView) v.findViewById(R.id.IMG_btn_before_dinner);
        IMG_btn_before_dinner.setImageBitmap(IMGtool.readBitMap(context,R.drawable.body_btn_before_dinner_n));

        ImageView IMG_blood_sugar = (ImageView) v.findViewById(R.id.IMG_blood_sugar);
        IMG_blood_sugar.setImageBitmap(IMGtool.readBitMap(context,R.drawable.body_blood_sugar));

        ImageView body_btn_before_dinner_n = (ImageView) v.findViewById(R.id.IMG_btn_after_dinner);
        body_btn_before_dinner_n.setImageBitmap(IMGtool.readBitMap(context,R.drawable.body_btn_after_dinner_y));

        return v;
    }

}
