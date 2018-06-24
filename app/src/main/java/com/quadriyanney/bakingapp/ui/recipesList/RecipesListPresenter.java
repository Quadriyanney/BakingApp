package com.quadriyanney.bakingapp.ui.recipesList;

import com.quadriyanney.bakingapp.data.model.Recipe;
import com.quadriyanney.bakingapp.data.remote.ApiService;
import com.quadriyanney.bakingapp.ui.base.BasePresenter;
import com.quadriyanney.bakingapp.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.quadriyanney.bakingapp.helper.Constants.CANT_GET_RECIPES;
import static com.quadriyanney.bakingapp.helper.Constants.NO_INTERNET;

public class RecipesListPresenter implements BasePresenter<RecipesListView> {

    private ApiService apiService;
    private RecipesListView recipesListView;
    private NetworkUtil networkUtil;
    private ArrayList<Recipe> recipes;
    private CompositeDisposable compositeDisposable;

    @Inject
    public RecipesListPresenter(ApiService apiService, NetworkUtil networkUtil,
                                CompositeDisposable compositeDisposable) {
        this.apiService = apiService;
        this.networkUtil = networkUtil;
        this.compositeDisposable = compositeDisposable;
    }


    @Override
    public void attachView(RecipesListView recipesListView) {
        this.recipesListView = recipesListView;
        this.recipesListView.setUpView();
    }

    void fetchRecipes() {
        if (networkUtil.isOnline()) {
            recipesListView.showRetryButton(false);
            recipesListView.showProgress(true);

            compositeDisposable.add(apiService.getRecipes()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<ArrayList<Recipe>>() {
                        @Override
                        public void onNext(ArrayList<Recipe> value) {
                            recipes = value;
                            recipesListView.showRecipesList(recipes);
                        }

                        @Override
                        public void onError(Throwable e) {
                            recipesListView.showRetryButton(true);
                            recipesListView.showMessage(CANT_GET_RECIPES);
                        }

                        @Override
                        public void onComplete() {
                            recipesListView.showProgress(false);
                        }
                    })
            );
        } else {
            recipesListView.showRetryButton(true);
            recipesListView.showMessage(NO_INTERNET);
        }
    }

    void onRecipeClicked(int position) {
        recipesListView.showRecipeDetails(recipes.get(position));
    }

    @Override
    public void detachView() {
        compositeDisposable.clear();
        this.recipesListView = null;
    }

}
