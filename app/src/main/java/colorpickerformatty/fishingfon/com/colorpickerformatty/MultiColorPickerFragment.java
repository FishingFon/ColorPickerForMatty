package colorpickerformatty.fishingfon.com.colorpickerformatty;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MultiColorPickerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MultiColorPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultiColorPickerFragment extends Fragment implements MultiColorPickerListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MultiColorPicker multiColorPicker;
    private OnFragmentInteractionListener mListener;
    private MultiColorPicker mcPicker;
    MultiColorPickerListener cListener;
    ColorChangeSelectedListener activityColorChangedListener;
    public MultiColorPickerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MultiColorPickerFragment newInstance() {
        MultiColorPickerFragment fragment = new MultiColorPickerFragment();

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_multi_color_picker, container, false);
        //MY LISTENER
        mcPicker = (MultiColorPicker) view.findViewById(R.id.MultiColorPicker);
        mcPicker.setListener(cListener);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

        cListener = new MultiColorPickerListener() {
            @Override
            public void onColorChanged(int[] colorHSV) {
                activityColorChangedListener.onColorChanged(colorHSV);
            }
        };
        /*
        mcPicker.setListener(new MultiColorPickerListener() {
            @Override
            public void onColorChanged(int[] colors) {
                Log.v("Color", Arrays.toString(mcPicker.getColors()));
                activityColorChangedListener.onColorChanged(colors);
            }
        });*/

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onColorChanged(int[] colorHSV) {
        //activityColorChangedListener.onColorChanged(colorHSV);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
