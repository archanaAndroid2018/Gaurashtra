package com.gaurashtra.app.view.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.bean.categoryListbean.CategoryDatum;
import com.gaurashtra.app.model.bean.categoryListbean.CategoryListResponseDTO;
import com.gaurashtra.app.presentator.CategoryFragPresentar;
import com.gaurashtra.app.presentator.presentarInteractor.CategoryPresentarInteractor;
import com.gaurashtra.app.view.adapter.CategoryAdapter;
import com.gaurashtra.app.view.fragment.fragementInteractor.categoryFragInteractor;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements categoryFragInteractor {
    private static String ARG_IMAGE_RESOURCE = "ARG_IMAGE_RESOURCE";
    private CategoryPresentarInteractor categoryPresentarInteractor;
    private RecyclerView categorRv;
    private CategoryAdapter adapter;
    private GlobalUtils globalUtils;
    PrefClass prefClass;

    public static CategoryFragment newInstance(int imgres){
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_IMAGE_RESOURCE,imgres);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        categoryPresentarInteractor = new CategoryFragPresentar(this);
        globalUtils = new GlobalUtils();
        categorRv = view.findViewById(R.id.rvCategories);
        categorRv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        categorRv.setLayoutManager(manager);
        categoryPresentarInteractor.sendRequest(SharedPreference.getUserid(getActivity()));
        return view;
    }

    @Override
    public void showProgress() {
        globalUtils.showdialog(getContext());
    }

    @Override
    public void hideProgress() {
       globalUtils.hidedialog();
    }

    @Override
    public void getResponse(Object object) {
        if (object != null) {
            List<CategoryDatum> catList = new ArrayList<>();
            CategoryListResponseDTO categoryListResponseDTO = (CategoryListResponseDTO) object;
//        String message = categoryListResponseDTO.getMessage();
//        globalUtils.showToast(getContext(),message);
            List<CategoryDatum> categoryData = categoryListResponseDTO.getResult().getCategoryData();
            for (int i = 0; i < categoryData.size(); i++) {
                CategoryDatum datum = new CategoryDatum();
                datum.setCategoryId(categoryData.get(i).getCategoryId());
                datum.setName(categoryData.get(i).getName());
                catList.add(datum);
                String categoryId = categoryData.get(i).getCategoryId();
                SharedPreference.setCategoryId(getActivity(), categoryId);
            }
            adapter = new CategoryAdapter(getContext(), catList);
            categorRv.setAdapter(adapter);
        }
    }
}
