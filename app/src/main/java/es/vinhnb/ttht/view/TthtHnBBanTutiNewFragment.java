package es.vinhnb.ttht.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.es.tungnv.views.R;

import static es.vinhnb.ttht.view.TthtHnBaseActivity.BUNDLE_POS;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IOnTthtHnBBanTutiNewFragment} interface
 * to handle interaction events.
 * Use the {@link TthtHnBBanTutiNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TthtHnBBanTutiNewFragment extends TthtHnBaseFragment {
    private IOnTthtHnBBanTutiNewFragment mListener;

    public TthtHnBBanTutiNewFragment() {
        // Required empty public constructor
    }

    public static TthtHnBBanTutiNewFragment newInstance(int pos) {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_POS, pos);
        TthtHnBBanTutiNewFragment fragment = new TthtHnBBanTutiNewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ttht_hn_bban_tuti_new, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IOnTthtHnBBanTutiNewFragment) {
            mListener = (IOnTthtHnBBanTutiNewFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IOnTthtHnBBanTutiNewFragment");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void initDataAndView(View rootView) throws Exception {

    }

    @Override
    public void setAction(Bundle savedInstanceState) throws Exception {

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
    public interface IOnTthtHnBBanTutiNewFragment {
    }
}
