package piyushjohnson.mytyre;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import piyushjohnson.mytyre.adapter.TyresAdapter;
import piyushjohnson.mytyre.common.BaseFragment;
import piyushjohnson.mytyre.common.OnTyreItemActionListener;
import piyushjohnson.mytyre.databinding.FragmentTyreFinderBinding;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.ui.MainViewModel;
import piyushjohnson.mytyre.ui.cart.CartViewModel;

/*
 * @extends BaseFragment
 * @implements OnTyreItemActionListener
 * @has-a FragmentTyreFinderBinding, MainViewModel, CartViewModel, LinearLayoutManager, TyresAdapter, Listener
 * */
public class TyreFinderFragment extends BaseFragment implements OnTyreItemActionListener {

    private static final String TAG = "TyreFinderFragment";

    private FragmentTyreFinderBinding binding;
    private MainViewModel mainViewModel;
    private CartViewModel cartViewModel;
    private LinearLayoutManager layoutManager;
    private TyresAdapter adapter;
    private Listener mListener;

    public TyreFinderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // setting instance of fragment listener to communicate with parent fragment/activity
        mListener = (Listener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting instances of view model's to access state
        mainViewModel = getViewModel(MainViewModel.class);
        cartViewModel = getViewModel(CartViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // setting instance of binding by inflating layout of fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tyre_finder, container, false);
        // setting binding event handler for view's of layout
        binding.setHandlers(new TyreFinderBindingHandlers());
        // return the root view from binding instance of fragment's layout
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        // workaround for forcing layout height/width to 'MATCH_PARENT', because when this fragment mounts onto 'NavHostFragment'
        // height/width is default to 'WRAP_CONTENT'
        // TODO: extra code try to remove this tweak by adjusting 'activity_home.xml' view i.e 'NavHostFragment'
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        getParentFragment().getView().setLayoutParams(layoutParams);
    }

    @Override
    public void onDetach() {
        // setting instance of fragment listener to prevent any leak, before fragments detaches from host fragment/activity
        mListener = null;
        super.onDetach();
    }

    private void init() {
        // initializing list of tyre's onto recycler view
        initTyresList();
        // start observing live data to, retain latest snapshot of tyre's to display
        setupViewModelData();
    }

    private void initTyresList() {
        // setting 'TyresAdapter' to transform list of tyres onto adapter view, also registering event listeners for tyre selection
        adapter = new TyresAdapter(this::onTyreSelected, this);
        // setting 'LinearLayoutManager' to arrange each tyre item in vertical list
        layoutManager = new LinearLayoutManager(getContext());
        // attaching layout manager to current adapter view instance
        binding.tyresList.setLayoutManager(layoutManager);
        // attaching adapter to current adapter view instance
        binding.tyresList.setAdapter(adapter);
    }

    private void setupViewModelData() {
        // listening to latest snapshot of all fetched tyre's list from 'MainViewModel'
        // show's progress bar to mark loading state of tyre's list, after list is attached to adapter, makes it hidden
        // also updates 'isOffline' live data with fetched resource 'isStale' method, which checks if it's from cache or network
        mainViewModel.getTyres().observe(Objects.requireNonNull(getActivity()), listResource -> {
            mainViewModel.setIsOffline(listResource.isStale());
            if (listResource.isSuccessful()) {
                adapter.setItemsList(listResource.data());
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void onTyreSelected(Tyre tyre) {
        // setting bundle with selected tyre name
        Bundle bundle = new Bundle();
        bundle.putString("tyreName", tyre.getName());
        // navigates from 'TyreFinder' to TyreShow destination, with attached bundle
        NavHostFragment.findNavController(this).navigate(R.id.action_TyreFinder_to_TyreShow, bundle);
        // recall's listener to fire 'onTyreSelected' event handler onto 'HomeActivity'
        // TODO: no need to call, this feature is already implemented in this method
        mListener.onTyreSelected(tyre);
    }

    @Override
    public void onTyreAddToCart(Tyre tyre) {
        Log.d(TAG, "onTyreAddToCart() called with: tyre = [" + tyre.getName() + "]");
        // calling addCartItem action listener from 'CartViewModel'
        cartViewModel.addCartItem(tyre);
    }

    @Override
    public void onTyreBuyNow(Tyre tyre) {
        Log.d(TAG, "onTyreBuyNow() called with: tyre = [" + tyre.getName() + "]");
        //TODO: to be implemented in future, for taking action when 'Buy Now' button is selected on any tyre list item
    }

    public interface Listener {
        void onTyreSelected(Tyre tyre);
    }

    public class TyreFinderBindingHandlers {
        public void onFilterSelected(View view, boolean isChecked) {
            Log.d(TAG, "onFilterSelected() called with: view = [" + view + "], id = [" + isChecked + "]");
            //TODO: to be implemented in future, for taking action when any filter chip is selected, which will trigger a dialog to apply filter
        }
    }
}
