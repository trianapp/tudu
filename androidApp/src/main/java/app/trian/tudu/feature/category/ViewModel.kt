package app.trian.tudu.feature.category

import app.trian.tudu.base.BaseViewModelData
import app.trian.tudu.data.domain.category.CreateCategoryUseCase
import app.trian.tudu.data.domain.category.DeleteCategoryUseCase
import app.trian.tudu.data.domain.category.GetListCategoryWithCounterUseCase
import app.trian.tudu.data.domain.task.UpdateCategoryUseCase
import app.trian.tudu.data.model.CategoryModel
import app.trian.tudu.data.utils.Response
import app.trian.tudu.feature.category.CategoryEvent.SetCategoryName
import app.trian.tudu.feature.category.CategoryEvent.ShowFormCategory
import app.trian.tudu.feature.category.CategoryEvent.SubmitCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getListCategoryWithCounterUseCase: GetListCategoryWithCounterUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : BaseViewModelData<CategoryState, CategoryDataState, CategoryEvent>(
    CategoryState(),
    CategoryDataState()
) {
    init {
        handleActions()
        getListCategory()
    }

    private fun getListCategory() = async {
        getListCategoryWithCounterUseCase().collect {
            when (it) {
                is Response.Error -> Unit
                Response.Loading -> Unit
                is Response.Result -> {
                    commitData { copy(category = it.data) }
                }
            }
        }
    }

    private fun updateCategory() = asyncWithState {
        updateCategoryUseCase(
            CategoryModel(
                categoryId = categoryId,
                categoryName = categoryName
            )
        ).collect {
            when (it) {
                is Response.Error -> Unit
                Response.Loading -> Unit
                is Response.Result -> {
                    commit {
                        copy(
                            categoryId = "",
                            categoryName = "",
                            showFormCategory = false
                        )
                    }
                    getListCategory()
                }
            }
        }
    }

    private fun createCategory() = asyncWithState {
        createCategoryUseCase(categoryName = categoryName).collect { response ->
            when (response) {
                is Response.Error -> Unit
                Response.Loading -> Unit
                is Response.Result -> {
                    commit {
                        copy(
                            showFormCategory = false,
                            categoryId = "",
                            categoryName = ""
                        )
                    }
                    getListCategory()
                }
            }

        }
    }

    private fun deleteCategory() = asyncWithState {
        deleteCategoryUseCase(categoryId)
            .collect {
                when (it) {
                    is Response.Error -> Unit
                    Response.Loading -> Unit
                    is Response.Result -> {
                        commit {
                            copy(
                                showDialogDeleteCategory = false,
                                categoryId = "",
                                categoryName = ""
                            )
                        }
                        getListCategory()
                    }
                }
            }
    }

    override fun handleActions() = onEvent {
        when (it) {
            SubmitCategory -> if (uiState.value.categoryId.isEmpty()) createCategory()
            else updateCategory()

            is ShowFormCategory -> commit { copy(showFormCategory = it.isShow) }
            is SetCategoryName -> commit { copy(categoryName = it.categoryName) }
            is CategoryEvent.SetUpdateCategory -> {
                commit {
                    copy(
                        showFormCategory = true,
                        categoryName = it.categoryName,
                        categoryId = it.categoryId
                    )
                }
            }

            is CategoryEvent.DeleteCategory -> deleteCategory()
            is CategoryEvent.ShowDialogDeleteCategory -> commit {
                copy(showDialogDeleteCategory = it.isShow)
            }
        }
    }

}