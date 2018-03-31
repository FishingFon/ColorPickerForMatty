package colorpickerformatty.fishingfon.com.colorpickerformatty;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ColorPickerFragment extends Fragment implements ColorPickerListener{


    private OnFragmentInteractionListener mListener;
    private ColorChangeSelectedListener activityColorChangedListener;
    ColorPickerListener cListener;
    ColorPicker cPicker;
    public ColorPickerFragment() {
        // Required empty public constructor
    }


    public static ColorPickerFragment newInstance() {
        ColorPickerFragment fragment = new ColorPickerFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_color_picker, container, false);
        //MY LISTENER
        cPicker = (ColorPicker) view.findViewById(R.id.colorPicker);
        cPicker.setListener(cListener);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        try {
            activityColorChangedListener = (ColorChangeSelectedListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

        cListener = new ColorPickerListener() {
            @Override
            public void onColorChanged(int color) {
                int[] colors = {color};
                activityColorChangedListener.onColorChanged(colors);
            }
        };
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onColorChanged(int color) {

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
